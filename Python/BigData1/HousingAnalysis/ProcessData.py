'''
Created on 6 Mar 2017

@author: will
'''
from numpy import nan
from pandas.core.series import Series
from sklearn import svm, feature_selection, linear_model
from sklearn.model_selection import train_test_split

import os
import math as ma
import matplotlib.dates as dt
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import scipy.stats as stats
import matplotlib.patches as mpatches
from IPython.core.pylabtools import figsize

def loadFile(filenom):
    try:
        return pd.read_csv("./" + filenom + ".csv", header=0, index_col=None)
    except Exception as e:
        return pd.DataFrame()
         
def sanitizeData(rawdata):
    rawdata.rename(columns={'ZIP CODE':'ZIP',
                            'RESIDENTIAL UNITS':'RES_UNITS', 
                            'TAX CLASS AT TIME OF SALE' : 'TAX_CLASS',
                            'BUILDING CLASS CATEGORY': 'BUILD_CLASS_CAT',                           
                            'APART\r\nMENT\r\nNUMBER' : 'APT_NO',
                            'TOTAL UNITS' : 'TOTAL_UNITS',
                            'BUILDING CLASS AT TIME OF SALE' : 'BUILD_CLASS_SALE',
                            'BUILDING CLASS AT PRESENT' : 'BUILD_CLASS_PRESENT',
                            'SALE\r\nPRICE' : 'SALE_PRICE',
                            'SALE DATE' : 'SALE_DATE',
                            'YEAR BUILT' : 'YEAR_BUILT',
                            'LAND SQUARE FEET' : 'LAND_SQ_FT',
                            'GROSS SQUARE FEET' : 'GROSS_SQ_FT',
                            'TOTAL LAND SQUARE FEET' : 'TOTAL_LAND_SQ_FT',
                            'COMMERCIAL UNITS' : 'COM_UNITS',
                            'EASE-MENT' : 'EASE_MENT'}, inplace = True)
    rawdata = rawdata.reset_index()
    rawdata['Counter'] = rawdata.index
    rawdata = rawdata.set_index(rawdata['Counter'])
    rawdata = cleanData(rawdata)
    cur_n = ""
    clndata = pd.DataFrame(data=None, columns=rawdata.columns)
    uni_nb = rawdata['NEIGHBORHOOD'].unique()
    uni_bc = rawdata['BUILD_CLASS_PRESENT'].unique()
    cnt_nb = len(uni_nb)
    c = 0
    frames = []
    for n in rawdata['NEIGHBORHOOD']:
        tempnb_sp = rawdata.loc[rawdata['NEIGHBORHOOD'] == n]
        if (n != cur_n):
            print('Processing', n, cnt_nb - c, 'remaining..', end="\r", flush=True)
            c += 1
            cur_n = n
            # Remove any NaN values
            tempnb_sp = tempnb_sp.loc[pd.notnull(tempnb_sp['LG_SALE_PRICE'])]
            
            # Create new column for date only (excludes time from timestamp)
            short_date = pd.to_datetime(tempnb_sp['SALE_DATE']).apply(
                lambda x:x.date().strftime('%d-%m-%y'))
            tempnb_sp['SHORT_DATE'] = short_date
            desc = tempnb_sp['LG_SALE_PRICE'].describe()
            mid_density = desc[5]

            tempnb_sp = removeOutliers(n, tempnb_sp, 'SALE_PRICE', 'SALE_DATE', 
                (desc[5] * 1.5) - desc[4], mid_density, desc[6] + (desc[5] * 1.5), True, False)
            frames.append(tempnb_sp)

    clndata = pd.concat(frames)
    clndata = clndata[clndata['NOT_RANGE'] == False]
    c = 0
    cnt_bc = len(uni_bc)
    tempnb_sp = pd.DataFrame()
    print(len(clndata))
    for b in uni_bc:
        print('Processing', b, cnt_bc - c, 'remaining..', end="\r", flush=True)
        c += 1
        tempnb_sp = clndata.loc[clndata['BUILD_CLASS_PRESENT'] == b]
        desc = tempnb_sp['LG_SALE_PRICE'].describe()
        if len(tempnb_sp) > 0:
            tempnb_sp = removeOutliers(b, tempnb_sp, 'SALE_PRICE', 'BUILD_CLASS_PRESENT', 
                (desc[5] * 1.5) - desc[4], mid_density, desc[6] + (desc[5] * 1.5), True, False)
            frames = [clndata, tempnb_sp]
            clndata = pd.concat(frames)
            pd.DataFrame.drop_duplicates(clndata, inplace = True)

    nums = []
    i = 0
    for y in clndata['YEAR_BUILT']:
        tempd = rawdata[rawdata['YEAR_BUILT'] == y]
        count = len(tempd)
        nums.append(count)
        i += 1
    clndata['NUM_YEAR_BUILT'] = nums
    
    clndata = moreCleaning(clndata)
    col = ['GROSS_SQ_FT', 'LAND_SQ_FT', 'TOTAL_UNITS']
    

    temp1 = clndata.loc[clndata[col[0]] > 0]
    sfdesc = temp1[col[0]].describe()
    
    spdesc = temp1['SALE_PRICE'].describe()
    asp = spdesc[1]
    
    asqf = sfdesc[1]
    rat = asqf / asp
    
    temp2 = clndata.loc[clndata[col[0]] == 0]
    temp2[col[0]] = temp2['SALE_PRICE'].apply(lambda x : x * rat)
    
    temp3 = clndata.loc[clndata[col[1]] > 0]
    sfdesc = temp3[col[1]].describe()
    
    spdesc = temp3['SALE_PRICE'].describe()
    asp = spdesc[1]
    
    asqf = sfdesc[1]
    rat = asqf / asp
    
    temp4 = clndata.loc[clndata[col[1]] == 0]
    temp4[col[1]] = temp4['SALE_PRICE'].apply(lambda x : x * rat) 
    
    temp5 = clndata.loc[clndata[col[2]] > 0]
    sfdesc = temp5[col[2]].describe()
    
    spdesc = temp5['SALE_PRICE'].describe()
    asp = spdesc[1]
    
    asqf = sfdesc[1]
    rat = asqf / asp
    
    temp6 = clndata.loc[clndata[col[2]] == 0]
    temp6[col[2]] = temp6['SALE_PRICE'].apply(lambda x : x * rat)
    
    # Concatenate the filtered dataframes
    clndata = pd.concat([temp2, temp4, temp6])
    
    # Group duplicated rows and keep the max row.
    clndata = clndata.groupby(clndata.index).max()
    clndata['TOTAL_UNITS'] = clndata['TOTAL_UNITS'].apply(lambda x : np.round(x))
    
    try:
        clndata.to_csv('clean_data.csv')
        print("Data cleaning successful. New file 'clean_data.csv' created.")
    except IOError as e:
        print("Failure : Data cleaning failed. Unable to create file.")
        print(e)
                
