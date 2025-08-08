package hexlet.code.formatters;

import hexlet.code.DiffItem;
import java.util.List;

public class PlainFormatter {

    public static String format(List<DiffItem> diff) {
        StringBuilder result = new StringBuilder();

        for (DiffItem item : diff) {
            switch (item.getType()) {
                case ADDED:
                    result.append(String.format("Property '%s' was added with value: %s\n",
                            item.getKey(), formatValue(item.getNewValue())));
                    break;
                case REMOVED:
                    result.append(String.format("Property '%s' was removed\n", item.getKey()));
                    break;
                case CHANGED:
                    result.append(String.format("Property '%s' was updated. From %s to %s\n",
                            item.getKey(), formatValue(item.getOldValue()), formatValue(item.getNewValue())));
                    break;
                case UNCHANGED:
                    break;
                default:
                    throw new IllegalArgumentException("Unknown diff type: " + item.getType());
            }
        }

        return result.toString().trim();
    }

    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof List || value instanceof java.util.Map) {
            return "[complex value]";
        } else {
            return value.toString();
        }
    }
}
