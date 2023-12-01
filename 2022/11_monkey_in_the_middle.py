monkeys = [
    [0, lambda x: x * 7, lambda x: x % 13 == 0, 1, 3, [64]],
    [0, lambda x: x + 7, lambda x: x % 19 == 0, 2, 7, [60, 84, 84, 65]],
    [0, lambda x: x * 3, lambda x: x % 5 == 0,  5, 7, [52, 67, 74, 88, 51, 61]],
    [0, lambda x: x + 3, lambda x: x % 2 == 0,  1, 2, [67, 72]],
    [0, lambda x: x * x, lambda x: x % 17 == 0, 6, 0, [80, 79, 58, 77, 68, 74, 98, 64]],
    [0, lambda x: x + 8, lambda x: x % 11 == 0, 4, 6, [62, 53, 61, 89, 86]],
    [0, lambda x: x + 2, lambda x: x % 7 == 0,  3, 0, [86, 89, 82]],
    [0, lambda x: x + 4, lambda x: x % 3 == 0,  4, 5, [92, 81, 70, 96, 69, 84, 83]],
]
from copy import deepcopy
def monkey_business(m, n, f):
    monkeys = deepcopy(m)
    for _ in range(n):
        for monkey in monkeys:
            for item in monkey[5]:
                item = f(monkey[1](item))
                if monkey[2](item):
                    monkeys[monkey[3]][5].append(item)
                else:
                    monkeys[monkey[4]][5].append(item)
                monkey[0] += 1
            monkey[5] = []
    monkeys.sort()
    return monkeys[-1][0] * monkeys[-2][0]
print(monkey_business(monkeys, 20, lambda x: x // 3))
print(monkey_business(monkeys, 10000, lambda x: x % 9699690))
