import numpy as np
import csv as csv
import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import precision_recall_fscore_support
from sklearn.metrics import classification_report
from sklearn import metrics
from sklearn import naive_bayes

android_train_file = "../data/android/training.csv"
android_test_file = "../data/android/test.csv"

openstack_train_file = "../data/openstack/training.csv"
openstack_test_file = "../data/openstack/test.csv"

def classify_and_report (label, classifier, train_file, test_file):
    # load the training data as a matrix
    train_dataset = pd.read_csv(train_file, header=0)

    # separate the data from the target attributes
    train_data = train_dataset.drop('isReq', axis=1)

    # remove unnecessary features
    train_data = train_data.drop('Id', axis=1)

    # the lables of training data. `label` is the title of the  last column in your CSV files
    train_target = train_dataset.isReq 

    # load the testing data
    test_dataset = pd.read_csv(test_file, header=0)

    # separate the data from the target attributes
    test_data = test_dataset.drop('isReq', axis=1)

    # remove unnecessary features
    test_data = test_data.drop('Id', axis=1)

    # the lables of test data
    test_target = test_dataset.isReq

    # gnb = GaussianNB()
    test_pred = classifier.fit(train_data, train_target).predict(test_data)
    
    precision = metrics.precision_score(test_target, test_pred, average="weighted", labels=[1])
    recall = metrics.recall_score(test_target, test_pred, average="weighted", labels=[1])
    f1 = metrics.f1_score(test_target, test_pred, average="weighted", labels=[1])
    
    return np.array([precision, recall, f1])


def make_naive_bayes_chart(label, train_file, test_file):
    
    df_index = ["BernoulliNB", "ComplementNB", "GaussianNB", "MultinomialNB"]
    chart = pd.DataFrame(columns=["precision", "recall", "f1_score"], index=df_index)
    
    chart.loc["BernoulliNB"] = classify_and_report(label, naive_bayes.BernoulliNB(), train_file, test_file)
    chart.loc["ComplementNB"] = classify_and_report(label, naive_bayes.ComplementNB(), train_file, test_file)
    chart.loc["GaussianNB"] = classify_and_report(label, naive_bayes.GaussianNB(), train_file, test_file)
    chart.loc["MultinomialNB"] = classify_and_report(label, naive_bayes.MultinomialNB(), train_file, test_file)
    
    chart = chart.rename_axis(label, axis="columns")
    
    return chart


android_chart_nb = make_naive_bayes_chart("Android", android_train_file, android_test_file)
openstack_chart_nb = make_naive_bayes_chart("Openstack", openstack_train_file, openstack_test_file)

print(android_chart_nb)
print(openstack_chart_nb)

