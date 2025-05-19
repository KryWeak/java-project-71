package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Differ {
    public static String generate(String filepath1, String filepath2) {
        String resultComparison = "";

        try {
            Map<String, Object> file1 = getData(filepath1);
            Map<String, Object> file2 = getData(filepath2);

            resultComparison = resultComparison + "{";

            var allKeys = new TreeMap<String, Object>();

            allKeys.putAll(file1);
            allKeys.putAll(file2);

            for (String key : allKeys.keySet()) {
                boolean inFirst = file1.containsKey(key);
                boolean inSecond = file2.containsKey(key);

                Object value1 = file1.get(key);
                Object value2 = file2.get(key);

                if (inFirst && inSecond) {
                    if (Objects.equals(value1, value2)) {
                        String comparison1 = "\n    " + key + ": " + value1;
                        resultComparison = resultComparison + comparison1;
                    } else {
                        String comparison2  = "\n  " + "- " + key + ": " + value1
                                + "\n  " + "+ " + key + ": " + value2;
                        resultComparison  = resultComparison + comparison2;
                    }

                } else if (inFirst) {
                    String comparison3 = "\n  " + "- " + key + ": " + value1;
                    resultComparison = resultComparison + comparison3;

                } else {
                    String comparison4 = "\n  " + "+ " + key + ": " + value2;
                    resultComparison = resultComparison + comparison4;
                }

            }
            resultComparison = resultComparison + "\n}";
            System.out.println("}");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return resultComparison;
    }

    private static Map<String, Object> getData(String filePath) throws Exception {
        Path path = Path.of(filePath).toAbsolutePath().normalize();
        String content = Files.readString(path);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, new TypeReference<>() {});

    }
}
