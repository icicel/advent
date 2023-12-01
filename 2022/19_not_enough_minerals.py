def increment(production, storage, bot=-1):
    production = list(production)
    storage = list(storage)
    for resource, amount in enumerate(production):
        storage[resource] += amount
    if bot != -1:
        for resource, amount in enumerate(bot_costs[bot]):
            storage[resource] -= amount
        production[bot] += 1
    return tuple(production + storage)
def xbetter(t1, t2):
    for a, b in zip(t1, t2):
        if a < b:
            return False
    return True
def get_best(n, bot_costs):
    paths = {(1, 0, 0, 0, 0, 0, 0, 0),}
    for i in range(n):
        new_paths = set()
        new_compare = {}
        for path in paths:
            production = path[:4]
            storage = path[4:]
            for bot, bot_cost in enumerate(bot_costs):
                for resource, amount in enumerate(bot_cost):
                    if storage[resource] < amount:
                        break
                else:
                    new = increment(production, storage, bot)
                    if new[4:] not in new_compare or xbetter(production, new_compare[new[4:]]):
                        new_compare[new[4:]] = production
                        new_paths.add(increment(production, storage, bot))
            new = increment(production, storage)
            if new[4:] not in new_compare or xbetter(production, new_compare[new[4:]]):
                new_compare[new[4:]] = production
                new_paths.add(increment(production, storage))
        paths = new_paths
    return max([path[-1] for path in paths])
s = 0
for blueprint in open("2022\\19.txt").read().splitlines():
    id, blueprint = blueprint[10:].split(": Each ore robot costs ")
    ore_bot, blueprint = blueprint.split(" ore. Each clay robot costs ")
    clay_bot, blueprint = blueprint.split(" ore. Each obsidian robot costs ")
    obsidian_bot, geode_bot = [bot.split(" ore and ") for bot in blueprint[:-10].split(" clay. Each geode robot costs ")]
    ore_bot = (int(ore_bot), 0, 0, 0)
    clay_bot = (int(clay_bot), 0, 0, 0)
    obsidian_bot = (int(obsidian_bot[0]), int(obsidian_bot[1]), 0, 0)
    geode_bot = (int(geode_bot[0]), 0, int(geode_bot[1]), 0)
    bot_costs = [ore_bot, clay_bot, obsidian_bot, geode_bot]
    id = int(id)
    best = get_best(24, bot_costs)
    s += best * id
print(s)

s = 1
for blueprint in open("2022\\19.txt").read().splitlines()[:3]:
    id, blueprint = blueprint[10:].split(": Each ore robot costs ")
    ore_bot, blueprint = blueprint.split(" ore. Each clay robot costs ")
    clay_bot, blueprint = blueprint.split(" ore. Each obsidian robot costs ")
    obsidian_bot, geode_bot = [bot.split(" ore and ") for bot in blueprint[:-10].split(" clay. Each geode robot costs ")]
    ore_bot = (int(ore_bot), 0, 0, 0)
    clay_bot = (int(clay_bot), 0, 0, 0)
    obsidian_bot = (int(obsidian_bot[0]), int(obsidian_bot[1]), 0, 0)
    geode_bot = (int(geode_bot[0]), 0, int(geode_bot[1]), 0)
    bot_costs = [ore_bot, clay_bot, obsidian_bot, geode_bot]
    id = int(id)
    best = get_best(32, bot_costs)
    s *= best
print(s)