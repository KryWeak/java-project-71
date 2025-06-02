package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;
import java.util.Map;

public class Formatter {

    public static String format(List<Map<String, Object>> diff, String formatName) {
        return switch (formatName) {
            case "plain" -> Plain.format(diff);
            case "stylish", "default", "" -> Stylish.format(diff);
            default -> throw new IllegalArgumentException("Unknown format: " + formatName);
        };
    }
}