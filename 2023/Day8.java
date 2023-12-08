import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import base.Input;

// Haunted Wasteland
// 16579
// 12927600769609
public class Day8 {
    public static void main(String[] args) {
        Day8 main = new Day8();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final String instructions;
    public final int cycleLength;
    public final List<Node> nodes;
    public final List<Node> starts;
    public final Map<String, Node> nameMap;

    public Day8() {
        nodes = new LinkedList<>();
        starts = new LinkedList<>();
        nameMap = new HashMap<>();

        String[] input = Input.string(8).split("\n\n");
        instructions = input[0];
        cycleLength = instructions.length();

        // parse nodes
        for (String line : input[1].split("\n")) {
            String name = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);
            Node node = new Node(name, left, right);
            nodes.add(node);
            nameMap.put(node.name, node);
            if (node.start) {
                starts.add(node);
            }
        }

        // connect nodes
        for (Node node : nodes) {
            node.left = nameMap.get(node.leftName);
            node.right = nameMap.get(node.rightName);
        }

        // find afterCycle nodes
        for (Node node : nodes) {
            Node current = node;
            for (int i = 0; i < cycleLength; i++) {
                char instruction = instructions.charAt(i);
                if (instruction == 'L') {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            node.afterCycle = current;
        }
    }

    public int solve1() {
        int steps = 0;
        Node current = nameMap.get("AAA");
        Node goal = nameMap.get("ZZZ");
        while (current != goal) {
            current = current.afterCycle;
            steps++;
        }
        return steps * cycleLength;
    }

    public long solve2() {
        long product = 1;
        // calculate how many complete instruction cycles need to be done from this node to arrive at ANY goal node
        for (Node node : starts) {
            Node current = node;
            long cycles = 0;
            while (!current.goal) {
                current = current.afterCycle;
                cycles++;
            }
            product *= cycles;
        }
        // why tf is this correct?????
        return product * cycleLength;
    }
    


    // A node has a left connection and a right connection
    // The afterCycle node is the node arrived at if starting an instruction cycle at this node
    public class Node {
        public final String name;
        public final String leftName;
        public final String rightName;
        public final boolean start;
        public final boolean goal;
        public Node left;
        public Node right;
        public Node afterCycle;

        public Node(String name, String left, String right) {
            this.name = name;
            this.leftName = left;
            this.rightName = right;
            if (name.endsWith("Z")) {
                this.goal = true;
                this.start = false;
            } else if (name.endsWith("A")) {
                this.start = true;
                this.goal = false;
            } else {
                this.start = false;
                this.goal = false;
            }
        }

        public String toString() {
            return this.name;
        }
    }
}
