# Justin Alexis
# The purpose of this file is for testing
# The code below takes an integer, then increments that integer and 
# converts it into a hexadecimal value. It is then written into a new file.
# Change mac_start to alter the starting addr and change the filename.

mac_start = 273743281651712


f = open("printedMACs.txt", "w")

for i in (range(2048)):
    print(hex(mac_start))
    f.write(str(hex(mac_start)) + "\n"  )
    mac_start += 1


f.close()