def cleanData(rawdata):
    pd.DataFrame.drop_duplicates(rawdata, keep=False, inplace = True)
    rawdata['SALE_DATE'] = pd.to_datetime(rawdata['SALE_DATE'], 
                                          format = '%d/%m/%Y')
    rawdata['SALE_PRICE'] = rawdata['SALE_PRICE'].str.replace(',','')
    rawdata['SALE_PRICE'] = rawdata['SALE_PRICE'].str.replace('$','')
    rawdata['SALE_PRICE'] = pd.to_numeric(rawdata['SALE_PRICE'])
    rawdata.is_copy = False
    rawdata.replace(0, np.nan, inplace = True)
    rawdata.replace(r'\s+', '_', regex = True, inplace = True)
    rawdata.drop('APT_NO', axis = 1,  inplace = True)
    rawdata.drop('EASE_MENT', axis = 1, inplace = True)
    rawdata.drop('BOROUGH', axis = 1, inplace = True)
    rawdata = rawdata[pd.notnull(rawdata['SALE_PRICE'])]
    rawdata = rawdata[pd.notnull(rawdata['YEAR_BUILT'])]
    rawdata['SALE_PRICE'] = rawdata['SALE_PRICE'].astype(int)
    rawdata['LG_SALE_PRICE'] = np.log(rawdata['SALE_PRICE'])
    rawdata['OUTLIER_SALE_PRICE'] = [x < 10000 for x in rawdata['SALE_PRICE']]
    rawdata = rawdata[rawdata['OUTLIER_SALE_PRICE'] == False]
    rawdata.drop('OUTLIER_SALE_PRICE', axis = 1, inplace = True)
    rawdata = rawdata.loc[rawdata['NEIGHBORHOOD'] != '_']
    return rawdata

