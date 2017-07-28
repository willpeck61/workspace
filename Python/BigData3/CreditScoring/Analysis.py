'''
Created on 27 Mar 2017

@author: wrp3
'''

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import seaborn as sns

from sklearn.decomposition import PCA
from sklearn.feature_selection import RFE
from sklearn.cluster import KMeans
from sklearn import linear_model, model_selection
from sklearn.cross_validation import cross_val_score
from collections import Counter
from sklearn import metrics

# Load csv
def loadFile(filenom):
    try:
        return pd.read_csv("./" + filenom + ".csv", header = 0)
    except Exception as e:
        return e
    
# Ensures data clean
def cleanData(df):
    df.drop_duplicates(inplace = True)
    cols = df.select_dtypes(include = ['object'])
    for c in cols:
        df[c] = df[c].str.replace('A', '')
        setOfVal = df[c].unique()
        i = 0
        dict = {}
        alp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        for v in setOfVal:
            dict[v] = alp[i]
            i += 1
        df[c] = df[c].map(dict)
    #setBool0 = {0 : False, 1 : True}
    setBool1 = {'191' : False, '192' : True}
    df['Telephone'] = df['Telephone'].map(setBool1)
    #df['CreditStatus'] = df['CreditStatus'].map(setBool0)
    print("CleanData Finished..")
    print(df.describe())
    return df

# produces scatter plots of column vs column    
def compareColumns(df, col1, col2):
    plt.scatter(df[col1], df[col2])
    plt.xlabel(col1)
    plt.ylabel(col2)
    plt.savefig("Graphs/" + col1 + "Vs" + col2)
    plt.close()
    
# creates histograms from dataframe, column names and comparison feature
def condHists(df, col1, col2):
    for col in col1.columns:
        #g = sns.factorplot(col, hue='Key', col="CreditStatus", data=df, kind="bar")
        g = sns.factorplot(x='Key', y=col, data=df,
                            col="CreditStatus", kind="bar", ci=None, size=2.5)
    #plt.show()
    plt.close()
    
    for col in col1.columns:
        g = sns.FacetGrid(df, col=col2, margin_titles = True)
        g.map(sns.violinplot, col)
    #plt.show()
    plt.close()

# Convert categorical data into new boolean columns
def prepData(df, grp, xlab):
    plt.hist(df[grp])
    plt.title(grp)
    #plt.show()
    plt.clf()
    df.groupby(grp).mean()
    plt.plot(df[xlab], df[grp], 'ro')
    plt.xlabel(xlab)
    plt.ylabel(grp)
    plt.title(grp + " Vs. " + xlab)
    #plt.show()
    plt.close()
    return pd.get_dummies(df, columns=df.select_dtypes(include = [np.object]))

# Logistical Regression
def logRegress(df, cols, y):
    clf = linear_model.LogisticRegression()
    preds = df.columns.values.tolist()
    predictors = [i for i in preds if i not in ['CreditStatus']]
    X0 = df[predictors]
    Y0 = df['CreditStatus']
    cols = ['Duration', 'CreditAmount', 'Age','NumberDependents','ExistingCreditsAtBank', 'Job_A', 'Job_B', 'Job_C', 'Job_D',
     'CreditHistory_A','CreditHistory_B',
     'CreditHistory_C', 'CreditHistory_D',
     'CreditHistory_E', 'Housing_A', 'Housing_B',
     'Housing_C', 'Employment_A', 'Employment_B', 
     'Employment_C', 'Employment_D', 'Employment_E']
    selector = RFE(clf,12,step=1)
    selector = selector.fit(X0, Y0)
    rank = selector.ranking_
    temp = np.array(predictors)
    msk = rank==1
    s_features = temp[msk].tolist()
    print(s_features)
    X = df[s_features]
    Y = df['CreditStatus']
    clf.fit(X, Y)
    print("Coefficients:\n {}".format(clf.coef_))
    print("Intercept: {}".format(clf.intercept_))
    clf.score(X, Y)
    scores = cross_val_score(linear_model.LogisticRegression(), X, Y, 
                             scoring = 'accuracy', cv = 8)
    print("Scores:\n {}".format(["{:.3f}".format(s) for s in scores]))
    print("Mean score: {}".format(scores.mean()))
    X_train, X_test, Y_train, Y_test = model_selection.train_test_split(X, Y, test_size=0.2)
    clf = linear_model.LogisticRegression(C=0.7)
    clf.fit(X_train, Y_train)
    print("Score against training data: {}".format(clf.score(X_train, Y_train)))
    print("Score against test data: {}".format(clf.score(X_test, Y_test)))

    d = {'actual' : Counter(df['CreditStatus']),
         'predicted' : Counter(clf.predict(X))}
    
    index = np.arange(len(d['actual']))
    width = 0.35
    plt.close()
    probs = clf.predict_proba(X_test)
    prob = probs[:,1]
    prob = np.array(prob)
    Y_test = np.array(Y_test)+1
    fpr, sensitivity, _ = metrics.roc_curve(Y_test, prob, pos_label=2)
    fig = plt.figure()
    ax = fig.add_subplot(111)
    ax.scatter(fpr, fpr, c='b', marker='s')
    ax.scatter(fpr, sensitivity, c='r', marker='o')
    plt.title("ROC Curve")
    plt.show()
    auc = metrics.auc(fpr,sensitivity)
    print("auc= ", auc)
    
    x = np.arange(len(d))
    dlist = list(d.keys())
    for i in range(len(d)):
        key = dlist[i]
        keys = d[key].keys()
        values = d[key].values()
        plt.bar(left = x + i * width, height = values, width = width, label = key)
        plt.xticks(x + (0.5 * width), keys)
    plt.legend()
    plt.show()
    plt.close()
    
