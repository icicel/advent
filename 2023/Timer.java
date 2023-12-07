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
    public static void time(int dayNumber) throws Exception {
        try {
            Class<?> day = Class.forName("Day" + dayNumber);
            List<Long> total = new LinkedList<>();
            List<Long> init = new LinkedList<>();
            List<Long> part1 = new LinkedList<>();
            List<Long> part2 = new LinkedList<>();
            long finish = System.nanoTime() + seconds * 1000000000;
            do {
                long totalStart = System.nanoTime();

                long start = System.nanoTime();
                Object dayObject = day.getDeclaredConstructor().newInstance();
                init.add(System.nanoTime() - start);

                start = System.nanoTime();
                day.getMethod("solve1").invoke(dayObject);
                part1.add(System.nanoTime() - start);

                start = System.nanoTime();
                day.getMethod("solve2").invoke(dayObject);
                part2.add(System.nanoTime() - start);

                total.add(System.nanoTime() - totalStart);
            } while (System.nanoTime() < finish);
            System.out.println("Day " + dayNumber);
            System.out.println(" init: " + average(init) + "ms");
            System.out.println(" part 1: " + average(part1) + "ms");
            System.out.println(" part 2: " + average(part2) + "ms");
            System.out.println(" in total: " + average(total) + "ms");
        } catch (ClassNotFoundException e) {
            // silently skip
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
