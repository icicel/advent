stackin = []
instr = []
stacks = []
switch = False
for line in open("2022\\5.txt").read().splitlines():
    if not line:
        switch = True
        continue
    if switch:
        instr.append(line.split(" "))
    else:
        stackin.append(line)
for i in range(0, len(stackin[-1]), 4):
    stacks.append([])
stackin.pop()
for s in stackin:
    for i in range(0, len(s), 4):
        if s[i] == "[":
            stacks[i//4].insert(0, s[i+1])
for _, c, _, frm, _, to in instr:
    c = int(c)
    frm = int(frm)-1
    to = int(to)-1
    for _ in range(c):
        stacks[to].append(stacks[frm].pop())
print("".join([s[-1] for s in stacks]))

stackin = []
instr = []
stacks = []
switch = False
for line in open("2022\\5.txt").read().splitlines():
    if not line:
        switch = True
        continue
    if switch:
        instr.append(line.split(" "))
    else:
        stackin.append(line)
for i in range(0, len(stackin[-1]), 4):
    stacks.append([])
stackin.pop()
for s in stackin:
    for i in range(0, len(s), 4):
        if s[i] == "[":
            stacks[i//4].insert(0, s[i+1])
for _, c, _, frm, _, to in instr:
    c = int(c)
    frm = int(frm)-1
    to = int(to)-1
    tmp = []
    for _ in range(c):
        tmp.insert(0, stacks[frm].pop())
    stacks[to].extend(tmp)
print("".join([s[-1] for s in stacks]))