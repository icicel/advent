import java.util.LinkedList;
import java.util.List;

import base.Input;

// Wait For It
// 1084752
// 28228952
public class Day6 {
    public static void main(String[] args) {
        Day6 main = new Day6();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final List<Race> races;
    public final Race kernedRace;

    public Day6() {
        String[] lines = Input.string(6).split("\n");

        String[] times = lines[0].split(" +");
        String[] records = lines[1].split(" +");
        races = new LinkedList<>();
        // skip first entry in the arrays (the title)
        for (int i = 1; i < times.length; i++) {
            races.add(new Race(times[i], records[i]));
        }

        String kernedTime = lines[0].substring(5).replace(" ", "");
        String kernedRecord = lines[1].substring(9).replace(" ", "");
        kernedRace = new Race(kernedTime, kernedRecord);
    }

    public long solve1() {
        long product = 1;
        for (Race race : races) {
            long waysToWin = race.getLongestWinTime() - race.getShortestWinTime() + 1;
            product *= waysToWin;
        }
        return product;
    }

    public long solve2() {
        return kernedRace.getLongestWinTime() - kernedRace.getShortestWinTime() + 1;
    }
    


    // If holding the button for a ms, the boat is accelerated to b mm/ms
    //  and travels c = b*(t-a) mm where t is the total race time
    // a = b -> c = at-a^2
    // With r as the record distance, c > r -> -a^2+at-r > 0
    //  -> t/2 - sqrt(t^2 - 4r)/2 < a < t/2 + sqrt(t^2 - 4r)/2
    public class Race {
        public final long time;
        public final long record;

        public Race(String time, String record) {
            this.time = Long.parseLong(time);
            this.record = Long.parseLong(record);
        }

        // Shortest time to hold the button to win
        // In order to guarantee a beaten record (c = r is invalid), add 1 to c and floor it
        public long getShortestWinTime() {
            return (long) (Math.floor((time - Math.sqrt(time*time - 4*record))/2) + 1);
        }

        // Longest --||--
        // Reverse of getShortest
        public long getLongestWinTime() {
            return (long) (Math.ceil((time + Math.sqrt(time*time - 4*record))/2) - 1);
        }
    }
}
