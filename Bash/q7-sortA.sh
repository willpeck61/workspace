#!/bin/bash
# Author W Peck

sort -t ";" -k 1,1 -d $1 > alpha-$1
sort -t ";" -k 2,2 -d $1 > R1-$1
sort -t ";" -k 3,3 -d $1 > R2-$1
sort -t ";" -k 4,4 -d $1 > total-$1
