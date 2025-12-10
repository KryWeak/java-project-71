package hexlet.code.formatters;

import hexlet.code.Node;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import static hexlet.code.formatters.StylishFormat.STYLISH_NAME;
import static hexlet.code.formatters.PlainFormat.PLAIN_NAME;
import static hexlet.code.formatters.JsonFormat.JSON_NAME;

public class Formatter {
    public static final Map<String, Format> FORMATS = Map.of(
            STYLISH_NAME, new StylishFormat(),
            PLAIN_NAME, new PlainFormat(),
            JSON_NAME, new JsonFormat()
    );

    public static String format(TreeMap<String, Node> diffData, String format) throws IOException {
        return FORMATS.get(format).generate(diffData);
    }
}
