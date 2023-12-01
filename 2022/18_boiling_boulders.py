cubes = [tuple(int(c) for c in cube.split(",")) for cube in open("2022\\18.txt").read().splitlines()]
area = 0
for x, y, z in cubes:
    for v in [(x+1, y, z), (x-1, y, z), (x, y+1, z), (x, y-1, z), (x, y, z+1), (x, y, z-1)]:
        if v not in cubes:
            area += 1
print(area)

steam = [(0, 0, 0)]
old_steam = steam
while True:
    new_steam = []
    for x, y, z in old_steam:
        for v in [(x+1, y, z), (x-1, y, z), (x, y+1, z), (x, y-1, z), (x, y, z+1), (x, y, z-1)]:
            if v[0] < 0 or v[0] > 21 or v[1] < 0 or v[1] > 21 or v[2] < 0 or v[2] > 21:
                continue
            if v not in cubes and v not in steam and v not in new_steam:
                new_steam.append(v)
    if not new_steam:
        break
    steam.extend(new_steam)
    old_steam = new_steam
inside = []
for x in range(22):
    for y in range(22):
        for z in range(22):
            if (x, y, z) not in cubes and (x, y, z) not in steam and (x, y, z) not in inside:
                inside.append((x, y, z))
for x, y, z in inside:
    for v in [(x+1, y, z), (x-1, y, z), (x, y+1, z), (x, y-1, z), (x, y, z+1), (x, y, z-1)]:
        if v in cubes:
            area -= 1
print(area)