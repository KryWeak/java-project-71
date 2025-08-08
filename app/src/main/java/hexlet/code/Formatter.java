package hexlet.code;

import hexlet.code.formatters.StylishFormatter;
import hexlet.code.formatters.PlainFormatter;
import java.util.List;

public class Formatter {

    public static String format(List<DiffItem> diff, String formatName) {
        switch (formatName.toLowerCase()) {
            case "stylish":
                return StylishFormatter.format(diff);
            case "plain":
                return PlainFormatter.format(diff);
            default:
                throw new IllegalArgumentException("Unsupported format: " + formatName);
        }
    }
}
