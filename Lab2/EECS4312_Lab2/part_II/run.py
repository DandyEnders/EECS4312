import numpy as np
import csv as csv
import pandas as pd
import itertools
from sklearn.naive_bayes import GaussianNB
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.linear_model import LogisticRegression

from sklearn.metrics import precision_recall_fscore_support
from sklearn.metrics import classification_report
from sklearn import metrics
from sklearn import naive_bayes

import os

android_train_file = "../data/android/training.csv"
android_test_file = "../data/android/test.csv"

openstack_train_file = "../data/openstack/training.csv"
openstack_test_file = "../data/openstack/test.csv"

def classify_and_report (classifier, train_file, test_file):
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
    
    f1 = metrics.f1_score(test_target, test_pred, average="weighted", labels=[1])
    
    return f1

def get_generator_parameters(p_dict):
    keys = p_dict.keys()
    vals = p_dict.values()
    for instance in itertools.product(*vals):
        yield dict(zip(keys, instance))
        
def get_classifier_parameter_tuning_chart(label, train_file, test_file, model_params, classifier):
    df_columns = [*model_params.keys()]
    df_columns.append("f1_score")
    chart = pd.DataFrame (columns=df_columns)
    chart = chart.rename_axis(label, axis="columns")
    
    for idx, param_dict in enumerate(get_generator_parameters(model_params), start=1):
        
        f1_score = classify_and_report(classifier(**param_dict), train_file, test_file)
        chart_dict = param_dict
        chart_dict["f1_score"] = f1_score
        chart.loc[idx] = chart_dict
    
    return chart

def make_decision_tree_chart(label, train_file, test_file):
    def get_parameters():
        return {
            "max_depth": [None, 8, 4, 2, 1],
            "max_leaf_nodes": [None, 2, 4, 8, 16],
            "min_samples_split": [2, 4, 8, 16, 32, 64],
        }
    
    return get_classifier_parameter_tuning_chart(label, train_file, test_file, get_parameters(), DecisionTreeClassifier)
    
def make_random_forest_chart(label, train_file, test_file):
    def get_parameters():
        return {
            "max_depth": [None, 8, 4, 2, 1],
            "max_leaf_nodes": [None, 16, 8, 4, 2],
            "bootstrap": [True, False],
        }
    
    return get_classifier_parameter_tuning_chart(label, train_file, test_file, get_parameters(), RandomForestClassifier)
    
def make_logistic_regression_chart(label, train_file, test_file):
    def get_parameters():
        return {
            "max_iter": [1, 10, 100, 1000, 10000, 100000],
        }
    
    return get_classifier_parameter_tuning_chart(label, train_file, test_file, get_parameters(), LogisticRegression)

def classify_then_export_result(label, train_file, test_file):
    tree_chart = make_decision_tree_chart(label, train_file, test_file)                 
    tree_chart.to_csv(f"./result/{label}_decision_tree.csv", index=False)
    print(f"Done {label} decision tree!")
    
    random_forest = make_random_forest_chart(label, train_file, test_file)              
    random_forest.to_csv(f"./result/{label}_random_forest.csv", index=False)
    print(f"Done {label} random forest!")
    
    logistic_regression = make_logistic_regression_chart(label, train_file, test_file)  
    logistic_regression.to_csv(f"./result/{label}_logistic_regression.csv", index=False)
    print(f"Done {label} logistic regression!")
    
    print(f"Done {label} all!")
    


os.system("rm -rf ./result/*") # purge previous results
classify_then_export_result("Android", android_train_file, android_test_file)
classify_then_export_result("Openstack", openstack_train_file, openstack_test_file)

print("Done!")

