package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Command(
        name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference."
)
public class App implements Runnable {

    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(
            names = {"-f", "--format"},
            description = "output format [default: ${DEFAULT-VALUE}]",
            defaultValue = "stylish"
    )
    private String format;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            Map<String, Object> data1 = getData(filepath1);
            Map<String, Object> data2 = getData(filepath2);

            System.out.println("File 1: " + data1);
            System.out.println("File 2: " + data2);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }

    public static Map<String, Object> getData(String filePath) throws Exception {
        String content = Files.readString(Paths.get(filePath));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Map.class);
    }
}