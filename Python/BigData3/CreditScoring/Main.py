'''
Created on 27 Mar 2017

@author: wrp3
'''

import traceback

from dask.dataframe.core import DataFrame

from CreditScoring.Analysis import compareColumns, prepData
from CreditScoring.Analysis import condHists
from CreditScoring.Analysis import kMeans, logRegress
from CreditScoring.Analysis import loadFile, cleanData
import numpy as np
import pandas as pd


if __name__ == '__main__':
    pass

filenom = "credit_scores"
df = loadFile(filenom)
df = cleanData(df)
df_num = df.select_dtypes(include=[np.number])

genG = False

if genG == True:
    for c1 in df_num:
        for c2 in df_num:
            if (c1 != c2):
                print("Generating Graph for " + c1 + " vs " + c2 + "..")
                compareColumns(df, c1, c2)
df = prepData(df, 'Age', 'Duration')
df_num = df.select_dtypes(include=[np.number]).copy()
cols = df.columns
cols = cols.drop('CreditStatus')

print(cols)
#condHists(df, df_num, 'CreditStatus')
try:
    logRegress(df, cols, 'CreditStatus')
    #logRegress(df_num, cols, 'CreditStatus')
except Exception:
    traceback.print_exc()    
kMeans(df, 'CreditStatus')
