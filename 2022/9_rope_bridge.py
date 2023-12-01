head = (0, 0)
tail = (0, 0)
visited = {(0,0),}
for line in open("2022\\9.txt").read().splitlines():
    d, c = line.split(" ")
    c = int(c)
    if d == "U":
        v = (0,1)
    elif d == "D":
        v = (0,-1)
    elif d == "L":
        v = (-1,0)
    elif d == "R":
        v = (1,0)
    for _ in range(c):
        head = (head[0] + v[0], head[1] + v[1])
        dx = head[0] - tail[0]
        dy = head[1] - tail[1]
        if abs(dx) > 1 or abs(dy) > 1:
            if not dx:
                tail = (tail[0], tail[1] + dy//2)
            elif not dy:
                tail = (tail[0] + dx//2, tail[1])
            else:
                tail = (tail[0] + dx//abs(dx), tail[1] + dy//abs(dy))
            if tail not in visited:
                visited.add(tail)
print(len(visited))

rope = [(0,0) for _ in range(10)]
visited = {(0,0),}
for line in open("2022\\9.txt").read().splitlines():
    d, c = line.split(" ")
    c = int(c)
    if d == "U":
        v = (0,1)
    elif d == "D":
        v = (0,-1)
    elif d == "L":
        v = (-1,0)
    elif d == "R":
        v = (1,0)
    for _ in range(c):
        rope[0] = (rope[0][0] + v[0], rope[0][1] + v[1])
        for i in range(1, 10):
            dx = rope[i-1][0] - rope[i][0]
            dy = rope[i-1][1] - rope[i][1]
            if abs(dx) > 1 or abs(dy) > 1:
                if not dx:
                    rope[i] = (rope[i][0], rope[i][1] + dy//2)
                elif not dy:
                    rope[i] = (rope[i][0] + dx//2, rope[i][1])
                else:
                    rope[i] = (rope[i][0] + dx//abs(dx), rope[i][1] + dy//abs(dy))
        if rope[9] not in visited:
            visited.add(rope[9])
print(len(visited))