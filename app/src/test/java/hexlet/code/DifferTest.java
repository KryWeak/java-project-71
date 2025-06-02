package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    @Test
    void testGenerateJson() throws Exception {

        File file1 = new File(getClass().getClassLoader().getResource("file1.json").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("file2.json").getFile());

        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate(file1.getAbsolutePath(), file2.getAbsolutePath());
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testGenerateYaml() throws Exception {

        File file1 = new File(getClass().getClassLoader().getResource("file1.yml").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("file2.yml").getFile());

        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate(file1.getAbsolutePath(), file2.getAbsolutePath());
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testEmptyFiles() throws Exception {
        File file1 = new File(getClass().getClassLoader().getResource("empty1.json").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("empty2.json").getFile());

        String expected = "{\n}";

        String actual = Differ.generate(file1.getAbsolutePath(), file2.getAbsolutePath());
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testIdenticalFiles() throws Exception {
        File file1 = new File(getClass().getClassLoader().getResource("file1.json").getFile());
        File file1Copy = new File(getClass().getClassLoader().getResource("file1.json").getFile());

        String expected = """
                {
                    follow: false
                    host: hexlet.io
                    proxy: 123.234.53.22
                    timeout: 50
                }""";

        String actual = Differ.generate(file1.getAbsolutePath(), file1Copy.getAbsolutePath());
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testStylishFormatExplicit() throws Exception {
        File file1 = new File(getClass().getClassLoader().getResource("file1.json").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("file2.json").getFile());

        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate(file1.getAbsolutePath(), file2.getAbsolutePath(), "stylish");
        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void testUnsupportedFormat() {
        File file1 = new File(getClass().getClassLoader().getResource("file1.json").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("file2.json").getFile());

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> Differ.generate(file1.getAbsolutePath(), file2.getAbsolutePath(), "unsupported")
        );

        assertEquals("Unsupported format: unsupported", exception.getMessage());
    }

    @Test
    void testPlainFormat() throws Exception {
        File file1 = new File(getClass().getClassLoader().getResource("plain1.json").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("plain2.json").getFile());

        String expected = """
            Property 'chars2' was updated. From [complex value] to false
            Property 'checked' was updated. From false to true
            Property 'default' was updated. From null to [complex value]
            Property 'id' was updated. From 45 to null
            Property 'key1' was removed
            Property 'key2' was added with value: 'value2'
            Property 'numbers2' was updated. From [complex value] to [complex value]
            Property 'numbers3' was removed
            Property 'numbers4' was added with value: [complex value]
            Property 'obj1' was added with value: [complex value]
            Property 'setting1' was updated. From 'Some value' to 'Another value'
            Property 'setting2' was updated. From 200 to 300
            Property 'setting3' was updated. From true to 'none'
            """;

        String actual = Differ.generate(file1.getAbsolutePath(), file2.getAbsolutePath(), "plain");
        assertEquals(expected.trim(), actual.trim());
    }

}
