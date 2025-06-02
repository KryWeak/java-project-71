package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Plain {

    public static String format(List<Map<String, Object>> diff) {
        StringBuilder result = new StringBuilder();

        for (Map<String, Object> line : diff) {
            String key = (String) line.get("key");
            String status = (String) line.get("status");

            switch (status) {
                case "added" -> {
                    Object value = line.get("value");
                    result.append("Property '")
                            .append(key)
                            .append("' was added with value: ")
                            .append(stringify(value))
                            .append("\n");
                }
                case "removed" -> {
                    Object value = line.get("value");
                    result.append("Property '")
                            .append(key)
                            .append("' was removed")
                            .append("\n");
                }
                case "changed" -> {
                    Object oldValue = line.get("oldValue");
                    Object newValue = line.get("newValue");
                    result.append("Property '")
                            .append(key)
                            .append("' was updated. From ")
                            .append(stringify(oldValue))
                            .append(" to ")
                            .append(stringify(newValue))
                            .append("\n");
                }
                case "nested" -> {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> children = (List<Map<String, Object>>) line.get("children");
                    String nested = format(children);
                    if (!nested.isEmpty()) {
                        result.append(nested).append("\n");
                    }
                }
                default -> { }
            }
        }

        return result.toString().trim();
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        if (value instanceof Map || value instanceof List) {
            return "[complex value]";
        }
        return value.toString();
    }
}