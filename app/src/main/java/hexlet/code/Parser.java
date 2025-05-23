package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String filePath) throws Exception {
        if (filePath == null) {
            throw new IllegalArgumentException("File path must not be null");
        }

        String lowerPath = filePath.toLowerCase();

        if (lowerPath.endsWith(".json")) {
            return getDataJsonFile(filePath);
        } else if (lowerPath.endsWith(".yml") || lowerPath.endsWith(".yaml")) {
            return getDataYamlFile(filePath);
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + filePath);
        }
    }

    static Map<String, Object> getDataYamlFile(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File file = new File(filePath).getAbsoluteFile();
        return mapper.readValue(file, new TypeReference<>() { });

    }

    static Map<String, Object> getDataJsonFile(String filePath) throws Exception {
        File file = new File(filePath).getAbsoluteFile();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, new TypeReference<>() {});

    }
}
