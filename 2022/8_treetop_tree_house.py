trees = [line for line in open("2022\\8.txt").read().splitlines()]
visible = set()
for y, row in enumerate(trees):
    top = -1
    for x, tree in enumerate(row):
        tree = int(tree)
        if tree > top:
            visible.add((x, y))
            top = tree
    top = -1
    for x, tree in zip(range(len(row)-1, -1, -1), row[::-1]):
        tree = int(tree)
        if tree > top:
            visible.add((x, y))
            top = tree
for x, column in enumerate(zip(*trees)):
    top = -1
    for y, tree in enumerate(column):
        tree = int(tree)
        if tree > top:
            visible.add((x, y))
            top = tree
    top = -1
    for y, tree in zip(range(len(column)-1, -1, -1), column[::-1]):
        tree = int(tree)
        if tree > top:
            visible.add((x, y))
            top = tree
print(len(visible))

top = -1
def view(x, y, stepx, stepy, base):
    sum = 0
    while True:
        x += stepx
        y += stepy
        if x < 0 or y < 0 or x >= len(trees) or y >= len(trees):
            return sum
        sum += 1
        viewed = int(trees[x][y])
        if viewed >= base:
            return sum
for x, y in [(x, y) for x in range(len(trees)) for y in range(len(trees))]:
    tree = int(trees[x][y])
    score = view(x, y, 1, 0, tree) * view(x, y, -1, 0, tree) * view(x, y, 0, 1, tree) * view(x, y, 0, -1, tree)
    top = max(score, top)
print(top)