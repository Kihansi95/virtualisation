#!/usr/bin/python
import sys
import csv
import os

file1 = "forum_node"
first_line = True
file2 = "forum_users"

for line in sys.stdin:
    data = line.strip().split("\t")

    if len(data) == 9:
        # le premier fichier
        if first_line:
            first_line = False
            continue

        print("{0}\t{1}\t{2}").format(data[0], data[1:9], file1)

    elif len(data) == 4:

        #le deuxieme fichier
        if first_line:
            first_line = False
            continue

        print("{0}\t{1}\t{2}").format(data[0], data[1:9], file2)
