package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String format(List<Map<String, Object>> diff) {
        return format(diff, 1);
    }

    @SuppressWarnings("unchecked")
    public static String format(List<Map<String, Object>> diff, int depth) {
        StringBuilder result = new StringBuilder("{\n");
        String indent = "    ".repeat(depth - 1);
        String prefixIndent = "  ".repeat(depth - 1);


        for (Map<String, Object> entry : diff) {
            String key = (String) entry.get("key");
            String status = (String) entry.get("status");

            switch (status) {
                case "added" -> result.append(formatLine(key, entry.get("value"), "+", depth));
                case "removed" -> result.append(formatLine(key, entry.get("value"), "-", depth));
                case "unchanged" -> result.append(formatLine(key, entry.get("value"), " ", depth));
                case "changed" -> {
                    result.append(formatLine(key, entry.get("oldValue"), "-", depth));
                    result.append(formatLine(key, entry.get("newValue"), "+", depth));
                }
                case "nested" -> {
                    result.append(prefixIndent).append("  ").append(key).append(": ")
                            .append(format((List<Map<String, Object>>) entry.get("children"), depth + 1))
                            .append("\n");
                }
                default -> { }
            }
        }

        result.append(indent).append("}");

        return result.toString();
    }

    private static String formatLine(String key, Object value, String sign, int depth) {
        String indent = "    ".repeat(depth - 1);
        String prefix = indent + "  " + sign + " " + key + ": ";
        return prefix + toString(value, depth) + "\n";
    }

    private static String toString(Object value, int depth) {
        if (value instanceof Map<?, ?> mapValue) {
            StringBuilder result = new StringBuilder("{\n");
            for (Map.Entry<?, ?> entry : mapValue.entrySet()) {
                String indent = "    ".repeat(depth);
                result.append(indent)
                        .append(entry.getKey())
                        .append(": ")
                        .append(toString(entry.getValue(), depth + 1))
                        .append("\n");
            }
            result.append("    ".repeat(depth - 1)).append("}");
            return result.toString();
        }

        return value == null ? "null" : value.toString();
    }

}
