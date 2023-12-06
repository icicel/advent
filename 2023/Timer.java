import java.util.LinkedList;
import java.util.List;

// Quick script to time any day
public class Timer {

    public static final long seconds = 5; // how many seconds to time for

    public static void main(String[] args) throws Exception {
        for (int day = 1; day <= 25; day++) {
            time(day);
        }
    }

    // featuring some hacky ways of getting vscode to stop complaining
    public static void time(int dayN) throws Exception {
        try {
            Class<?> dayX = Class.forName("Day" + dayN);
            Object day = dayX.getDeclaredConstructor().newInstance();
            List<Long> part1 = new LinkedList<>();
            List<Long> part2 = new LinkedList<>();
            long finish = System.nanoTime() + seconds * 1000000000;
            do {
                long start = System.nanoTime();
                dayX.getMethod("solve1").invoke(day);
                part1.add(System.nanoTime() - start);
                day = dayX.getDeclaredConstructor().newInstance();
                start = System.nanoTime();
                dayX.getMethod("solve2").invoke(day);
                part2.add(System.nanoTime() - start);
                day = dayX.getDeclaredConstructor().newInstance();
            } while (System.nanoTime() < finish);
            System.out.println("Day " + dayN);
            System.out.println(" part 1: " + average(part1) + "ms");
            System.out.println(" part 2: " + average(part2) + "ms");
        } catch (ClassNotFoundException e) {
            // finish
        }
    }

    // in ms
    public static double average(List<Long> longs) {
        long sum = 0;
        for (Long l : longs) {
            sum += l;
        }
        return (sum / longs.size()) / 1000000.0;
    }
}
