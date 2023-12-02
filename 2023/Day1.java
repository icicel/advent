import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import base.Input;

// Trebuchet!?
// 54159
// 53866
public class Day1 {
    public static void main(String[] args) {
        Day1 main = new Day1();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final Map<String, Integer> numwords;
    public final int numwordMaxLength;
    public final int numwordMinLength;
    public final List<Line> lines;
    
    public Day1() {
        numwords = new HashMap<>();
        numwords.put("one", 1);
        numwords.put("two", 2);
        numwords.put("three", 3);
        numwords.put("four", 4);
        numwords.put("five", 5);
        numwords.put("six", 6);
        numwords.put("seven", 7);
        numwords.put("eight", 8);
        numwords.put("nine", 9);
        numwordMaxLength = 5;
        numwordMinLength = 3;

        lines = new LinkedList<>();
        for (String line : Input.lines(1)) {
            lines.add(new Line(line));
        }

    }

    public int solve1() {
        int sum = 0;
        for (Line line : lines) {
            sum += 10 * line.firstDigit(false, false);
            sum += line.firstDigit(false, true);
        }
        return sum;
    }

    public int solve2() {
        int sum = 0;
        for (Line line : lines) {
            sum += 10 * line.firstDigit(true, false);
            sum += line.firstDigit(true, true);
        }
        return sum;
    }



    // Wrapper for a line
    public class Line {
        public final String line;

        public Line(String line) {
            this.line = line;
        }
        
        // Returns the first digit found by a scanner
        // If fromRight, return the last digit instead (first from the right)
        public int firstDigit(boolean includeNumwords, boolean fromRight) {
            Scanner scanner;
            if (includeNumwords) {
                scanner = new NumwordScanner(line, fromRight);
            } else {
                scanner = new CharScanner(line, fromRight);
            }
            while (true) {
                int digit = scanner.scan();
                if (digit != -1) {
                    return digit;
                }
                scanner.step();
            }
        }
    }



    // Iterable-like that steps through a string one char at a time
    public interface Scanner {

        // Increment the current char
        public void step();

        // Checks current char for digits
        // Returns -1 if not a digit
        public int scan();
    }



    // Simple implementation
    public class CharScanner implements Scanner {
        public final boolean reverse;
        public final String string;
        public int current;

        public CharScanner(String string, boolean reverse) {
            this.string = string;
            this.reverse = reverse;

            current = reverse ? string.length() - 1 : 0;
        }

        public void step() {
            if (reverse) {
                current--;
            } else {
                current++;
            }
        }

        public int scan() {
            char currentChar = string.charAt(current);
            if (Character.isDigit(currentChar)) {
                return Character.digit(currentChar, 10);
            }
            return -1;
        }
    }



    // Scans for both digits and numwords by using a buffer
    public class NumwordScanner implements Scanner {
        public final boolean reverse;
        public final String string;
        public String buffer;
        public int current;

        public NumwordScanner(String string, boolean reverse) {
            this.string = string;
            this.reverse = reverse;

            if (reverse) {
                current = string.length() - 1;
                buffer = string.substring(current);
            } else {
                current = 0;
                buffer = string.substring(0, 1);
            }
        }

        public void step() {
            if (reverse) {
                current--;
                buffer = string.charAt(current) + buffer;
            } else {
                current++;
                buffer = buffer + string.charAt(current);
            }
        }

        public int scan() {
            char currentChar = string.charAt(current);
            if (Character.isDigit(currentChar)) {
                return Character.digit(currentChar, 10);
            }
            // check buffer for numwords of every possible length
            for (int l = numwordMinLength; l <= numwordMaxLength; l++) {
                if (buffer.length() < l) {
                    continue;
                }
                String numword;
                if (reverse) {
                    numword = buffer.substring(0, l);
                } else {
                    numword = buffer.substring(buffer.length() - l);
                }
                if (numwords.containsKey(numword)) {
                    return numwords.get(numword);
                }
            }
            return -1;
        }
    }
}
