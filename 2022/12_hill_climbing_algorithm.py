map = open('2022\\12.txt').read().splitlines()
def getsteps(start):
    paths = [start]
    visited = [start]
    step = 0
    end = False
    while True:
        new = []
        step += 1
        for x, y in paths:
            sq = map[y][x]
            if sq == "S": sq = "a"
            for dx, dy in ((0, 1), (0, -1), (1, 0), (-1, 0)):
                if x + dx < 0 or x + dx >= len(map[0]) or y + dy < 0 or y + dy >= len(map):
                    continue
                dsq = map[y + dy][x + dx]
                if dsq == "E": 
                    dsq = "z"
                    end = True
                if (x + dx, y + dy) not in visited and (x + dx, y + dy) not in new and ord(dsq) <= ord(sq) + 1:
                    if end:
                        return step
                    new.append((x + dx, y + dy))
                    visited.append((x + dx, y + dy))
                end = False
        paths = new
print(getsteps((0, 20)))
print(min([getsteps((0, y)) for y in range(41)]))