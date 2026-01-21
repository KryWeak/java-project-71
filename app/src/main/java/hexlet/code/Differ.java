package hexlet.code;

import hexlet.code.formatters.Formatter;
import hexlet.code.utils.FileReader;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Differ {

    public static String generate(String path1, String path2, String format) throws IOException {

        String text1 = FileReader.getFileText(path1);
        String text2 = FileReader.getFileText(path2);

        String format1 = FileReader.getFileExtension(path1);
        String format2 = FileReader.getFileExtension(path2);

        Map<String, Object> data1 = Parser.parse(text1, format1);
        Map<String, Object> data2 = Parser.parse(text2, format2);

        TreeMap<String, Node> diff = DiffBuilder.build(data1, data2);

        return Formatter.format(diff, format);
    }

    public static String generate(String path1, String path2) throws IOException {
        return generate(path1, path2, "stylish");
    }
}
