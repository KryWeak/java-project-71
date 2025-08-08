package hexlet.code;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Objects;

public class DiffBuilder {

    public static List<DiffItem> buildDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> allKeys = new TreeSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        List<DiffItem> diff = new ArrayList<>();

        for (String key : allKeys) {
            boolean inFirst = data1.containsKey(key);
            boolean inSecond = data2.containsKey(key);

            if (!inFirst) {
                diff.add(new DiffItem(key, null, data2.get(key), DiffItem.DiffType.ADDED));
            } else if (!inSecond) {
                diff.add(new DiffItem(key, data1.get(key), null, DiffItem.DiffType.REMOVED));
            } else if (!Objects.equals(data1.get(key), data2.get(key))) {
                diff.add(new DiffItem(key, data1.get(key), data2.get(key), DiffItem.DiffType.CHANGED));
            } else {
                diff.add(new DiffItem(key, data1.get(key), data2.get(key), DiffItem.DiffType.UNCHANGED));
            }
        }

        return diff;
    }
}
