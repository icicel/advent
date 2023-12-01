import itertools
chamber = []
rock_pattern = itertools.cycle([
    ["####"], 
    [".#.", "###", ".#."],
    ["###", "..#", "..#"],
    ["#", "#", "#", "#"],
    ["##", "##"]
])
jet_pattern = itertools.cycle(open("2022\\17.txt").read())
jetd = {">": 1, "<": -1}
rock_amount = 0
def valid_x(x):
    return x >= 0 and x < 7
def valid_rock(rock_x, rock_y):
    for y, rock_layer in enumerate(rock, start=rock_y):
        if y >= len(chamber):
            continue
        for x, rock_tile in enumerate(rock_layer, start=rock_x):
            if rock_tile == "#" and chamber[y][x] == "#":
                return False
    return True
rock = next(rock_pattern)
rock_x = 2
rock_y = len(chamber) + 3
rock_height = len(rock)
rock_width = len(rock[0])
r = 0
while True:
    jet = jetd[next(jet_pattern)]
    if valid_x(rock_x + jet) and valid_x(rock_x + jet + rock_width - 1) and valid_rock(rock_x + jet, rock_y):
        rock_x += jet
    if rock_y == 0 or not valid_rock(rock_x, rock_y - 1):
        r += 1
        for y, rock_layer in enumerate(rock, start=rock_y):
            if y == len(chamber):
                chamber.append(".......")
            chamber_layer = ""
            for x in range(rock_x):
                chamber_layer += chamber[y][x]
            for x, rock_tile in enumerate(rock_layer, start=rock_x):
                if chamber[y][x] == "." and rock_tile == "#":
                    chamber_layer += "#"
                else:
                    chamber_layer += chamber[y][x]
            while len(chamber_layer) != 7:
                x += 1
                chamber_layer += chamber[y][x]
            chamber[y] = chamber_layer
        if r == 2022:
            print(len(chamber))
        if r == 1449 + 2746:
            print(len(chamber) + ((1000000000000 - r) // 1695) * 2610)
            break
        rock = next(rock_pattern)
        rock_x = 2
        rock_y = len(chamber) + 3
        rock_height = len(rock)
        rock_width = len(rock[0])
    else:
        rock_y -= 1