def removeOutliers(nhood, rawdata, coly, colx , min_val, mid_density, max_val, first, last_iter):
    if (min_val == '_'):
        rawdata = rawdata[rawdata[coly] != '_']
    
    # Create range for x axis at length of sale
    xlab = np.arange(len(rawdata[colx]))
    
    # percentiles=[min_val,0.8,max_val]
    # Set interquartile range
    idx = rawdata[coly].describe()
    
    # Order by date
    rawdata.is_copy = False;
    rawdata.sort_values(by='SALE_DATE', inplace = True)
    
    # Add new column with boolean values for sale price IQR
    # rawdata['NOT_IQR'] = rawdata['SALE_PRICE'].apply(
    #    lambda x: False if (idx[4] / 1.5) <= x <= (idx[6] * 1.5) else True)
    rawdata['LOWER'] = np.where(rawdata[coly] < ((idx[5] * 1.5) - idx[4]), True, False)
    rawdata['UPPER'] = np.where(rawdata[coly] > (idx[6] + (idx[5] * 1.5)), True, False)
    rawdata['NOT_RANGE'] = np.where(rawdata.LOWER != rawdata.UPPER, True, False)
    
    a = 0; b = 0;
    if (max(rawdata[coly]) < (idx[6] + (idx[5] * 1.5))):
        a = 1
    elif (idx[7] == max_val):
        a = 1
    if (min(rawdata[coly]) > ((idx[5] * 1.5) - idx[4])):
        b = 1
    elif (idx[3] == min_val):
        b = 1
    if a + b == 2:
        last_iter = True
    plt.title(nhood + ' ' + coly  + ' vs. ' + colx)
    
    # Plot scatter where values are not in the IQR * 1.5 range are highlighted
    if last_iter == False:
        plt.scatter(xlab, rawdata[coly], c=rawdata.NOT_RANGE,
                    label=coly)
    else:
        plt.scatter(xlab, rawdata[coly], label=coly)        
    
    if (colx == 'SALE_DATE'):
        plt.xticks(xlab, rawdata['SHORT_DATE'],
                   rotation = 'vertical', fontsize=6)
    else:
        plt.xticks(xlab, rawdata[colx],
                   rotation = 'vertical', fontsize=6)
    
    # Set x axis labels to fixed positions
    plt.locator_params(nbins=20)
    plt.xlabel(colx)
    plt.ylabel(coly)
    
    idx = rawdata[coly].describe()
        
    if last_iter == True:
        # Save to png
        plt.savefig("Graphs/" + nhood + "FINAL")
        plt.close()
        return rawdata
    else:
        # Save to png
        if first == True:
            rawdata = rawdata[rawdata.NOT_RANGE == False]
            try:
                plt.savefig("Graphs/" + nhood + "ORIGINAL")
            except FileNotFoundError as e:
                directory = os.path.dirname("Graphs/")
                os.mkdir(directory)                
                plt.savefig("Graphs/" + nhood + "ORIGINAL")
        plt.close()    
        
        # Remove outliers from data
        rawdata = rawdata[rawdata.NOT_RANGE == False]
        return removeOutliers(nhood, rawdata, coly, colx, idx[3], mid_density, idx[7], False, False) 
        #return rawdata
    
    
