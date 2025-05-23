package hexlet.code;

import java.util.Map;
import java.util.TreeSet;
import java.util.Objects;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws Exception {
        Map<String, Object> file1 = Parser.parse(filepath1);
        Map<String, Object> file2 = Parser.parse(filepath2);
        var allKeys = new TreeSet<String>();
        allKeys.addAll(file1.keySet());
        allKeys.addAll(file2.keySet());

        StringBuilder result = new StringBuilder();
        result.append("{\n");

        for (String key : allKeys) {
            boolean inFirst = file1.containsKey(key);
            boolean inSecond = file2.containsKey(key);
            Object value1 = file1.get(key);
            Object value2 = file2.get(key);

            if (inFirst && inSecond) {
                if (Objects.equals(value1, value2)) {
                    result.append("    ").append(key).append(": ").append(value1).append("\n");
                } else {
                    result.append("  - ").append(key).append(": ").append(value1).append("\n");
                    result.append("  + ").append(key).append(": ").append(value2).append("\n");
                }
            } else if (inFirst) {
                result.append("  - ").append(key).append(": ").append(value1).append("\n");
            } else {
                result.append("  + ").append(key).append(": ").append(value2).append("\n");
            }
        }

        result.append("}");
        return result.toString();
    }
}
