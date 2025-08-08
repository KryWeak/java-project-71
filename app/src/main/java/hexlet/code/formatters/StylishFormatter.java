package hexlet.code.formatters;

import hexlet.code.DiffItem;
import java.util.List;

public class StylishFormatter {

    public static String format(List<DiffItem> diff) {
        StringBuilder result = new StringBuilder("{\n");

        for (DiffItem item : diff) {
            switch (item.getType()) {
                case ADDED:
                    result.append(String.format("  + %s: %s\n", item.getKey(), formatValue(item.getNewValue())));
                    break;
                case REMOVED:
                    result.append(String.format("  - %s: %s\n", item.getKey(), formatValue(item.getOldValue())));
                    break;
                case CHANGED:
                    result.append(String.format("  - %s: %s\n", item.getKey(), formatValue(item.getOldValue())));
                    result.append(String.format("  + %s: %s\n", item.getKey(), formatValue(item.getNewValue())));
                    break;
                case UNCHANGED:
                    result.append(String.format("    %s: %s\n", item.getKey(), formatValue(item.getOldValue())));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown diff type: " + item.getType());
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return (String) value;
        } else {
            return value.toString();
        }
    }
}
