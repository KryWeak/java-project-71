package hexlet.code.formatters;

import hexlet.code.Node;
import hexlet.code.OperationType;

import java.util.TreeMap;

public final class StylishFormat implements Format {
    private static final String PLUS_PREFIX = "  + ";
    private static final String MINUS_PREFIX = "  - ";
    private static final String UNCHANGED_PREFIX = "    ";
    public static final String STYLISH_NAME = "stylish";

    public String generate(TreeMap<String, Node> diffData) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        diffData.forEach((key, node) -> {
            OperationType type = node.getType();
            Object oldValue = node.getOldValue();
            Object newValue = node.getNewValue();

            switch (type) {
                case ADDED:
                    appendChange(builder, PLUS_PREFIX, key, newValue);
                    break;
                case DELETED:
                    appendChange(builder, MINUS_PREFIX, key, oldValue);
                    break;
                case UNCHANGED:
                    appendChange(builder, UNCHANGED_PREFIX, key, newValue);
                    break;
                case UPDATED:
                    appendChange(builder, MINUS_PREFIX, key, oldValue);
                    appendChange(builder, PLUS_PREFIX, key, newValue);
                    break;
                default:
                    throw new IllegalStateException("Unexpected operation type: " + type);
            }
        });
        builder.append("}");

        return builder.toString();
    }

    private static void appendChange(StringBuilder builder, String prefix, String key, Object value) {
        builder.append(prefix).append(key).append(": ").append(value).append("\n");
    }
}
