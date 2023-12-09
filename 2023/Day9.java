import java.util.LinkedList;
import java.util.List;

import base.Input;

// Mirage Maintenance
// 1938731307
// 948
public class Day9 {
    public static void main(String[] args) {
        Day9 main = new Day9();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final List<Sequence> sequences;

    public Day9() {
        sequences = new LinkedList<>();
        for (String line : Input.lines(9)) {
            sequences.add(new Sequence(line));
        }
    }

    public int solve1() {
        int sum = 0;
        for (Sequence sequence : sequences) {
            sum += sequence.extrapolate();
        }
        return sum;
    }

    public int solve2() {
        int sum = 0;
        for (Sequence sequence : sequences) {
            sum += sequence.extrapolateBackwards();
        }
        return sum;
    }
    


    public class Sequence {
        public final List<Integer> values;
        public Integer first;
        public Integer last;
        public Sequence subsequence;

        public Sequence(String sequence) {
            this();
            for (String value : sequence.split(" ")) {
                append(Integer.parseInt(value));
            }
        }

        public Sequence() {
            this.values = new LinkedList<>();
        }

        public void append(int value) {
            values.add(value);
            if (last == null) {
                first = value;
                last = value;
                return;
            }
            if (subsequence == null) {
                subsequence = new Sequence();
            }
            subsequence.append(value - last);
            last = value;
        }

        public int extrapolate() {
            if (subsequence != null) {
                return last + subsequence.extrapolate();
            }
            return 0;
        }

        public int extrapolateBackwards() {
            if (subsequence != null) {
                return first - subsequence.extrapolateBackwards();
            }
            return 0;
        }
    }
}
