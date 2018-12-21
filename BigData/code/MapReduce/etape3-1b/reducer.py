#!/usr/bin/python
import sys
nbTotal = 0
oldWord = None
listNodes = []

for line in sys.stdin:
    dataMapped = line.strip().split("\t")

    thisWord = str(dataMapped[0])
    thisNode = str(dataMapped[1])

    if oldWord and oldWord.lower() != thisWord.lower():
        listNodes.sort(key = lambda listNodes: len(listNodes) - 1)
        print(oldWord, "\t", nbTotal, "\t", listNodes)
        oldWord = thisWord.lower();
        nbTotal = 0
        listNodes = []

    oldWord = thisWord.lower()
    nbTotal = nbTotal + 1
    if thisNode not in listNodes:
        listNodes.append(thisNode)
if oldWord != None:
    listNodes.sort(key = lambda listNodes: len(listNodes) - 1)