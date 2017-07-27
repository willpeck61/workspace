'''
Created on 6 Mar 2017

@author: will
'''

from HousingAnalysis.ProcessData import loadFile, processData, sanitizeData

if __name__ == '__main__':
    pass

print("Started...")
try:
    filenom = "clean_data"
    rawdata = loadFile(filenom)
    if len(rawdata) == 0:
        raise IOError
    print("Clean data found! Load 'clean_data.csv' successful..")
    processData(rawdata)
except IOError as e:
    print("Clean data process not completed. File 'clean_data.csv does not exist'.")
    print("Cleaning process started..")
    filenom = "Manhattan12"
    rawdata = loadFile(filenom)
    sanitizeData(rawdata)



    
