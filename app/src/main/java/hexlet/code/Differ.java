package hexlet.code;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;

public class Differ {

    public static String generate(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        StringBuilder result = new StringBuilder("{\n");

        for (String key : allKeys) {
            boolean inFirst = data1.containsKey(key);
            boolean inSecond = data2.containsKey(key);

            if (!inFirst) {
                result.append(String.format("  + %s: %s\n", key, data2.get(key)));
            } else if (!inSecond) {
                result.append(String.format("  - %s: %s\n", key, data1.get(key)));
            } else if (!Objects.equals(data1.get(key), data2.get(key))) {
                result.append(String.format("  - %s: %s\n", key, data1.get(key)));
                result.append(String.format("  + %s: %s\n", key, data2.get(key)));
            } else {
                result.append(String.format("    %s: %s\n", key, data1.get(key)));
            }
        }

        result.append("}");
        return result.toString();
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        Map<String, Object> data1 = Parser.parse(filePath1);
        Map<String, Object> data2 = Parser.parse(filePath2);

        if ("stylish".equals(format)) {
            return generate(data1, data2);
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }
}
