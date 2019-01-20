#!/usr/bin/python
import sys
import csv

file1 = "forum_node"
file2 = "forum_users"
tab1 = {}
tab2 = {}

for line in sys.stdin:
    id, list, file = line.split("\t")
    print(file, file1)

    if file1 in file:
        tab1[id] = list
    else:
        tab2[id] = list

for key1, value1 in tab1.item():
    if key1 in tab2:
        print "{0}\t{1}\t{2}".format(key1, value1, tab2[key1])
