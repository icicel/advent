priorities = {}
for i, c in enumerate("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", start=1):
    priorities[c] = i
sum = 0
for line in open("2022\\3.txt").read().splitlines():
    r1 = line[:len(line)//2]
    r2 = line[len(line)//2:]
    for c in r1:
        if c in r2:
            sum += priorities[c]
            break
print(sum)

priorities = {}
for i, c in enumerate("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", start=1):
    priorities[c] = i
sum = 0
r1 = None
r2 = None
for line in open("2022\\3.txt").read().splitlines():
    if not r1:
        r1 = line
        continue
    if not r2:
        r2 = line
        continue
    for c in line:
        if c in r1 and c in r2:
            sum += priorities[c]
            r1 = r2 = None
            break
print(sum)