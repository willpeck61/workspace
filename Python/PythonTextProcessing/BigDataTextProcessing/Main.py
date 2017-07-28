'''
Big Data Coursework 1

@author: wrp3

Main
'''
from BigDataTextProcessing.TextProcessor import pairedCount, crossCorrelation1
from BigDataTextProcessing.TextProcessor import crossCorrelation2

if __name__ == '__main__':
    pass

assert(pairedCount('paper1','this', 'the') == 52), "Test 1 : Failed"
print("Test 1 : Passed")
assert(pairedCount('paper1','algorithm','matrix') == 14), "Test 2 : Failed"
print("Test 2 : Passed")
assert(pairedCount('paper1','notinthetext','matrix') == 0), "Test 3 : Failed"
print("Test 3 : Passed")
assert(pairedCount('paper1','notinthetext','notinthetext') == 0), "Test 4 : Failed"
print("Test 4 : Passed")
assert(pairedCount('paper1','matrix','matrix') == 14), "Test 5 : Failed"
print("Test 5 : Passed")
assert(pairedCount('paper1','The Art of Computer Programming','computer code') == 1), "Test 6 : Failed"
print("Test 6 : Passed")
assert(crossCorrelation2(['paper1','paper2'], ['The Art of Computer Programming','computer code'])[('The Art of Computer Programming','computer code')] == 1), "Test 7 : Failed"
print("Test 7 : Passed")
assert(crossCorrelation2(['paper1','paper2'], ['algorithm','matrix'])[('algorithm','matrix')] == 17), "Test 8 : Failed"
print("Test 8 : Passed")
assert(crossCorrelation2(['paper1','paper2'], ['notinthetext','matrix'])[('notinthetext','matrix')] == 0), "Test 9 : Failed"
print("Test 9 : Passed")
assert(crossCorrelation2(['paper1','paper2'], ['notinthetext','notinthetext'])[('notinthetext','notinthetext')] == 0), "Test 10 : Failed"
print("Test 10 : Passed")
assert(crossCorrelation2(['paper1','paper2'], ['matrix','matrix'])[('matrix','matrix')] == 17), "Test 11 : Failed"
print("Test 11 : Passed")