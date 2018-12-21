#!/usr/bin/python
import sys
import csv
import re

firstLine = 1

reader = csv.reader(sys.stdin, delimiter = "\t")
writer = csv.writer(sys.stdout, delimiter="\t", quoting=csv.QUOTE_ALL)

for line in reader:
    if firstLine == 1:
        firstLine = 0
        continue
    body = line[4]
    node = line[0]
    words = re.findall(r"[\w']+|[.,!?;]", body) 
    for word in words:
        if word not in [".", "!", ",","?","#","$","[","]","/",">","<","=","-",";",":","(",")"]:
            print("{0}\t{1}".format(node,word))