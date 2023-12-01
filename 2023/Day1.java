import java.util.HashMap;
import java.util.Map;

import base.Input;

// Trebuchet!?
public class Day1 {

    public static final Map<String, Integer> numberWords = new HashMap<>();
    
    public static void main(String[] args) {
        numberWords.put("one", 1);
        numberWords.put("two", 2);
        numberWords.put("three", 3);
        numberWords.put("four", 4);
        numberWords.put("five", 5);
        numberWords.put("six", 6);
        numberWords.put("seven", 7);
        numberWords.put("eight", 8);
        numberWords.put("nine", 9);

        int sum1 = 0;
        int sum2 = 0;
        for (String line : Input.lines(1)) {
            sum1 += getValue1(line);
            sum2 += getValue2(line);
        }
        System.out.println(sum1);
        System.out.println(sum2);
    }

    // Return a number consisting of the first and last digits of a string
    public static int getValue1(String string) {
        char first;
        char last;
        int i = 0;
        int j = string.length() - 1;

        do {
            first = string.charAt(i++);
        } while (!Character.isDigit(first));
        do {
            last = string.charAt(j--);
        } while (!Character.isDigit(last));

        return 10 * toInt(first) + toInt(last);
    }

    public static int toInt(char c) {
        return Character.digit(c, 10);
    }

    // getValue2 but also count number words as digits
    public static int getValue2(String string) {
        String buffer = "";
        int length = 0;
        int first;
        int last;
        char c;
        int i = 0;
        int j = string.length() - 1;

        while (true) {
            c = string.charAt(i++);
            first = Character.digit(c, 10);
            if (first != -1) {
                break;
            }
            buffer += c;
            length++;

            if (length < 3) {
                continue;
            }
            first = numberWord(buffer.substring(length - 3));
            if (first != -1) {
                break;
            }

            if (length < 4) {
                continue;
            }
            first = numberWord(buffer.substring(length - 4));
            if (first != -1) {
                break;
            }

            if (length < 5) {
                continue;
            }
            first = numberWord(buffer.substring(length - 5));
            if (first != -1) {
                break;
            }
        };

        buffer = "";
        length = 0;

        while (true) {
            c = string.charAt(j--);
            last = Character.digit(c, 10);
            if (last != -1) {
                break;
            }
            buffer = c + buffer;
            length++;
            
            if (length < 3) {
                continue;
            }
            last = numberWord(buffer.substring(0, 3));
            if (last != -1) {
                break;
            }

            if (length < 4) {
                continue;
            }
            last = numberWord(buffer.substring(0, 4));
            if (last != -1) {
                break;
            }

            if (length < 5) {
                continue;
            }
            last = numberWord(buffer.substring(0, 5));
            if (last != -1) {
                break;
            }
        };

        return 10 * first + last;
    }

    public static int numberWord(String string) {
        if (numberWords.containsKey(string)) {
            return numberWords.get(string);
        }
        return -1;
    }
}
