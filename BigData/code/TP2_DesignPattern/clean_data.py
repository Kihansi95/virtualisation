#! /usr/bin/python

with open("../../data/forum_node.tsv", 'r') as _in:
    with open("../../data/clean_forum.tsv", 'w') as _out:
        for l in _in.readlines():
            _out.write(l.strip() + "\n")