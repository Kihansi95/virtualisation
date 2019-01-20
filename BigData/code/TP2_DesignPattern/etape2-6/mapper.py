#!/usr/bin/python
import sys
import csv

reader = csv.reader(sys.stdin, delimiter="\t")
writer = csv.writer(sys.stdout, delimiter="\t", quotechar='"', quoting=csv.QUOTE_NONE)

next(reader, None)

for line in reader:
	id = line[0]
	body = line[4]
	writer.writerow((id, len(body) ))