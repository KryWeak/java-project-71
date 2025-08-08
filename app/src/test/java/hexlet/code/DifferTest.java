package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
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
    void testGenerateWithStylishFormat() throws Exception {
        String result = Differ.generate(jsonFile1Path, jsonFile2Path, "stylish");

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
        assertTrue(result.contains("+ obj1: {nestedKey=value, isNested=true}"));
    }

    @Test
    void testGenerateWithPlainFormat() throws Exception {
        String result = Differ.generate(jsonFile1Path, jsonFile2Path, "plain");

        assertTrue(result.contains("Property 'chars2' was updated. From [complex value] to false"));
        assertTrue(result.contains("Property 'key1' was removed"));
        assertTrue(result.contains("Property 'key2' was added with value: 'value2'"));
    }

    @Test
    void testGenerateWithJsonFormat() throws Exception {
        String result = Differ.generate(jsonFile1Path, jsonFile2Path, "json");

        System.out.println("JSON result:");
        System.out.println(result);

        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));

        assertTrue(result.contains("\"key\""), "Должен содержать поле 'key'");
        assertTrue(result.contains("\"type\""), "Должен содержать поле 'type'");

        assertTrue(result.contains("added") || result.contains("ADDED"), "Должен содержать тип 'added'");
        assertTrue(result.contains("removed") || result.contains("REMOVED"), "Должен содержать тип 'removed'");
        assertTrue(result.contains("changed") || result.contains("CHANGED"), "Должен содержать тип 'changed'");
        assertTrue(result.contains("unchanged") || result.contains("UNCHANGED"), "Должен содержать тип 'unchanged'");
    }

    @Test
    void testGenerateWithDefaultFormat() throws Exception {
        String result = Differ.generate(jsonFile1Path, jsonFile2Path);

        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
    }

    @Test
    void testGenerateWithUnsupportedFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Differ.generate(jsonFile1Path, jsonFile2Path, "unsupported");
        });

        assertTrue(exception.getMessage().contains("Unsupported format"));
    }

    @Test
    void testGenerateWithYamlAndJsonFormat() throws Exception {
        String result = Differ.generate(yamlFile1Path, yamlFile2Path, "json");

        System.out.println("YAML JSON result:");
        System.out.println(result);

        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
        assertTrue(result.contains("\"key\""), "Должен содержать поле 'key'");
        assertTrue(result.contains("\"type\""), "Должен содержать поле 'type'");
    }

    @Test
    void testGenerateWithMixedFormatsAndJson() throws Exception {
        String result = Differ.generate(jsonFile1Path, yamlFile2Path, "json");

        System.out.println("Mixed JSON result:");
        System.out.println(result);

        assertTrue(result.startsWith("["));
        assertTrue(result.endsWith("]"));
        assertTrue(result.contains("\"key\""), "Должен содержать поле 'key'");
    }

    @Test
    void testJsonFormatStructure() throws Exception {
        String result = Differ.generate(jsonFile1Path, jsonFile2Path, "json");

        assertTrue(result.contains("\"key\""), "Должен содержать поле 'key'");
        assertTrue(result.contains("\"type\""), "Должен содержать поле 'type'");
        assertTrue(result.contains("\"oldValue\""), "Должен содержать поле 'oldValue'");
        assertTrue(result.contains("\"newValue\""), "Должен содержать поле 'newValue'");

        assertTrue(result.trim().startsWith("["));
        assertTrue(result.trim().endsWith("]"));
    }
}