#Normalisation
def normalize(df):
    cols = df.select_dtypes(include=[np.number]).copy()
    #cols.drop("LG_SALE_PRICE", axis=1, inplace = True)
    print(cols.describe())
    return ((cols - cols.min())/(cols.max() - cols.min()))


def moreCleaning(df):
    df['GROSS_SQ_FT'].replace(r'[^0-9^.]','', regex = True, inplace = True)
    df['GROSS_SQ_FT'] = pd.to_numeric(df['GROSS_SQ_FT'])
    df['LAND_SQ_FT'].replace(r'[^0-9^.]','', regex = True, inplace = True)
    df['LAND_SQ_FT'] = pd.to_numeric(df['LAND_SQ_FT'])
    df['TOTAL_UNITS'].replace(r'[^0-9^.]','', regex = True, inplace = True)
    df['TOTAL_UNITS'] = pd.to_numeric(df['TOTAL_UNITS'])
    df['TO_DATE'] = pd.to_datetime(df['SHORT_DATE'])
    df['NUMERIC_DATE'] = (df['TO_DATE'] - df['TO_DATE'].min()) / np.timedelta64(1,'D')
    df['BUILT_YEAR'] = pd.to_datetime(df['YEAR_BUILT'])
    df['NUMERIC_YEAR'] = (df['BUILT_YEAR'] - df['BUILT_YEAR'].min()) / np.timedelta64(1, 'Y')
    df = df[pd.isnull(df['COM_UNITS'])]
    df = df[pd.notnull(df['YEAR_BUILT'])]
    return df

# Cleaned data processes for printing graphs and regression
def processData(df):
    # Plot ASP by Neighbourhood
    uni_nb = df['NEIGHBORHOOD'].unique()
    salepriceav = []
    xaxis_neighborhood = uni_nb
    for n in uni_nb:
        tempd = df[df['NEIGHBORHOOD'] == n]
        print(tempd['SALE_PRICE'].describe())
        tempdesc = tempd['SALE_PRICE'].describe()
        salepriceav.append([n, tempdesc[1]])
    xaxislab = np.arange(len(xaxis_neighborhood))
    salepriceav = sorted(salepriceav, key=lambda x: x[1], reverse=True)
    xaxis_neighborhood = []
    yaxis_salepriceav = []
    for k in salepriceav:
        xaxis_neighborhood.append(k[0])
        yaxis_salepriceav.append(k[1])
    plt.figure(figsize=(15,6))
    plt.axes()
    plt.bar(xaxislab, yaxis_salepriceav)
    plt.title("Average Selling Price by Neighbourhood")
    plt.ylabel("AVERAGE SELLING PRICE")
    plt.xticks(xaxislab, (xaxis_neighborhood), rotation="vertical", fontsize=5)
    plt.yticks(fontsize=6)
    plt.show()
    plt.close()
    
    cols = ['SALE_PRICE','NUM_YEAR_BUILT','NUMERIC_DATE','TOTAL_UNITS','GROSS_SQ_FT']
    df = moreCleaning(df)
    uni_nb = df['NEIGHBORHOOD'].unique()
    
    linearRegress(df)
        
    df = normalize(df)
    matrix = pd.scatter_matrix(df[cols], alpha=0.5, diagonal="kde")
    [plt.setp(item.yaxis.get_label(), 'size', 6) for item in matrix.ravel()]
    [plt.setp(item.xaxis.get_label(), 'size', 6) for item in matrix.ravel()]
    plt.show()
    plt.close()
    num_df = normalize(df)
    x = 'NUMERIC_DATE'
    y = 'SALE_PRICE'
    num_df.plot.scatter(x, y, title = "", marker=".")
    z = np.polyfit(x = df.loc[:,'NUMERIC_DATE'], y = df.loc[:,'SALE_PRICE'], deg = 1)
    p = np.poly1d(z)
    df['FIRST_DEGREE'] = p(df.loc[:,'NUMERIC_DATE'])
    df.set_index('NUMERIC_DATE', inplace = True)
    df['FIRST_DEGREE'].sort_index(ascending = False).plot()
    plt.gca().invert_xaxis()
    plt.show()

