'''
Big Data Coursework 1

@author: wrp3

TextProcessor
'''
import string
import itertools
import re

# Function accepts a pair of words or phrases then returns the number of
# times the pair or phrase appears within the accepted file. 
# filenom - Filename without extension. Files located in package root.
# word1, word1 - Pair of words
def pairedCount(filenom, word1, word2):
    # treat text file to unicode
    fl  = open('./' + filenom + '.txt', 'r', encoding="utf8")
    word1 = word1.lower() 
    word2 = word2.lower()
    sp = word1.count(" ") + word2.count(" ") + 1 # number of words in phrase
    word1 = word1.replace(" ", "") # remove spaces
    word2 = word2.replace(" ", "") # remove spaces
    cnter = dict() # create an empty dictionary
    index = [0, 0] # counter for each word
    arr = (word1, word2) # pair of words 
    for line in fl:
        # remove punctuation from text
        words = line.translate(str.maketrans('', '', string.punctuation)) 
        words = words.split() # split words up
        k = 0
        for word in words:
            word = re.sub('[^0-9a-zA-Z]+', '', word)
            phrase = [] # list each word of the phrase
            concat = "" # resulting string when phrase in concatenated
            if sp > 1:
                for n in range(0, sp):
                    if (k + n) < len(words):
                        phrase.append(words[k + n])
                        concat = ''.join(phrase) # concatenate phrase
                        concat = concat.lower()
                        concat = concat.replace(" ", "") # make sure no white space
                    if concat == word1 or concat == word2:
                        word = concat
                        break
            word = word.lower()
            if word == word1:
                index[0] += 1 
            elif word == word2:
                index[1] += 1
            if word == word1 or word == word2:
                if word == word1:
                    i = 0
                else:
                    i = 1
                try:
                    cnter[arr][word] = index[i] # if key exists replace
                except KeyError:
                    cnter[arr] = {} # create new key 
                    cnter[arr][word] = index[i] # add new quantity
            k += 1
    fl.close()
    nullval = [False, False] # validate keys
    try:
        cnter[arr][word1] # has word1 been found?
    except KeyError:
        nullval[0] = True # set flag
    try:
        cnter[arr][word2]
    except KeyError:
        nullval[1] = True
        
    if not nullval[0] and not nullval[1]:
        # get min of both words
        lowest = min(cnter[arr][word1], cnter[arr][word2])
    else:
        lowest = 0
    cnter[arr] = lowest
    return lowest
     
# Function accepts list of terms then puts them into
# pairs.  Returns dictionary of all paired terms.
# filenom - Filename
# terms - List of words 
def crossCorrelation1(filenom, terms):
    arrlist = list(itertools.combinations(terms, 2))
    dc = dict()
    for t in arrlist:
        dc[t] = pairedCount(filenom, t[0], t[1])
    return dc
    
# Function accepts list of filenames and
# list of terms.  Returns merged dictionary.
# filenoms - List of filenames
# terms - List of words
def crossCorrelation2(filenoms, terms):
    dlist = [None] * len(filenoms)
    i = 0
    for f in filenoms:
        dlist[i] = crossCorrelation1(f, terms)
        i += 1
    superd = dictionaryMerger(dlist)
    return superd

# Function merges multiple dictionaries.
def dictionaryMerger(*dictionaries):
    superd = {}
    for d in dictionaries:
        for i in d:
            for j in i:
                try:
                    if i[j] < superd[j]:
                        superd[j] += i[j] 
                except KeyError:
                    superd[j] = i[j]
    return superd  