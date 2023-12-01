import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

public class Input {

    public static Stream<String> inputStream(int day) {
        try {
            Path path = FileSystems.getDefault().getPath(".", "input", day + ".txt");
            return Files.lines(path); 
        } catch (Exception e) {
            return null;
        }
    }

    public static String inputString(int day) {
        String result = "";
        try (Stream<String> stream = inputStream(day)) {
            stream.forEach(line -> result.concat(line + "\n"));
        }
        return result;
    }

    public static Iterable<String> inputLines(int day) {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return inputStream(day).iterator();
            }
        };
    }
}
