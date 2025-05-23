package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;

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

    @Test
    void testRunWithJsonFiles() throws Exception {
        File file1 = new File(getClass().getClassLoader().getResource("file1.json").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("file2.json").getFile());

        int exitCode = new CommandLine(new App())
                .execute(file1.getAbsolutePath(), file2.getAbsolutePath());
        assertEquals(0, exitCode);
    }

    @Test
    void testRunWithYamlFiles() throws Exception {
        File file1 = new File(getClass().getClassLoader().getResource("file1.yml").getFile());
        File file2 = new File(getClass().getClassLoader().getResource("file2.yml").getFile());

        int exitCode = new CommandLine(new App())
                .execute(file1.getAbsolutePath(), file2.getAbsolutePath());
        assertEquals(0, exitCode);
    }

}
