dirs = {}
for cmd in open("2022\\7.txt").read().splitlines():
    cmd = cmd.split(" ")
    if cmd[:2] == ["$", "cd"]:
        arg = cmd[2]
        if arg == "..":
            current.pop()
        elif arg == "/":
            current = [""]
        else:
            current.append(arg)
    elif cmd[0][0] in "1234567890":
        for i in range(len(current)):
            file = "/".join(current[:i+1])
            if file not in dirs:
                dirs[file] = int(cmd[0])
            else:
                dirs[file] += int(cmd[0])
print(sum([v for v in dirs.values() if v <= 100000]))
print(min([v for v in dirs.values() if v >= 30000000 - 70000000 + dirs[""]]))