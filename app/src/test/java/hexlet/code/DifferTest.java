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
        File file1 = new File(getClass().getClassLoader().getResource("file1.yml").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("file2.yml").getFile());

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
}
