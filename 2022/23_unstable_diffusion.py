state = [line for line in open("2022\\23.txt").read().splitlines()]
elves = set()
for y, row in enumerate(state.__reversed__()):
    for x, tile in enumerate(row):
        if tile == "#":
            elves.add((x, y))
directionlist = [(0, 1, 2, (0, 1)), (4, 5, 6, (0, -1)), (6, 7, 0, (-1, 0)), (2, 3, 4, (1, 0))]
round = 1
while True:
    neighbord = {}
    for e in elves:
        x, y = e
        neighbord[e] = []
        for dx, dy in [(-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1), (-1, -1), (-1, 0)]:
            if (x + dx, y + dy) in elves:
                neighbord[e].append(True)
            else:
                neighbord[e].append(False)
    proposed = {}
    for e in elves:
        neighbors = neighbord[e]
        if not any(neighbors):
            proposed[e] = e
            continue
        for d1, d2, d3, (dx, dy) in directionlist:
            if not (neighbors[d1] or neighbors[d2] or neighbors[d3]):
                x, y = e
                proposed[e] = (x + dx, y + dy)
                break
        else:
            proposed[e] = e
    newelves = set()
    proposeds = list(proposed.values())
    invalids = [c for c in proposeds if proposeds.count(c) != 1]
    moved = False
    for e, de in proposed.items():
        if e == de or de in invalids:
            newelves.add(e)
        else:
            newelves.add(de)
            moved = True
    if not moved:
        break
    elves = newelves
    directionlist = directionlist[1:] + [directionlist[0]]
    round += 1
    if round == 10:
        maxx = max([x for x, _ in elves])
        maxy = max([y for _, y in elves])
        minx = min([x for x, _ in elves])
        miny = min([y for _, y in elves])
        print(abs(maxx - minx + 1) * abs(maxy - miny + 1) - len(elves))
print(round)