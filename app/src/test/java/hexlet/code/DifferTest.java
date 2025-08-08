package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {

    private static String jsonFile1Path;
    private static String jsonFile2Path;
    private static String yamlFile1Path;
    private static String yamlFile2Path;

    @BeforeAll
    static void setUp() {
        jsonFile1Path = "src/test/resources/file1.json";
        jsonFile2Path = "src/test/resources/file2.json";
        yamlFile1Path = "src/test/resources/file1.yml";
        yamlFile2Path = "src/test/resources/file2.yml";
    }

    @Test
    void testGenerateWithJsonFiles() throws Exception {
        String result = Differ.generate(jsonFile1Path, jsonFile2Path);

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
    void testGenerateWithYamlFiles() throws Exception {
        String result = Differ.generate(yamlFile1Path, yamlFile2Path);

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
    void testGenerateWithMixedFiles() throws Exception {
        String result = Differ.generate(jsonFile1Path, yamlFile2Path);

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
    void testGenerateWithIdenticalYamlFiles() throws Exception {
        String result = Differ.generate(yamlFile1Path, yamlFile1Path);

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
    void testGenerateWithUnsupportedFormat(@TempDir Path tempDir) throws Exception {
        // Создаем файл с неподдерживаемым форматом
        Path unsupportedFile = tempDir.resolve("file.txt");
        Files.writeString(unsupportedFile, "some content");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(unsupportedFile.toString(), unsupportedFile.toString());
        });

        assertTrue(exception.getMessage().contains("Unsupported file format"));
    }
}
