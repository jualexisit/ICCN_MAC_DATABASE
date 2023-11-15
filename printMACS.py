
mac_start = 273743281651712


f = open("printedMACs.txt", "w")
for i in (range(2048)):
    print(hex(mac_start))
    f.write(str(hex(mac_start)) + "\n"  )
    mac_start += 1





f.close()