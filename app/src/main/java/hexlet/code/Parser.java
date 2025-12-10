package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String text, String format) throws IOException {
        ObjectMapper mapper = switch (format) {
            case "json" -> new ObjectMapper();
            case "yml", "yaml" -> new YAMLMapper();
            default -> throw new IllegalArgumentException("Unsupported data format: " + format);
        };

        return mapper.readValue(text, Map.class);
    }
}
