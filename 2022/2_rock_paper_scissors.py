shape_score = {"A": 1, "B": 2, "C": 3, "X": 1, "Y": 2, "Z": 3}
total_score = 0
for line in open("2022\\2.txt").read().splitlines():
    outcome = opponent, you = line.split(" ")
    total_score += shape_score[you]
    if shape_score[opponent] == shape_score[you]:
        total_score += 3
    elif outcome in [["A", "Y"], ["B", "Z"], ["C", "X"]]:
        total_score += 6
print(total_score)

shape_score = {"A": 1, "B": 2, "C": 3}
result_score = {"X": 0, "Y": 3, "Z": 6}
win = {"A": "B", "B": "C", "C": "A"}
lose = {"A": "C", "B": "A", "C": "B"}
total_score = 0
for line in open("2022\\2.txt").read().splitlines():
    opponent, result = line.split(" ")
    total_score += result_score[result]
    if result == "Z":
        total_score += shape_score[win[opponent]]
    elif result == "X":
        total_score += shape_score[lose[opponent]]
    else:
        total_score += shape_score[opponent]
print(total_score)