def pathfind(start, end, blizzards, bearings):
    minutes = 0
    paths = [start]
    while True:
        minutes += 1
        for i, (blizzard, bearing) in enumerate(zip(blizzards, bearings)):
            x, y = blizzard
            dx, dy = bearing
            blizzards[i] = ((x + dx) % width, (y + dy) % height)
        newpaths = []
        for path in paths:
            x, y = path
            for dx, dy in [(1, 0), (-1, 0), (0, 1), (0, -1), (0, 0)]:
                newpath = (x + dx, y + dy)
                if newpath in blizzards:
                    continue
                if (x + dx < 0 or x + dx >= width or y + dy < 0 or y + dy >= height) and newpath not in [start, end]:
                    continue
                if newpath == end:
                    return minutes
                if newpath not in newpaths:
                    newpaths.append(newpath)
        paths = newpaths
blizzards = []
bearings = []
height = 0
for y, line in enumerate(open("2022\\24.txt").read().splitlines()[1:-1]):
    width = len(line) - 2
    height += 1
    for x, c in enumerate(line[1:-1]):
        if c != ".":
            blizzards.append((x, y))
            bearings.append({">": (1, 0), "<": (-1, 0), "^": (0, -1), "v": (0, 1)}[c])
start = (0, -1)
end = (width - 1, height)
a = pathfind(start, end, blizzards, bearings)
b = pathfind(end, start, blizzards, bearings)
c = pathfind(start, end, blizzards, bearings)
print(a)
print(a + b + c)