import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import base.Input;

// Gear Ratios
// 557705
// 84266818
public class Day3 {
    public static void main(String[] args) {
        Day3 main = new Day3();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final Engine engine;

    public Day3() {
        engine = new Engine();
        for (String line : Input.lines(3)) {
            engine.addRow(line);
        }
    }

    public int solve1() {
        int sum = 0;
        for (Number number : engine.getPartNumbers()) {
            sum += number.value;
        }
        return sum;
    }

    public int solve2() {
        int sum = 0;
        for (int gearRatio : engine.getGearRatios()) {
            sum += gearRatio;
        }
        return sum;
    }
    


    // Kind of a handler for List<Row>?
    public class Engine {
        public final List<Row> rows;
        public Row lastRow;

        public Engine() {
            rows = new LinkedList<>();
        }

        public void addRow(String rowString) {
            Row row = new Row(rowString, rows.size());
            if (lastRow != null) {
                lastRow.below = row;
                row.above = lastRow;
            }
            rows.add(row);
            lastRow = row;
        }

        public List<Number> getPartNumbers() {
            List<Number> partNumbers = new LinkedList<>();
            for (Row row : engine.rows) {
                partNumbers.addAll(row.getPartNumbers());
            }
            return partNumbers;
        }

        public List<Integer> getGearRatios() {
            List<Integer> gearRatios = new LinkedList<>();
            for (Row row : engine.rows) {
                gearRatios.addAll(row.getGearRatios());
            }
            return gearRatios;
        }
    }



    // One row of an engine
    // Can communicate with the rows above and below, if set
    public class Row {
        public final Number[] row;
        public final List<Symbol> symbols;
        public Row above;
        public Row below;

        public Row(String rowString, int y) {
            row = new Number[rowString.length()];
            symbols = new LinkedList<>();
            String buffer = "";
            for (int i = 0; i < rowString.length(); i++) {
                char current = rowString.charAt(i);
                if (Character.isDigit(current)) {
                    buffer += current;
                    continue;
                }
                // current is not a digit
                if (buffer != "") {
                    Number number = new Number(buffer);
                    row[i - 1] = number;
                    row[i - buffer.length()] = number;
                    buffer = "";
                }
                if (current != '.') {
                    symbols.add(new Symbol(current, i));
                }
            }
            if (buffer != "") {
                int i = rowString.length();
                Number number = new Number(buffer);
                row[i - 1] = number;
                row[i - buffer.length()] = number;
            }
        }

        // Return the number at the position
        // Return null if invalid position or there is no number there
        public Number numberAt(int pos) {
            if (pos < 0 || pos >= row.length) {
                return null;
            }
            return row[pos];
        }

        // Put all numbers near this row's symbols in a set
        // If there is no number at some position (null) it will add it anyway
        public Set<Number> getPartNumbers() {
            Set<Number> set = new HashSet<>();
            for (Symbol symbol : symbols) {
                set.addAll(getNearbyNumbers(symbol));
            }
            return set;
        }

        // A gear ratio is the two nearby numbers of a gear multiplied together
        public List<Integer> getGearRatios() {
            List<Integer> gearRatios = new LinkedList<>();
            for (Symbol symbol : symbols) {
                List<Number> nearbyNumbers = getNearbyNumbersIfGear(symbol);
                if (nearbyNumbers == null) {
                    continue;
                }
                // This list is guaranteed to be size 2
                gearRatios.add(nearbyNumbers.get(0).value * nearbyNumbers.get(1).value);
            }
            return gearRatios;
        }

        // Scan around the symbol for numbers (including above and below)
        // Assumes the symbol is in this row
        public List<Number> getNearbyNumbers(Symbol symbol) {
            Set<Number> set = new HashSet<>();
            int pos = symbol.pos;
            set.add(numberAt(pos + 1));
            set.add(numberAt(pos - 1));
            // in line above
            if (above != null) {
                set.add(above.numberAt(pos + 1));
                set.add(above.numberAt(pos));
                set.add(above.numberAt(pos - 1));
            }
            // in line below
            if (below != null) {
                set.add(below.numberAt(pos + 1));
                set.add(below.numberAt(pos));
                set.add(below.numberAt(pos - 1));
            }
            set.remove(null);
            return new LinkedList<>(set);
        }

        // Assumes the symbol is in this row
        // Return null if not a gear
        public List<Number> getNearbyNumbersIfGear(Symbol symbol) {
            List<Number> nearbyNumbers = getNearbyNumbers(symbol);
            if (symbol.type != '*' || nearbyNumbers.size() != 2) {
                return null;
            }
            return nearbyNumbers;
        }
    }



    // Is a gear if type = '*' and the amount of nearby numbers is 2
    public class Symbol {
        public final char type;
        public final int pos;

        public Symbol(char symbol, int pos) {
            this.type = symbol;
            this.pos = pos;
        }
    }



    // It's a number.
    // Is a part number if next to a symbol
    public class Number {
        public final int value;

        public Number(String string) {
            this.value = Integer.parseInt(string);
        }
    }
}
