inp = open("2022\\6.txt").read()
c = 0
for i in range(3, len(inp)):
    if inp[i] not in inp[i-3:i]:
        if not c:
            print(i)
            break
        c -= 1
        continue
    c = 3

inp = open("2022\\6.txt").read()
buffer = ""
for i, ch in enumerate(inp):
    if ch in buffer:
        buffer = ""
        continue
    buffer += ch
    if len(buffer) == 14:
        print(i+1)