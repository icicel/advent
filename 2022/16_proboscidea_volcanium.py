flow_rate = {}
connections = {}
valves = []
active_valves = []
start = "AA"
for line in open("2022\\16.txt").read().splitlines():
    a, b = line.split(";")
    valve, rate = a[6:].split(" has flow rate=")
    neighbors = b.split(", ")
    neighbors[0] = neighbors[0][-2:]
    flow_rate[valve] = int(rate)
    connections[valve] = neighbors
    valves.append(valve)
    if rate != "0":
        active_valves.append(valve)
time_between = {}
for endvalve in active_valves + [start]:
    time_to_endvalve = {}
    visited = [endvalve]
    endpaths = [endvalve]
    step = 0
    while endpaths:
        step += 1
        new = []
        for valve in endpaths:
            for neighbor in connections[valve]:
                if neighbor not in visited:
                    visited.append(neighbor)
                    new.append(neighbor)
                    if neighbor in active_valves:
                        time_to_endvalve[neighbor] = step + 1
        endpaths = new
    time_between[endvalve] = time_to_endvalve
def pressure(path):
    opened = []
    time = 0
    pressure = 0
    previous = start
    for valve in path[1]:
        elapsed = time_between[previous][valve]
        time += elapsed
        for open in opened:
            pressure += flow_rate[open] * elapsed
        opened.append(valve)
        previous = valve
    for valve in opened:
        pressure += flow_rate[valve] * (max_time - time)
    return pressure
paths = [(0, [], start)]
finished = []
max_time = 30
def search(*path):
    global bestpressure
    if len(path) == 2:
        current = start
    else:
        current = path[-1]
    time, pressure, *way = path
    for neighbor in time_between[current]:
        if neighbor not in way:
            newtime = time + time_between[current][neighbor]
            if newtime > max_time:
                continue
            newway = way + [neighbor]
            newpressure = pressure + flow_rate[neighbor] * (max_time - newtime)
            bestpressure = max(bestpressure, newpressure)
            search(newtime, newpressure, *newway)
bestpressure = 0
search(0, 0)
print(bestpressure)

def dualsearch(time, pressure, way):
    global bestpressure, bestpressure2
    if way:
        current = way[-1]
    else:
        current = start
    for neighbor in time_between[current]:
        if neighbor not in way:
            newtime = time + time_between[current][neighbor]
            if newtime > max_time:
                continue
            newway = way + [neighbor]
            newpressure = pressure + flow_rate[neighbor] * (max_time - newtime)
            bestpressure2 = 0
            bansearch(way, 0, 0, [])
            bestpressure = max(bestpressure, newpressure + bestpressure2)
            dualsearch(newtime, newpressure, newway)
def bansearch(banlist, time, pressure, way):
    global bestpressure2
    if way:
        current = way[-1]
    else:
        current = start
    for neighbor in time_between[current]:
        if neighbor not in way and neighbor not in banlist:
            newtime = time + time_between[current][neighbor]
            if newtime > max_time:
                continue
            newway = way + [neighbor]
            newpressure = pressure + flow_rate[neighbor] * (max_time - newtime)
            bestpressure2 = max(bestpressure2, newpressure)
            bansearch(banlist, newtime, newpressure, newway)
max_time = 26
bestpressure = 0
bestpressure2 = 0
dualsearch(0, 0, [])
print(bestpressure)