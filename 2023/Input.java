import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

public class Input {

    public static Stream<String> inputStream(int day) {
        try {
            return Files.lines(Paths.get("input", "day" + day + ".txt")); 
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
        Iterator<String> iterator = inputStream(day).iterator();

        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return iterator;
            }
        };
    }
}
