best_elf = 0
current_elf = 0
for line in open("2022\\1.txt").read().splitlines():
    if line:
        current_elf += int(line)
    else:
        best_elf = max(best_elf, current_elf)
        current_elf = 0
print(best_elf)

top_elves = [0, 0, 0]
current_elf = 0
for line in open("2022\\1.txt").read().splitlines():
    if line:
        current_elf += int(line)
    else:
        top_elves.append(current_elf)
        top_elves.sort(reverse=True)
        top_elves.pop()
        current_elf = 0
print(sum(top_elves))