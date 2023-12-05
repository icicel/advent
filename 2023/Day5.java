import java.util.LinkedList;
import java.util.List;

import base.Input;

// If You Give A Seed A Fertilizer
// 535088217
// 51399228
public class Day5 {
    public static void main(String[] args) {
        Day5 main = new Day5();
        System.out.println(main.solve1());
        System.out.println(main.solve2());
    }



    public final Almanac almanac;

    public Day5() {
        String input = Input.string(5);
        String[] splitInput = input.split("\n\n", 2);
        String[] maps = splitInput[1].split("\n\n");
        almanac = new Almanac(splitInput[0], maps);
    }

    public long solve1() {
        long smallest = Long.MAX_VALUE;
        for (Long seed : almanac.seeds) {
            smallest = Long.min(smallest, almanac.getFinalValue(seed));
        }
        return smallest;
    }

    public long solve2() {
        long smallest = Long.MAX_VALUE;
        for (Range range : almanac.seedRanges) {
            for (Range finalRange : almanac.getFinalRanges(range)) {
                // The start value of a range is always the smallest in the range
                smallest = Long.min(smallest, finalRange.start);
            }
        }
        return smallest;
    }
    


    // Contains a list of starting seed numbers and a list of maps between categories
    // The seed numbers can also be paired and used as ranges
    public class Almanac {
        public final List<Long> seeds;
        public final List<Range> seedRanges;
        public final List<MultiRangeMap> maps;

        public Almanac(String seedString, String[] maps) {
            this.seeds = new LinkedList<>();
            this.seedRanges = new LinkedList<>();
            this.maps = new LinkedList<>();

            // parse seeds
            String[] seeds = seedString.substring(7).split(" ");
            for (String seed : seeds) {
                this.seeds.add(Long.parseLong(seed));
            }

            // parse seed pairs
            for (int i = 0; i < seeds.length; i += 2) {
                long start = Long.parseLong(seeds[i]);
                long length = Long.parseLong(seeds[i + 1]);
                this.seedRanges.add(new Range(start, start + length - 1));
            }

            // parse maps
            for (String map : maps) {
                // remove map title
                String[] mapSplit = map.split("\n", 2);
                this.maps.add(new MultiRangeMap(mapSplit[1]));
            }
        }

        // Value of a seed after having been converted through all maps
        public long getFinalValue(Long seed) {
            if (!seeds.contains(seed)) {
                return -1;
            }
            long result = seed;
            for (MultiRangeMap map : maps) {
                result = map.map(result);
            }
            return result;
        }

        // Value of a range
        // Outputs many result ranges, as the input range is split everytime it overlaps with
        // the start or end of a RangeMap
        public List<Range> getFinalRanges(Range startRange) {
            List<Range> subranges = new LinkedList<>();
            subranges.add(startRange);

            for (MultiRangeMap map : maps) {
                List<Range> result = new LinkedList<>();
                for (Range range : subranges) {
                    result.addAll(map.mapRange(range));
                }
                subranges = result;
            }
            return subranges;
        }
    }



    // Represents a map, or rather a series of range maps
    // If a number n is in any of the range maps, it is mapped from range.from to range.to
    // Otherwise, it is mapped to itself
    public class MultiRangeMap {
        public final List<RangeMap> rangeMaps;

        public MultiRangeMap(String map) {
            this.rangeMaps = new LinkedList<>();

            String[] ranges = map.split("\n");
            for (String range : ranges) {
                this.rangeMaps.add(new RangeMap(range));
            }
            // sort range maps by from.start
            this.rangeMaps.sort((r1, r2) -> Long.signum(r1.from.start - r2.from.start));
        }

        // Map a number according to range maps
        public long map(long n) {
            for (RangeMap range : rangeMaps) {
                if (range.from.contains(n)) {
                    return n + range.difference;
                }
            }
            return n;
        }

        // Map an entire range
        // Returns multiple ranges for if the range overlaps with the start or end of a range map
        public List<Range> mapRange(Range range) {
            List<Range> ranges = new LinkedList<>();

            // keep track of the starting point of the unmapped part of the input range
            long low = range.start;

            // this.rangeMaps is assumed sorted by rangeMap -> rangeMap.from.start
            // so smallest (as in leftmost on the number line) range map first, and range maps don't overlap
            for (RangeMap rangeMap : this.rangeMaps) {
                if (low > rangeMap.from.end) {
                    continue;
                } else if (low < rangeMap.from.start) {
                    ranges.add(new Range(low, rangeMap.from.start - 1));
                    low = rangeMap.from.start;
                }

                if (range.end < rangeMap.from.end) {
                    ranges.add(new Range(low + rangeMap.difference, range.end + rangeMap.difference));
                    low = range.end + 1;
                    break;
                }
                ranges.add(new Range(low + rangeMap.difference, rangeMap.to.end));
                low = rangeMap.from.end + 1;
            }
            // if some of the range is still unmapped
            if (low != range.end + 1) {
                ranges.add(new Range(low, range.end));
            }

            return ranges;
        }
    }



    // An inclusive range from start to end, or [start, end] mathematically speaking
    public class Range {
        public final long start;
        public final long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public boolean contains(long n) {
            return n >= start && n <= end;
        }
    }



    // Maps one range to another range
    public class RangeMap {
        public final Range from;
        public final Range to;
        public final long length;
        public final long difference;

        public RangeMap(String rangeString) {
            String[] rangeSplit = rangeString.split(" ");
            long toStart = Long.parseLong(rangeSplit[0]);
            long fromStart = Long.parseLong(rangeSplit[1]);
            this.length = Long.parseLong(rangeSplit[2]);
            
            this.from = new Range(fromStart, fromStart + length - 1);
            this.to = new Range(toStart, toStart + length - 1);
            this.difference = toStart - fromStart;
        }
    }
}
