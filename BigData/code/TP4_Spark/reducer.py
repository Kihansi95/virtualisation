#!/usr/bin/python
import sys
total = 0
oldKey = None

for line in sys.stdin:
    data = line.strip().split("\t")
    if len(data) != 2:
        continue

    word, count = data
    if oldKey and oldKey != word:
        print "{0}\t{1}".format(oldKey, count)
        total = 0

    oldKey = word
    total += int(count)

if oldKey != None:
    print oldKey, '\t', total
