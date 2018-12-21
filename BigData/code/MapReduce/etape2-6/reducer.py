#!/usr/bin/python
import sys

top = []
nb_top = 10

for line in sys.stdin:
	pack = tuple( map(int, line.strip().split("\t") ) ) 
	if len(top) < nb_top :
		top.append(pack)
		top = sorted(top, key = lambda el: el[1], reverse=True)
	else:
		if top[-1][1] < pack[1] :
			top[-1] = pack
			top = sorted(top, key = lambda el : el[1], reverse=True)

for el in top:
	print (el[0], "\t", el[1])