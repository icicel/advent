import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import base.Input;

// Pipe Maze
// 7107
// 281
public class Day10 {
    public static void main(String[] args) {
        Day10 main = new Day10();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final Maze maze;
    public final Set<Pipe> loop;
    public final Set<Pipe> leftSide;
    public final Set<Pipe> rightSide;

    public Day10() {
        maze = new Maze(Input.string(10));
        loop = new HashSet<>();
        leftSide = new HashSet<>();
        rightSide = new HashSet<>();
    }

    public int solve1() {
        Pipe start = maze.start;
        Pipe current = start;
        Pipe last = null;
        loop.add(current);
        // solve first part and generate nexts simultaneously
        while (true) {
            Pipe next;
            if (current.connected1 != last) {
                next = current.connected1;
            } else {
                next = current.connected2;
            }
            current.next = next;
            if (next == start) {
                break;
            }

            last = current;
            current = next;
            loop.add(current);
        }
        return loop.size() / 2;
    }

    public int solve2() {
        Pipe current = maze.start;
        while (true) {
            switch (current.type) {
                case 'J':
                    if (current.next == current.left) {
                        addFill(leftSide, current.right);
                        addFill(leftSide, current.down);
                    } else {
                        addFill(rightSide, current.right);
                        addFill(rightSide, current.down);
                    }
                    break;
                case 'F':
                    if (current.next == current.right) {
                        addFill(leftSide, current.up);
                        addFill(leftSide, current.left);
                    } else {
                        addFill(rightSide, current.up);
                        addFill(rightSide, current.left);
                    }
                    break;
                case '7':
                    if (current.next == current.down) {
                        addFill(leftSide, current.up);
                        addFill(leftSide, current.right);
                    } else {
                        addFill(rightSide, current.up);
                        addFill(rightSide, current.right);
                    }
                    break;
                case 'L':
                    if (current.next == current.up) {
                        addFill(leftSide, current.down);
                        addFill(leftSide, current.left);
                    } else {
                        addFill(rightSide, current.down);
                        addFill(rightSide, current.left);
                    }
                    break;
                case '-':
                    if (current.next == current.right) {
                        addFill(leftSide, current.up);
                        addFill(rightSide, current.down);
                    } else {
                        addFill(leftSide, current.down);
                        addFill(rightSide, current.up);
                    }
                    break;
                case '|':
                    if (current.next == current.up) {
                        addFill(leftSide, current.left);
                        addFill(rightSide, current.right);
                    } else {
                        addFill(leftSide, current.right);
                        addFill(rightSide, current.left);
                    }
                    break;
            }
            current = current.next;
            if (current == maze.start) {
                break;
            }
        }
        leftSide.removeAll(loop);
        rightSide.removeAll(loop);
        // check if inside is actually inside
        for (Pipe borderPipe : maze.borders) {
            if (leftSide.contains(borderPipe)) {
                return rightSide.size();
            }
        }
        return leftSide.size();
    }



    // Add pipe and all its neighbors, and its neighbors' neighbors etc. to left/rightside
    public void addFill(Set<Pipe> side, Pipe pipe) {
        if (pipe == null || loop.contains(pipe) || side.contains(pipe)) {
            return;
        }
        side.add(pipe);
        addFill(side, pipe.up);
        addFill(side, pipe.down);
        addFill(side, pipe.left);
        addFill(side, pipe.right);
    }



    // A rectangular matrix of pipes
    public class Maze {
        public final List<List<Pipe>> maze;
        public final List<Pipe> borders;
        public final int height;
        public final int width;
        public Pipe start;

        public Maze(String maze) {
            String[] lines = maze.split("\n");
            this.height = lines.length;
            this.width = lines[0].length();
            this.maze = new ArrayList<>(height);
            this.borders = new LinkedList<>();

            for (int y = 0; y < lines.length; y++) {
                List<Pipe> pipeLine = new ArrayList<>(width);
                this.maze.add(pipeLine);

                String line = lines[y];
                for (int x = 0; x < line.length(); x++) {
                    Pipe pipe = new Pipe(line.charAt(x), get(x, y - 1), get(x - 1, y));
                    if (pipe.type == 'S') {
                        this.start = pipe;
                    }
                    pipeLine.add(pipe);
                    if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                        this.borders.add(pipe);
                    }
                }
            }
        }

        public Pipe get(int x, int y) {
            if (y < 0 || y >= height || x < 0 || x >= width) {
                return null;
            }
            return maze.get(y).get(x);
        }
    }



    // A single-tile pipe
    // Contains its type, neighboring pipes, and connected pipes
    // Also contains a reference to the "next" pipe (as in, chaining next.next.next... will
    //  eventually come back to the root)
    public class Pipe {
        public final char type;
        public Pipe up;
        public Pipe down;
        public Pipe left;
        public Pipe right;
        public Pipe connected1;
        public Pipe connected2;
        public Pipe next; // only relevant for pipes in the main loop

        // Needs the neighboring pipes to the left and above to (potentially) connect to
        public Pipe(char type, Pipe up, Pipe left) {
            this.type = type;
            // above connection
            if (up != null) {
                this.up = up;
                up.down = this;
                if ((this.type == 'S' || this.type == 'J' || this.type == '|' || this.type == 'L') &&
                    (up.type == 'S' || up.type == '7' || up.type == '|' || up.type == 'F')
                ) {
                    connectTo(up);
                    up.connectTo(this);
                }
            }
            // left connection
            if (left != null) {
                this.left = left;
                left.right = this;
                if ((this.type == 'S' || this.type == 'J' || this.type == '-' || this.type == '7') &&
                    (left.type == 'S' || left.type == 'L' || left.type == '-' || left.type == 'F')
                ) {
                    connectTo(left);
                    left.connectTo(this);
                }
            }
        }

        // Connect another pipe to this one
        // A maximum of two can be connected
        public void connectTo(Pipe other) {
            if (connected1 == null) {
                connected1 = other;
            } else if (connected2 == null) {
                connected2 = other;
            }
        }
    }
}
