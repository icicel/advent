def parse(it):
    l = []
    temp = ""
    for c in it:
        if c == "[":
            l.append(parse(it))
        elif c == "]":
            if temp:
                l.append(int(temp))
                temp = ""
            return l
        elif c == ",":
            if temp:
                l.append(int(temp))
                temp = ""
            continue
        else:
            temp += c
def checkorder(n1, n2):
    if n1 < n2:
        return True
    elif n1 > n2:
        return False
def ordered(l1, l2):
    if l2 and not l1:
        return True
    if l1 and not l2:
        return False
    for n1, n2 in zip(l1, l2):
        if isinstance(n1, list) and isinstance(n2, list):
            test = ordered(n1, n2)
        elif isinstance(n1, list) and isinstance(n2, int):
            test = ordered(n1, [n2])
        elif isinstance(n1, int) and isinstance(n2, list):
            test = ordered([n1], n2)
        else:
            test = checkorder(n1, n2)
        if test is not None:
            return test
    else:
        return checkorder(len(l1), len(l2))
sum = 0
for i, pair in enumerate(open("2022\\13.txt").read().strip().split("\n\n"), start=1):
    small, big = pair.split("\n")
    small = parse(iter(small[1:]))
    big = parse(iter(big[1:]))
    if ordered(small, big):
        sum += i
print(sum)

packets = [parse(iter(line[1:])) for line in open('2022\\13.txt').read().split('\n') if line]
packets.extend([[[2]],[[6]]])
for i in range(1, len(packets)):
    for j in range(i, 0, -1):
        if ordered(packets[j], packets[j-1]):
            packets[j], packets[j-1] = packets[j-1], packets[j]
        else:
            break
print((packets.index([[6]]) + 1) * (packets.index([[2]]) + 1))