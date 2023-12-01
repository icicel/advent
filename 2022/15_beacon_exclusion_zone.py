distance_to_beacon = {}
sensors = []
beacons = []
def manhattan(v1, v2):
    return abs(v1[0] - v2[0]) + abs(v1[1] - v2[1])
for line in open("2022\\15.txt").read().splitlines():
    line = line.split(":")
    sx, sy = line[0][12:].split(", y=")
    bx, by = line[1][24:].split(", y=")
    sensors.append((int(sx), int(sy)))
    beacons.append((int(bx), int(by)))
    distance_to_beacon[(int(sx), int(sy))] = manhattan((int(sx), int(sy)), (int(bx), int(by)))
c = 0
for x in range(-2000000, 6000000):
    for sensor, distance in distance_to_beacon.items():
        if manhattan(sensor, (x, 2000000)) <= distance:
            c += 1
            break
print(c)

possible = set()
for s in sensors:
    for c in zip(range(s[0]-distance_to_beacon[s]-1, s[0]), range(s[1], s[1]-distance_to_beacon[s]-1, -1)):
        for s in sensors:
            if manhattan(c, s) <= distance_to_beacon[s]:
                break
        else:
            possible.add(c)
            break
for p in possible:
    if p[0] >= 0 and p[0] <= 4000000 and p[1] >= 0 and p[1] <= 4000000:
        print(p[0] * 4000000 + p[1])
        break