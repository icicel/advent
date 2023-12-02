import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import base.Input;

// Cube Conundrum
// 2348
// 76008
public class Day2 {
    public static void main(String[] args) {
        Day2 main = new Day2();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final List<Game> games;

    public Day2() {
        this.games = new LinkedList<>();
        for (String line : Input.lines(2)) {
            games.add(new Game(line));
        }
    }

    public int solve1() {
        Bag bag = new Bag(12, 13, 14);
        int sum = 0;
        for (Game game : games) {
            if (game.isValidForBag(bag)) {
                sum += game.id;
            }
        }
        return sum;
    }

    public int solve2() {
        int sum = 0;
        for (Game game : games) {
            sum += game.minimalBag().power();
        }
        return sum;
    }



    // Contains varying amounts of cubes of different colors
    public class Bag {
        public final Map<Color, Integer> cubes;
        
        public Bag(int red, int green, int blue) {
            cubes = new HashMap<>();
            cubes.put(Color.RED, red);
            cubes.put(Color.GREEN, green);
            cubes.put(Color.BLUE, blue);
        }

        // Return true if this bag is a subset ("sub-bag") of another bag
        public boolean isSubbagOf(Bag superbag) {
            boolean result = true;
            for (Color color : Color.values()) {
                result = result && (this.cubes.get(color) <= superbag.cubes.get(color));
            }
            return result;
        }

        // The amounts of red, green, and blue cubes multiplied together
        public int power() {
            // flex
            return cubes.values().stream().reduce((i,j) -> i*j).get();
        }
    }



    // A series of draws from an unknown bag
    // The cubes are put back in the bag after each draw, obviously
    public class Game {
        public final List<Draw> draws;
        public final int id;

        public Game(String game) {
            // split into "Game n" and draws
            String[] splitGame = game.split(": ");
            this.id = Integer.parseInt(splitGame[0].substring(5));

            // split into draws
            String[] draws = splitGame[1].split("; ");
            this.draws = new LinkedList<>();
            for (String draw : draws) {
                this.draws.add(new Draw(draw));
            }
        }

        // Returns true if this game is a possible outcome when using the specified bag
        public boolean isValidForBag(Bag bag) {
            return minimalBag().isSubbagOf(bag);
        }

        // Calculate the smallest possible valid bag for this game
        public Bag minimalBag() {
            return new Bag(
                getMaxOfColor(Color.BLUE), 
                getMaxOfColor(Color.GREEN), 
                getMaxOfColor(Color.RED)
            );
        }

        // Get the max amount of a color that was drawn in any draw
        // Iterated over many colors
        public int getMaxOfColor(Color color) {
            int max = 0;
            for (Draw draw : draws) {
                max = Integer.max(max, draw.amountOf(color));
            }
            return max;
        }
    }



    // A couple of cubes drawn out of a bag
    public class Draw {
        public final Map<Color, Integer> cubes;

        public Draw(String draw) {
            cubes = new HashMap<>();
            String[] cubeSets = draw.split(", ");
            for (String cubeSet : cubeSets) {
                String[] splitSet = cubeSet.split(" ");
                int value = Integer.parseInt(splitSet[0]);
                Color color = Color.parse(splitSet[1]);
                cubes.put(color, value);
            }
        }

        public int amountOf(Color color) {
            if (cubes.containsKey(color)) {
                return cubes.get(color);
            }
            return 0;
        }
    }



    public enum Color {
        RED, GREEN, BLUE;

        public static Color parse(String color) {
            switch (color) {
                case "red":
                    return RED;
                case "green":
                    return GREEN;
                case "blue":
                    return BLUE;
                default:
                    return null;
            }
        }
    }
}
