#!/usr/bin/python
import sys
salesTotal = 0
cpt = 0
oldKey = None

for line in sys.stdin:
    data = line.strip().split("\t")
    if len(data) != 2:
        continue

    thisKey, thisSale = data
    if oldKey and oldKey != thisKey:
        print "{0}\t{1}".format(oldKey, salesTotal/cpt)
        salesTotal = 0
        cpt = 0

    oldKey = thisKey
    salesTotal += float(thisSale)
    cpt += 1

if oldKey != None:
    print oldKey, '\t', salesTotal/cpt
