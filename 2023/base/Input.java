package base;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class Input {

    public static Stream<String> stream(int day) {
        try {
            Path path = FileSystems.getDefault().getPath(".", "input", day + ".txt");
            return Files.lines(path); 
        } catch (Exception e) {
            return null;
        }
    }

    public static String string(int day) {
        String result = "";
        for (String line : lines(day)) {
            result += line + "\n";
        }
        return result;
    }

    public static Iterable<String> lines(int day) {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return stream(day).iterator();
            }
        };
    }
}
