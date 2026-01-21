package hexlet.code.formatters;

import hexlet.code.Node;
import hexlet.code.OperationType;
import hexlet.code.utils.CustomUtils;

import java.util.TreeMap;

public final class PlainFormat implements Format {

    private static final String COMPLEX_VALUE = "[complex value]";
    private static final String PROPERTY_PREFIX = "Property '";
    public static final String PLAIN_NAME = "plain";

    @Override
    public String generate(TreeMap<String, Node> diffData) {
        StringBuilder result = new StringBuilder();

        diffData.forEach((key, node) -> {
            switch (node.getType()) {
                case ADDED:
                    result.append(PROPERTY_PREFIX)
                            .append(key)
                            .append("' was added with value: ")
                            .append(stringify(node.getNewValue()))
                            .append("\n");
                    break;

                case DELETED:
                    result.append(PROPERTY_PREFIX)
                            .append(key)
                            .append("' was removed\n");
                    break;

                case UPDATED:
                    result.append(PROPERTY_PREFIX)
                            .append(key)
                            .append("' was updated. From ")
                            .append(stringify(node.getOldValue()))
                            .append(" to ")
                            .append(stringify(node.getNewValue()))
                            .append("\n");
                    break;

                case UNCHANGED:
                    // ничего не выводим
                    break;

                default:
                    throw new IllegalStateException("Unexpected operation type");
            }
        });

        return result.toString().trim();
    }

    private static String stringify(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        if (CustomUtils.isComplexValue(value)) {
            return COMPLEX_VALUE;
        }
        return value.toString();
    }
}
