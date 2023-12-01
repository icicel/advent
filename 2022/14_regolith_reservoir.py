solid = {}
for line in open("2022\\14.txt").read().splitlines():
    rocks = [(int(p.split(",")[0]), int(p.split(",")[1])) for p in line.split(" -> ")]
    for p1, p2 in zip(rocks, rocks[1:]):
        x1, y1 = p1
        x2, y2 = p2
        if x1 == x2:
            for y in range(min(y1, y2), max(y1, y2) + 1):
                solid[(x1, y)] = True
        else:
            for x in range(min(x1, x2), max(x1, x2) + 1):
                solid[(x, y1)] = True
settled = False
sand = (500, 0)
void = max(p[1] for p in solid) + 1
solid2 = solid.copy()
while True:
    if sand[1] == void:
        break
    for fallv in [(0,1), (-1,1), (1,1)]:
        new = (sand[0] + fallv[0], sand[1] + fallv[1])
        if new not in solid:
            sand = new
            break
    else:
        solid[sand] = False
        sand = (500, 0)
print(sum([1 for s in solid.values() if s == False]))

solid = solid2
while True:
    if sand[1] == void:
        solid[sand] = False
        sand = (500, 0)
    for fallv in [(0,1), (-1,1), (1,1)]:
        new = (sand[0] + fallv[0], sand[1] + fallv[1])
        if new not in solid:
            sand = new
            break
    else:
        solid[sand] = False
        if sand == (500, 0):
            break
        sand = (500, 0)
print(sum([1 for s in solid.values() if s == False]))