c = 0
for line in open("2022\\4.txt").read().splitlines():
    r1, r2 = line.split(",")
    r1 = [int(n) for n in r1.split("-")]
    r2 = [int(n) for n in r2.split("-")]
    if (r1[0] <= r2[0] and r1[1] >= r2[1]) or (r1[0] >= r2[0] and r1[1] <= r2[1]):
        c += 1
print(c)

c = 0
for line in open("2022\\4.txt").read().splitlines():
    r1, r2 = line.split(",")
    r1 = [int(n) for n in r1.split("-")]
    r2 = [int(n) for n in r2.split("-")]
    if r1[1] >= r2[0] and r2[1] >= r1[0]:
        c += 1
print(c)