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
        assertTrue(result.contains("- chars2: [d, e, f]"));
        assertTrue(result.contains("+ chars2: false"));
    }

    @Test
    void testGenerateWithPlainFormat() throws Exception {
        String result = Differ.generate(jsonFile1Path, jsonFile2Path, "plain");

        assertTrue(result.contains("Property 'chars2' was updated. From [complex value] to false"));
        assertTrue(result.contains("Property 'checked' was updated. From false to true"));
        assertTrue(result.contains("Property 'default' was updated. From null to [complex value]"));
        assertTrue(result.contains("Property 'id' was updated. From 45 to null"));
        assertTrue(result.contains("Property 'key1' was removed"));
        assertTrue(result.contains("Property 'key2' was added with value: 'value2'"));
        assertTrue(result.contains("Property 'numbers2' was updated. From [complex value] to [complex value]"));
        assertTrue(result.contains("Property 'numbers3' was removed"));
        assertTrue(result.contains("Property 'numbers4' was added with value: [complex value]"));
        assertTrue(result.contains("Property 'obj1' was added with value: [complex value]"));
        assertTrue(result.contains("Property 'setting1' was updated. From 'Some value' to 'Another value'"));
        assertTrue(result.contains("Property 'setting2' was updated. From 200 to 300"));
        assertTrue(result.contains("Property 'setting3' was updated. From true to 'none'"));
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
    void testGenerateWithYamlAndPlainFormat() throws Exception {
        String result = Differ.generate(yamlFile1Path, yamlFile2Path, "plain");
        assertTrue(result.contains("Property 'chars2' was updated"));
        assertTrue(result.contains("Property 'checked' was updated"));
    }
}
