s = 0
for line in open("2022\\25.txt").read().splitlines():
    for n, d in enumerate(line[::-1]):
        s += {"=": -2, "-": -1, "0": 0, "1": 1, "2": 2}[d] * 5 ** n
s5 = ""
carry = 0
while s:
    d = s % 5 + carry
    if d > 2:
        carry = 1
        s5 += "=-0"[d-3]
    else:
        carry = 0
        s5 += str(d)
    s //= 5
if carry:
    s5 += "1"
s5 = s5[::-1]
print(s5)