# Linear regression function
def linearRegress(df):
    ndf = df.select_dtypes(include=[np.number]).copy()
    ndf['TOTAL_UNITS'] = df['TOTAL_UNITS'].fillna(df['TOTAL_UNITS'].mean())
    ndf = ndf[pd.notnull(ndf)]
    ndf = normalize(ndf)
    feature_cols = ndf.columns.values.tolist()
    feature_cols.remove('index')
    feature_cols.remove('Counter')
    feature_cols.remove('SALE_PRICE')
    feature_cols.remove('RES_UNITS')
    feature_cols.remove('LG_SALE_PRICE')
    feature_cols.remove('COM_UNITS')
    feature_cols.remove('YEAR_BUILT')
    feature_cols.remove('LAND_SQ_FT')
    
    XO = ndf[feature_cols]
    YO = ndf['LG_SALE_PRICE']
    estimator = svm.SVR(kernel = 'linear')
    
    selector = feature_selection.RFE(estimator, 4, step = 1)
    selector = selector.fit(XO, YO)
    selector.support_
    rank = selector.ranking_
    
    select_features = np.array(feature_cols)
    
    msk = rank == 1
    select_features = select_features[msk].tolist()
    X = ndf[select_features]
    Y = ndf['LG_SALE_PRICE']
    trainX, testX, trainY, testY = train_test_split(X, Y, test_size=0.2)
    lm = linear_model.LinearRegression()
    lm.fit(trainX, trainY)
    
    print('Y intercept {}'.format(lm.intercept_))
    print('Weight coefficients:')
    zip(select_features, lm.coef_)
    for feat, coef in zip(select_features, lm.coef_):
        print("   {:>20}: {}".format(feat, coef))
    
    print("R squared for the training data {}".format(lm.score(trainX, trainY)))
    print("Score against test data: {}".format(lm.score(testX, testY)))
    
    fig = plt.figure(figsize=(14, 8))
    fig.clf()
    
    min_pt = X.min() * lm.coef_[0] + lm.intercept_
    max_pt = X.max() * lm.coef_[0] + lm.intercept_
    plt.plot([X.min(), X.max()], [min_pt, max_pt])
    plt.plot(trainX, trainY, 'o')
    plt.show()
    
    #Predicting the Ys from the train data
    pred_trainY = lm.predict(trainX)
    
    #Plotting
    fig.clf()
    plt.plot(trainX, trainY, 'o', label="Observed")
    plt.plot(trainX, pred_trainY, 'o', label="Predicted")
    plt.plot([X.min(), X.max()], [min_pt, max_pt], label='fit')
    plt.legend(loc='best')
    plt.show()
    
    #Predicting the Ys from the test data
    pred_testY = lm.predict(testX)
    
    # the value of R^2.
    print('R squared for the test data is: ', lm.score(testX, testY))
    
    rbf = svm.SVR(kernel="rbf", C=1e4, tol=1e-4)
    rbf.fit(trainX, trainY)
    print(rbf.score(testX, testY))
    predicted = rbf.predict(ndf[select_features])
    plt.figure(figsize=(12, 8))
    colors = ["r", "b"]   
    plt.scatter(ndf['LG_SALE_PRICE'], predicted, c=colors)
    
    # As described in http://matplotlib.org/users/legend_guide.html
    red_patch = mpatches.Patch(color='red', label='LG(Actual Sale Price)')
    blue_patch = mpatches.Patch(color='blue', label='Predicted Sale Price')
    plt.legend(handles=[red_patch, blue_patch])
    plt.xlabel = "Actual Prices"
    plt.ylabel = "Predicted Prices"
    plt.plot(range(2),range(2),
             color="darkorange")
    plt.title("Plot of predicted vs actual prices")
    plt.show()
    