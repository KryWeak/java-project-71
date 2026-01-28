package hexlet.code;

import hexlet.code.utils.FileReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestDiffer {

    private final String json1 = "src/test/resources/fixtures/file1.json";
    private final String json2 = "src/test/resources/fixtures/file2.json";
    private final String yml1 = "src/test/resources/fixtures/file1.yml";
    private final String yml2 = "src/test/resources/fixtures/file2.yml";

    @Test
    void testJsonStylish() throws IOException {
        assertEquals(
                FileReader.getFileText("src/test/resources/fixtures/expected/stylish.txt"),
                Differ.generate(json1, json2, "stylish")
        );
    }

    @Test
    void testJsonPlain() throws IOException {
        assertEquals(
                FileReader.getFileText("src/test/resources/fixtures/expected/plain.txt"),
                Differ.generate(json1, json2, "plain")
        );
    }

    @Test
    void testJsonFormat() throws IOException {
        assertEquals(
                FileReader.getFileText("src/test/resources/fixtures/expected/json.json"),
                Differ.generate(json1, json2, "json")
        );
    }

    @Test
    void testJsonDefault() throws IOException {
        assertEquals(
                FileReader.getFileText("src/test/resources/fixtures/expected/stylish.txt"),
                Differ.generate(json1, json2)
        );
    }

    @Test
    void testYmlDefault() throws IOException {
        assertEquals(
                FileReader.getFileText("src/test/resources/fixtures/expected/stylish.txt"),
                Differ.generate(yml1, yml2)
        );
    }

    @Test
    void testYmlStylish() throws IOException {
        assertEquals(
                FileReader.getFileText("src/test/resources/fixtures/expected/stylish.txt"),
                Differ.generate(yml1, yml2, "stylish")
        );
    }

    @Test
    void testYmlPlain() throws IOException {
        assertEquals(
                FileReader.getFileText("src/test/resources/fixtures/expected/plain.txt"),
                Differ.generate(yml1, yml2, "plain")
        );
    }

    @Test
    void testYmlJson() throws IOException {
        assertEquals(
                FileReader.getFileText("src/test/resources/fixtures/expected/json.json"),
                Differ.generate(yml1, yml2, "json")
        );
    }
}
