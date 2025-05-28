package hexlet.code;

import java.util.List;
import java.util.Map;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Map<String, Object> file1 = Parser.parse(filepath1);
        Map<String, Object> file2 = Parser.parse(filepath2);
        List<Map<String, Object>> diff = DiffBuilder.build(file1, file2);

        return switch (format) {
            case "stylish", "" -> Stylish.format(diff);
            case null -> Stylish.format(diff);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
