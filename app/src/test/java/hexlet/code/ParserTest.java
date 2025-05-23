package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testParseJsonFile() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("file1.json").getFile());
        Map<String, Object> data = Parser.parse(file.getAbsolutePath());
        assertNotNull(data);
        assertEquals("hexlet.io", data.get("host"));
        assertEquals(false, data.get("follow"));
    }

    @Test
    void testParseYamlFile() throws Exception {
        File file = new File(getClass().getClassLoader().getResource("file1.yml").getFile());
        Map<String, Object> data = Parser.parse(file.getAbsolutePath());
        assertNotNull(data);
        assertEquals("hexlet.io", data.get("host"));
        assertEquals(false, data.get("follow"));
    }

    @Test
    void testParseUnsupportedFile() {
        File file = new File(getClass().getClassLoader().getResource("file1.txt").getFile());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Parser.parse(file.getAbsolutePath());
        });
        assertTrue(exception.getMessage().contains("Unsupported file format"));
    }
}
