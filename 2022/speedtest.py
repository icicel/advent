import time, os, subprocess
year = input("Year: ")
day = input("Day: ")

years = [f for f in os.listdir() if f[0] == "2"]
if year != "all": 
    years = [year]

for year in years:
    to_run = [f for f in os.listdir(year) if f[-3:] == ".py"]
    if day != "all":
        to_run = [f for f in to_run if f[:len(day) + 1] == str(day) + "_"]
    to_run.sort(key=(lambda x: int(x.split("_")[0])))

    for day in to_run:
        n = day.split("_")[0]
        start = time.time()
        subprocess.run(f"py {year}/{day}", capture_output=True)
        print(f"{year}/{n} - {round(time.time() - start, 3)} s")