# K-Means algorithm implementation
def kMeans(df, cntd):
    model = KMeans(n_clusters = 4)
    model.fit(df)
    print("J-Score = ", model.inertia_)
    
    labels  = model.labels_
    md = pd.Series(labels)
    df['clust'] = md
    
    centroids = model.cluster_centers_
    #centroids
    
    plt.hist(df['clust'])
    plt.title('Histogram of Clusters')
    plt.xlabel('Cluster')
    plt.ylabel('Frequency')
    plt.show()
    plt.close()
    df.groupby('clust').mean()
    pca_data = PCA(n_components=4).fit(df)
    pca_2d = pca_data.transform(df)
    col_list = ['b']
    plt.scatter(pca_2d[:,0], pca_2d[:,1], c=col_list)
    plt.title('Clusters')
    plt.show()
    plt.close()
    
    df_cluster0 = df.where(df['clust'] == 0)
    df_cluster1 = df.where(df['clust'] == 1)
    df_cluster2 = df.where(df['clust'] == 2)
    df_cluster3 = df.where(df['clust'] == 3)
    
    df_cluster0.drop('clust', axis = 1, inplace = True)
    df_cluster1.drop('clust', axis = 1, inplace = True)
    df_cluster2.drop('clust', axis = 1, inplace = True)
    df_cluster3.drop('clust', axis = 1, inplace = True)

    keys = ['CreditAmount', 'Age']    
    df1 = pd.DataFrame(df_cluster0)
    df2 = pd.DataFrame(df_cluster1)
    df3 = pd.DataFrame(df_cluster2)
    df4 = pd.DataFrame(df_cluster3)
    
    df1['Key'] = 'Cluster 0'
    df2['Key'] = 'Cluster 1'
    df3['Key'] = 'Cluster 2'
    df4['Key'] = 'Cluster 3'
    
    pltdf = pd.concat([df1,df2,df3,df4], keys = ['Cluster 0', 'Cluster 1',
                                                 'Cluster 2', 'Cluster 3'])
    
    condHists(pltdf, 
              pltdf.select_dtypes(include = [np.number]), 'Key')
    #condHists(df_cluster1, 
    #          df_cluster1.select_dtypes(include = [np.number]), 'CreditStatus')
    
    #condHists(df_cluster2, 
    #          df_cluster2.select_dtypes(include = [np.number]), 'CreditStatus')
    #condHists(df_cluster3, 
    #          df_cluster3.select_dtypes(include = [np.number]), 'CreditStatus')
    #condHists(df_cluster4, 
    #          df_cluster4.select_dtypes(include = [np.number]), 'CreditStatus')
    #condHists(df_cluster5, 
    #          df_cluster5.select_dtypes(include = [np.number]), 'CreditStatus')
    
    print("** Cluster 0 **")
    print(df_cluster0.describe())
    print("")
    print("** Cluster 1 **")
    print(df_cluster1.describe())
    print("")
    print("** Cluster 2 **")
    print(df_cluster2.describe())
    print("")
    print("** Cluster 3 **")
    print(df_cluster3.describe())
    print("")
    mn = []
    mn.append(df_cluster0.mean())
    mn.append(df_cluster1.mean())
    mn.append(df_cluster2.mean())
    mn.append(df_cluster3.mean())
    
    keys = ['Duration', 'CreditAmount',
            'InstallmentRatePecnt', 'PresentResidenceTime',
            'Age', 'ExistingCreditsAtBank', 'NumberDependents']
    meanArr = {'Duration' : [], 'CreditAmount' : [],
            'InstallmentRatePecnt' : [], 'PresentResidenceTime' : [],
            'Age' : [], 'ExistingCreditsAtBank' : [],
            'NumberDependents' : []}
    i = 0
    while i < 4:
        for k in keys:
            meanArr[k].append(mn[i][k])
        i += 1
    xlab = ['Cluster 0', 'Cluster 1', 
         'Cluster 2', 'Cluster 3']
    col_list = ['b', 'g', 'r', 'c', 'm', 'y', 'w']
    x = range(4)
    plt.bar(x, meanArr['NumberDependents'][:], color = 'k')
    rect_heights = [0.] * len(meanArr['Duration'])
    i = 0
    for k in keys:
        rect_heights = [rect_heights[j] +
                        meanArr[keys[i - 1]][j] for j in range(len(rect_heights))]  
        if k == 'NumberDependents':
            plt.bar(x, meanArr[k], 
                    color = col_list[i % len(col_list)], 
                    bottom = rect_heights, alpha = 0)
        else:
            plt.bar(x, meanArr[k], 
                    color = col_list[i % len(col_list)], 
                    bottom = rect_heights)
        i += 1
    plt.xticks(x, xlab, rotation='vertical', fontsize = 6)
    blue_patch = mpatches.Patch(color='b', label='Duration')
    green_patch = mpatches.Patch(color='g', label='Credit Amt')
    red_patch = mpatches.Patch(color='r', label='Inst. Rate Pct')
    cay_patch = mpatches.Patch(color='c', label='Pres. Res. Time')
    mag_patch = mpatches.Patch(color='m', label='Age')
    yel_patch = mpatches.Patch(color='y', label='Exst. Cred. Banks')
    blk_patch = mpatches.Patch(color='k', label='No. Dependents')
    plt.title('Clusters with Properties Split')
    plt.ylabel('Sum of Mean Property Values')
    plt.legend(handles=[red_patch, blue_patch, green_patch,
                        cay_patch, mag_patch, yel_patch, blk_patch])
    #plt.show()       
    
    xlab = ['Cluster 0', 'Cluster 1', 
            'Cluster 2', 'Cluster 3']
    col_list = ['b', 'g', 'r', 'c', 'm', 'y', 'w', 
                'k', '#F39797', '#97B3F3', '#97F39A',
                '#5A53C4', '#8E738C', '#494949']
    x = range(4)
    keys = ['CheckingAcctStat_A', 'CheckingAcctStat_B',
            'OtherInstalments_A', 'OtherInstalments_B',
            'OtherInstalments_C', 'Housing_A', 'Housing_B',
            'Housing_C', 'Job_A', 'Job_B', 'Job_C', 'Job_D',
            'ForeignWorker_A', 'ForeignWorker_B']
    meanArr = {'CheckingAcctStat_A' : [], 'CheckingAcctStat_B' : [],
            'OtherInstalments_A' : [], 'OtherInstalments_B' : [],
            'OtherInstalments_C' : [], 'Housing_A' : [], 'Housing_B' : [],
            'Housing_C' : [], 'Job_A' : [], 'Job_B' : [], 'Job_C' : [], 'Job_D' : [],
            'ForeignWorker_A' : [], 'ForeignWorker_B' : []}
    i = 0;
    while i < 4:
        for k in keys:
            meanArr[k].append(mn[i][k])
        i += 1
    rect_heights = [0.] * len(meanArr['CheckingAcctStat_A'])
    plt.bar(x, meanArr['ForeignWorker_B'][:], color = '#494949')
    i = 0
    for k in keys:
        rect_heights = [rect_heights[j] +
                        meanArr[keys[i - 1]][j] for j in range(len(rect_heights))]  
        if k == 'ForeignWorker_B':
            plt.bar(x, meanArr[k], 
                    color = col_list[i % len(col_list)], 
                    bottom = rect_heights, alpha = 0)
        else:
            plt.bar(x, meanArr[k], 
                    color = col_list[i % len(col_list)], 
                    bottom = rect_heights)
        i += 1
    plt.xticks(x, xlab, rotation='vertical', fontsize = 6)
    blue_patch = mpatches.Patch(color='b', label='CheckingAcctStat_A')
    green_patch = mpatches.Patch(color='g', label='CheckingAcctStat_B')
    red_patch = mpatches.Patch(color='r', label='OtherInstalments_A')
    cay_patch = mpatches.Patch(color='c', label='OtherInstalments_B')
    mag_patch = mpatches.Patch(color='m', label='OtherInstalments_C')
    yel_patch = mpatches.Patch(color='y', label='Housing_A')
    blk_patch = mpatches.Patch(color='w', label='Housing_B')
    a_patch = mpatches.Patch(color='k', label='Housing_C')
    b_patch = mpatches.Patch(color='#F39797', label='Job_A')
    c_patch = mpatches.Patch(color='#97B3F3', label='Job_B')
    d_patch = mpatches.Patch(color='#97F39A', label='Job_C')
    e_patch = mpatches.Patch(color='#5A53C4', label='Job_D')
    f_patch = mpatches.Patch(color='#8E738C', label='ForeignWorker_A')
    g_patch = mpatches.Patch(color='#494949', label='ForeignWorker_B')
    plt.title('Clusters with Properties Split')
    plt.ylabel('Sum of Mean Property Values')
    plt.legend(handles=[red_patch, blue_patch, green_patch,
                        cay_patch, mag_patch, yel_patch, blk_patch,
                        a_patch, b_patch, c_patch, d_patch,
                        e_patch, f_patch, g_patch])
    plt.show()
    plt.close()
    xlab = ['Cluster 0', 'Cluster 1', 
            'Cluster 2', 'Cluster 3']