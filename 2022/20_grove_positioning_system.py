rnumbers = []
for line in open("2022\\20.txt").read().splitlines():
    rnumbers.append(int(line))
numbers = list(enumerate(rnumbers))
def mix():
    i = 0
    j = 0
    while j != len(numbers):
        n = numbers[i]
        if n[0] != j:
            i = (i + 1) % len(numbers)
            continue
        numbers.pop(i)
        numbers.insert((i + n[1]) % len(numbers), n)
        j += 1
mix()
numbers = [n[1] for n in numbers]
i = numbers.index(0)
print(numbers[(i + 1000) % len(numbers)] + numbers[(i + 2000) % len(numbers)] + numbers[(i + 3000) % len(numbers)])

numbers = list(enumerate([n * 811589153 for n in rnumbers]))
for _ in range(10):
    mix()
numbers = [n[1] for n in numbers]
i = numbers.index(0)
print(numbers[(i + 1000) % len(numbers)] + numbers[(i + 2000) % len(numbers)] + numbers[(i + 3000) % len(numbers)])