package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.HashMap;

class DifferTest {

    private static String file1Path;
    private static String file2Path;

    @BeforeAll
    static void setUp() {
        // Используем существующие файлы из test/resources
        file1Path = "src/test/resources/file1.json";
        file2Path = "src/test/resources/file2.json";
    }

    @Test
    void testGenerateWithDifferentFiles() throws Exception {
        Map<String, Object> data1 = App.getData(file1Path);
        Map<String, Object> data2 = App.getData(file2Path);

        String result = Differ.generate(data1, data2);

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
    void testGenerateWithIdenticalFiles() throws Exception {
        Map<String, Object> data1 = App.getData(file1Path);
        Map<String, Object> data2 = App.getData(file1Path); // Используем тот же файл

        String result = Differ.generate(data1, data2);

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
    void testGenerateWithEmptyMaps() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();

        String result = Differ.generate(data1, data2);
        String expected = "{\n}";

        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithOneEmptyMap() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();
        data2.put("key", "value");

        String result = Differ.generate(data1, data2);
        String expected = """
            {
              + key: value
            }""";

        assertEquals(expected, result);
    }

    @Test
    void testGenerateWithRealFiles() throws Exception {
        // Тест с реальными файлами из resources
        Map<String, Object> data1 = App.getData(file1Path);
        Map<String, Object> data2 = App.getData(file2Path);

        String result = Differ.generate(data1, data2);

        // Проверяем, что результат содержит ожидаемые ключи в правильном порядке
        assertTrue(result.contains("follow: false"), "Должен содержать удаленный ключ follow");
        assertTrue(result.contains("host: hexlet.io"), "Должен содержать одинаковый ключ host");
        assertTrue(result.contains("proxy: 123.234.53.22"), "Должен содержать удаленный ключ proxy");
        assertTrue(result.contains("timeout: 50"), "Должен содержать старое значение timeout");
        assertTrue(result.contains("timeout: 20"), "Должен содержать новое значение timeout");
        assertTrue(result.contains("verbose: true"), "Должен содержать добавленный ключ verbose");

        // Проверяем правильный порядок (алфавитный)
        int followIndex = result.indexOf("follow");
        int hostIndex = result.indexOf("host");
        int proxyIndex = result.indexOf("proxy");
        int timeoutIndex = result.indexOf("timeout");
        int verboseIndex = result.indexOf("verbose");

        assertTrue(followIndex < hostIndex, "follow должен идти перед host");
        assertTrue(hostIndex < proxyIndex, "host должен идти перед proxy");
        assertTrue(proxyIndex < timeoutIndex, "proxy должен идти перед timeout");
        assertTrue(timeoutIndex < verboseIndex, "timeout должен идти перед verbose");
    }

    @Test
    void testGenerateWithDifferentValueTypes() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();

        data1.put("number", 42);
        data1.put("string", "hello");
        data1.put("boolean", true);

        data2.put("number", 100);
        data2.put("string", "world");
        data2.put("boolean", false);

        String result = Differ.generate(data1, data2);

        // Проверяем, что результат содержит различия
        assertTrue(result.contains("- number: 42"));
        assertTrue(result.contains("+ number: 100"));
        assertTrue(result.contains("- string: hello"));
        assertTrue(result.contains("+ string: world"));
        assertTrue(result.contains("- boolean: true"));
        assertTrue(result.contains("+ boolean: false"));
    }

    @Test
    void testGenerateWithSpecialCharacters() {
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> data2 = new HashMap<>();

        data1.put("key with spaces", "value with \"quotes\"");
        data2.put("key with spaces", "different value");

        String result = Differ.generate(data1, data2);

        assertTrue(result.contains("- key with spaces: value with \"quotes\""));
        assertTrue(result.contains("+ key with spaces: different value"));
    }
}
