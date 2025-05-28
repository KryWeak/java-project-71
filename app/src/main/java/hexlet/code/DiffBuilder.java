package hexlet.code;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedHashMap;
import java.util.Objects;

public class DiffBuilder {

    public static List<Map<String, Object>> build(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> allKeys = new TreeSet<>();
        allKeys.addAll(map1.keySet());
        allKeys.addAll(map2.keySet());

        List<Map<String, Object>> result = new java.util.ArrayList<>();

        for (String key : allKeys) {
            boolean inFirst = map1.containsKey(key);
            boolean inSecond = map2.containsKey(key);
            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            Map<String, Object> line = new LinkedHashMap<>();
            line.put("key", key);

            if (!inFirst) {
                line.put("status", "added");
                line.put("value", value2);
            } else if (!inSecond) {
                line.put("status", "removed");
                line.put("value", value1);
            } else if (Objects.equals(value1, value2)) {
                line.put("status", "unchanged");
                line.put("value", value1);
            } else {
                if (value1 instanceof Map && value2 instanceof Map) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> nestedMap1 = (Map<String, Object>) value1;
                    @SuppressWarnings("unchecked")
                    Map<String, Object> nestedMap2 = (Map<String, Object>) value2;

                    List<Map<String, Object>> children = build(nestedMap1, nestedMap2);
                    line.put("status", "nested");
                    line.put("children", children);
                } else {
                    line.put("status", "changed");
                    line.put("oldValue", value1);
                    line.put("newValue", value2);
                }
            }

            result.add(line);
        }

        return result;
    }
}
