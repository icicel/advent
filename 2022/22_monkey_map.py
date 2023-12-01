f = open("2022\\22.txt").read().splitlines()
karta = []
for line in f:
    if not line:
        break
    karta.append(line)
directions = []
buffer = ""
for tile in f[-1]:
    if tile in "LR":
        if buffer:
            directions.append(int(buffer))
            buffer = ""
        directions.append(tile)
        continue
    buffer += tile
if buffer:
    directions.append(int(buffer))
faces = [
    (50, 0),
    (100, 0),
    (50, 50),
    (0, 100),
    (50, 100),
    (0, 150)
]
def realx(x, face):
    return faces[face][0] + x
def realy(y, face):
    return faces[face][1] + y
def rundirections():
    global pos
    for steps in directions:
        x, y, face, dir = pos
        if steps == "L":
            dir = (dir - 1) % 4
        elif steps == "R":
            dir = (dir + 1) % 4
        else:
            for _ in range(steps):
                oldpos = x, y, face, dir
                dx, dy = [(1, 0), (0, 1), (-1, 0), (0, -1)][dir]
                x += dx
                y += dy
                if x > 49 or y > 49 or x < 0 or y < 0:
                    x -= dx
                    y -= dy
                    newface, flipx, flipy, mirror, newdir = wraprules[face][dir]
                    face = newface
                    dir = newdir
                    if flipx:
                        x = 49 - x
                    if flipy:
                        y = 49 - y
                    if mirror:
                        x, y = y, x
                if karta[realy(y,face)][realx(x,face)] == "#":
                    x, y, face, dir = oldpos
                    break
        pos = x, y, face, dir
wraprules = [
    [(1, 1, 0, 0, 0), (2, 0, 1, 0, 1), (1, 1, 0, 0, 2), (4, 0, 1, 0, 3)],
    [(0, 1, 0, 0, 0), (1, 0, 1, 0, 1), (0, 1, 0, 0, 2), (1, 0, 1, 0, 3)],
    [(2, 1, 0, 0, 0), (4, 0, 1, 0, 1), (2, 1, 0, 0, 2), (0, 0, 1, 0, 3)],
    [(4, 1, 0, 0, 0), (5, 0, 1, 0, 1), (4, 1, 0, 0, 2), (5, 0, 1, 0, 3)],
    [(3, 1, 0, 0, 0), (0, 0, 1, 0, 1), (3, 1, 0, 0, 2), (2, 0, 1, 0, 3)],
    [(5, 1, 0, 0, 0), (3, 0, 1, 0, 1), (5, 1, 0, 0, 2), (3, 0, 1, 0, 3)]
]
pos = (0, 0, 0, 0)
rundirections()
print((realy(pos[1],pos[2]) + 1) * 1000 + (realx(pos[0],pos[2]) + 1) * 4 + pos[3])

wraprules = [
    [(1, 1, 0, 0, 0), (2, 0, 1, 0, 1), (3, 0, 1, 0, 0), (5, 0, 0, 1, 0)],
    [(4, 0, 1, 0, 2), (2, 0, 0, 1, 2), (0, 1, 0, 0, 2), (5, 0, 1, 0, 3)],
    [(1, 0, 0, 1, 3), (4, 0, 1, 0, 1), (3, 0, 0, 1, 1), (0, 0, 1, 0, 3)],
    [(4, 1, 0, 0, 0), (5, 0, 1, 0, 1), (0, 0, 1, 0, 0), (2, 0, 0, 1, 0)],
    [(1, 0, 1, 0, 2), (5, 0, 0, 1, 2), (3, 1, 0, 0, 2), (2, 0, 1, 0, 3)],
    [(4, 0, 0, 1, 3), (1, 0, 1, 0, 1), (0, 0, 0, 1, 1), (3, 0, 1, 0, 3)]
]
pos = (0, 0, 0, 0)
rundirections()
print((realy(pos[1],pos[2]) + 1) * 1000 + (realx(pos[0],pos[2]) + 1) * 4 + pos[3])