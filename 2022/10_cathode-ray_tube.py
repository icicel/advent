cycle = 1
x = 1
sum = 0
def check():
    global sum
    if cycle in [20, 60, 100, 140, 180, 220]:
        sum += x * cycle
for instr in open("2022\\10.txt").read().splitlines():
    check()
    cycle += 1
    if instr == "noop":
        continue
    check()
    cycle += 1
    x += int(instr.split(" ")[1])
print(sum)

x = 1
image = ""
imgx = 0
def draw():
    global image, imgx
    if abs(x - imgx) <= 1:
        image += "#"
    else:
        image += "."
    imgx += 1
    if imgx == 40:
        image += "\n"
        imgx = 0
for instr in open("2022\\10.txt").read().splitlines():
    draw()
    if instr == "noop":
        continue
    draw()
    x += int(instr.split(" ")[1])
print(image)