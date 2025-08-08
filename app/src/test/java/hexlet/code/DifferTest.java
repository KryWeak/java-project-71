package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Files;

class DifferTest {

    private static String file1Path;
    private static String file2Path;

    @BeforeAll
    static void setUp() {
        file1Path = "src/test/resources/file1.json";
        file2Path = "src/test/resources/file2.json";
    }

    @Test
    void testGenerateWithDifferentFiles() throws Exception {
        String result = Differ.generate(file1Path, file2Path);

        String expected = """
            {
              - follow: false
                host: hexlet.io
              - proxy: 123.234.53.22
              - timeout: 50
              + timeout: 20
              + verbose: true
            }""";

        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithFormat() throws Exception {
        String result = Differ.generate(file1Path, file2Path, "stylish");
        assertNotNull(result);
        assertTrue(result.contains("host: hexlet.io"));
    }

    @Test
    void testGenerateWithIdenticalFiles() throws Exception {
        String result = Differ.generate(file1Path, file1Path);

        String expected = """
            {
                follow: false
                host: hexlet.io
                proxy: 123.234.53.22
                timeout: 50
            }""";

        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithEmptyFiles(@TempDir Path tempDir) throws Exception {
        String emptyJson = "{}";
        Path emptyFile1 = tempDir.resolve("empty1.json");
        Path emptyFile2 = tempDir.resolve("empty2.json");

        Files.writeString(emptyFile1, emptyJson);
        Files.writeString(emptyFile2, emptyJson);

        String result = Differ.generate(emptyFile1.toString(), emptyFile2.toString());
        assertEquals("{\n}", result);
    }

    @Test
    void testGenerateWithOneEmptyFile(@TempDir Path tempDir) throws Exception {
        String emptyJson = "{}";
        Path emptyFile = tempDir.resolve("empty.json");
        Files.writeString(emptyFile, emptyJson);

        String result = Differ.generate(emptyFile.toString(), file1Path);

        // Должен содержать все ключи из file1 с префиксом +
        assertTrue(result.contains("+ follow: false"));
        assertTrue(result.contains("+ host: hexlet.io"));
        assertTrue(result.contains("+ proxy: 123.234.53.22"));
        assertTrue(result.contains("+ timeout: 50"));
    }

    @Test
    void testGenerateWithDifferentDataTypes(@TempDir Path tempDir) throws Exception {
        String json1 = """
            {
              "string": "hello",
              "number": 42,
              "boolean": true,
              "null": null
            }""";

        String json2 = """
            {
              "string": "world",
              "number": 100,
              "boolean": false,
              "null": null
            }""";

        Path file1 = tempDir.resolve("types1.json");
        Path file2 = tempDir.resolve("types2.json");

        Files.writeString(file1, json1);
        Files.writeString(file2, json2);

        String result = Differ.generate(file1.toString(), file2.toString());

        assertTrue(result.contains("- string: hello"));
        assertTrue(result.contains("+ string: world"));
        assertTrue(result.contains("- number: 42"));
        assertTrue(result.contains("+ number: 100"));
        assertTrue(result.contains("- boolean: true"));
        assertTrue(result.contains("+ boolean: false"));
        assertTrue(result.contains("null: null"));
    }

    @Test
    void testGenerateWithNestedObjects(@TempDir Path tempDir) throws Exception {
        String json1 = """
            {
              "simple": "value1",
              "nested": {"key": "value1"}
            }""";

        String json2 = """
            {
              "simple": "value2",
              "nested": {"key": "value2"}
            }""";

        Path file1 = tempDir.resolve("nested1.json");
        Path file2 = tempDir.resolve("nested2.json");

        Files.writeString(file1, json1);
        Files.writeString(file2, json2);

        String result = Differ.generate(file1.toString(), file2.toString());

        assertTrue(result.contains("- simple: value1"));
        assertTrue(result.contains("+ simple: value2"));
    }

    @Test
    void testGenerateWithSpecialCharacters(@TempDir Path tempDir) throws Exception {
        String json1 = """
            {
              "key with spaces": "value with \\"quotes\\"",
              "unicode": "привет мир"
            }""";

        String json2 = """
            {
              "key with spaces": "different value",
              "unicode": "hello world"
            }""";

        Path file1 = tempDir.resolve("special1.json");
        Path file2 = tempDir.resolve("special2.json");

        Files.writeString(file1, json1);
        Files.writeString(file2, json2);

        String result = Differ.generate(file1.toString(), file2.toString());

        assertTrue(result.contains("- key with spaces: value with \"quotes\""));
        assertTrue(result.contains("+ key with spaces: different value"));
        assertTrue(result.contains("- unicode: привет мир"));
        assertTrue(result.contains("+ unicode: hello world"));
    }

    @Test
    void testGenerateWithLargeNumbers(@TempDir Path tempDir) throws Exception {
        String json1 = """
            {
              "small": 1,
              "large": 999999999999999999
            }""";

        String json2 = """
            {
              "small": 2,
              "large": 999999999999999999
            }""";

        Path file1 = tempDir.resolve("numbers1.json");
        Path file2 = tempDir.resolve("numbers2.json");

        Files.writeString(file1, json1);
        Files.writeString(file2, json2);

        String result = Differ.generate(file1.toString(), file2.toString());

        assertTrue(result.contains("- small: 1"));
        assertTrue(result.contains("+ small: 2"));
        assertTrue(result.contains("large: 999999999999999999")); // одинаковые
    }

    @Test
    void testGenerateWithUnsupportedFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(file1Path, file2Path, "unsupported");
        });

        assertTrue(exception.getMessage().contains("Unsupported format"));
    }

    @Test
    void testGenerateWithNonExistentFile() {
        Exception exception = assertThrows(Exception.class, () -> {
            Differ.generate("non-existent-file.json", file2Path);
        });

        assertNotNull(exception.getMessage());
    }

    @Test
    void testGenerateWithInvalidJson(@TempDir Path tempDir) throws Exception {
        String invalidJson = "{ invalid json }";
        Path invalidFile = tempDir.resolve("invalid.json");
        Files.writeString(invalidFile, invalidJson);

        Exception exception = assertThrows(Exception.class, () -> {
            Differ.generate(invalidFile.toString(), file1Path);
        });

        assertNotNull(exception.getMessage());
    }

}
