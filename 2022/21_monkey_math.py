src = [line.split(": ") for line in open("2022\\21.txt").read().splitlines()]
vars = {}
while True:
    for var, val in src:
        if " " not in val:
            if var not in vars:
                vars[var] = int(val)
            continue
        a1, op, a2 = val.split()
        if a1 in vars and a2 in vars and var not in vars:
            vars[var] = int(eval(str(vars[a1]) + op + str(vars[a2])))
            continue
    if "root" in vars:
        print(vars["root"])
        break

x = 3952673930912
vars = {}
while True:
    for var, val in src:
        if " " not in val:
            if var not in vars:
                vars[var] = x if var == "humn" else int(val)
            continue
        a1, op, a2 = val.split()
        if a1 in vars and a2 in vars and var not in vars:
            if var == "root" and vars[a1] == vars[a2]:
                print(x)
                quit()
            vars[var] = int(eval(str(vars[a1]) + op + str(vars[a2])))
            continue