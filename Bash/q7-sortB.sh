#!/bin/bash
# Author W Peck

sort -t ";" -k 1,1 -d $1 > alpha-$1
sort -t ";" -k 2,2 -h $1 > size-$1
sort -t ";" -k 3,3 -M $1 > date-$1
sort -t ";" -k 4,4 -n $1 > value-$1
