#!/usr/bin/python
import sys
total_post = 0

for line in sys.stdin:
	data = line.strip().split("\"")
	total_post += int (data[1])

print ("Total post ", total_post)