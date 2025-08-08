package hexlet.code.formatters;

import hexlet.code.DiffItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.List;

public class JsonFormatter {

    public static String format(List<DiffItem> diff) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode result = mapper.createArrayNode();

        for (DiffItem item : diff) {
            ObjectNode node = mapper.createObjectNode();
            node.put("key", item.getKey());
            node.put("type", item.getType().toString().toLowerCase());

            switch (item.getType()) {
                case ADDED:
                    node.set("newValue", mapper.valueToTree(item.getNewValue()));
                    break;
                case REMOVED:
                    node.set("oldValue", mapper.valueToTree(item.getOldValue()));
                    break;
                case CHANGED:
                    node.set("oldValue", mapper.valueToTree(item.getOldValue()));
                    node.set("newValue", mapper.valueToTree(item.getNewValue()));
                    break;
                case UNCHANGED:
                    node.set("oldValue", mapper.valueToTree(item.getOldValue()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown diff type: " + item.getType());
            }

            result.add(node);
        }

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
    }
}
