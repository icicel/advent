import java.util.LinkedList;
import java.util.List;

import base.Input;

// Cosmic Expansion
// 9648398
// 618800410814
public class Day11 {
    public static void main(String[] args) {
        Day11 main = new Day11();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final List<Galaxy> galaxies;
    public final boolean[] occupiedColumn;
    public final boolean[] occupiedRow;
    public final int width;
    public final int height;

    public Day11() {
        galaxies = new LinkedList<>();
        int y = 0;
        int width = 0;
        for (String line : Input.lines(11)) {
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    galaxies.add(new Galaxy(x, y));
                }
            }
            y++;
            width = line.length();
        }
        this.width = width;
        this.height = y;
        this.occupiedColumn = new boolean[width];
        this.occupiedRow = new boolean[height];
        for (Galaxy galaxy : galaxies) {
            occupiedColumn[galaxy.x] = true;
            occupiedRow[galaxy.y] = true;
        }
    }

    public long solve1() {
        int[] xDistance = new int[width];
        int x = 0;
        for (int i = 0; i < width; i++) {
            if (!occupiedColumn[i]) {
                x++;
            }
            xDistance[i] = x++;
        }
        int[] yDistance = new int[height];
        int y = 0;
        for (int i = 0; i < height; i++) {
            if (!occupiedRow[i]) {
                y++;
            }
            yDistance[i] = y++;
        }
        return shortestPathSum(xDistance, yDistance);
    }

    public long solve2() {
        int[] xDistance = new int[width];
        int x = 0;
        for (int i = 0; i < width; i++) {
            if (!occupiedColumn[i]) {
                x += 999999;
            }
            xDistance[i] = x++;
        }
        int[] yDistance = new int[height];
        int y = 0;
        for (int i = 0; i < height; i++) {
            if (!occupiedRow[i]) {
                y += 999999;
            }
            yDistance[i] = y++;
        }
        return shortestPathSum(xDistance, yDistance);
    }



    // Calculate the sum of all shortest paths using the specified metrics
    // Where a metric is an array with a cumulatively increasing distance from (0, 0)
    public long shortestPathSum(int[] xMetric, int[] yMetric) {
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            Galaxy galaxy1 = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                Galaxy galaxy2 = galaxies.get(j);
                if (galaxy1 == galaxy2){
                    continue;
                }
                int dx = Math.abs(xMetric[galaxy1.x] - xMetric[galaxy2.x]);
                int dy = Math.abs(yMetric[galaxy1.y] - yMetric[galaxy2.y]);
                sum += dx + dy;
            }
        }
        return sum;
    }
    


    public class Galaxy {
        public final int x;
        public final int y;

        public Galaxy(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
