#!/usr/bin/python
import sys
import csv

reader = csv.reader(sys.stdin, delimiter="\t")
writer = csv.writer(sys.stdout, delimiter="\t", quotechar='"', quoting=csv.QUOTE_ALL)

for line in reader:
	i = line[4]
	if len(i) != 0:
		writer.writerow([1])