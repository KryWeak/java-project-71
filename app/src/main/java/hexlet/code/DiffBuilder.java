package hexlet.code;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class DiffBuilder {

    public static TreeMap<String, Node> build(Map<String, Object> data1,
                                              Map<String, Object> data2) {

        TreeMap<String, Node> differences = new TreeMap<>();

        data1.forEach((key, value) -> {
            if (data2.containsKey(key)) {
                Object newValue = data2.get(key);
                if (!Objects.equals(value, newValue)) {
                    differences.put(key, new Node(OperationType.UPDATED, value, newValue));
                } else {
                    differences.put(key, new Node(OperationType.UNCHANGED, value, value));
                }
            } else {
                differences.put(key, new Node(OperationType.DELETED, value, null));
            }
        });

        data2.forEach((key, value) -> {
            if (!data1.containsKey(key)) {
                differences.put(key, new Node(OperationType.ADDED, null, value));
            }
        });

        return differences;
    }
}
