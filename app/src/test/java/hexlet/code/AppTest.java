package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AppTest {

    @Test
    void testHelpOption() {
        int exitCode = new CommandLine(new App()).execute("--help");
        assertEquals(0, exitCode);
    }

    @Test
    void testVersionOption() {
        int exitCode = new CommandLine(new App()).execute("--version");
        assertEquals(0, exitCode);
    }

    @Test
    void testMissingArguments() {
        int exitCode = new CommandLine(new App()).execute();
        assertNotEquals(0, exitCode);
    }

    @Test
    void testValidArguments() {
        int exitCode = new CommandLine(new App()).execute(
                "src/test/resources/file1.json",
                "src/test/resources/file2.json"
        );
        assertEquals(0, exitCode);
    }


}
