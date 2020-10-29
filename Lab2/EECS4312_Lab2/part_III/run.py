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
from bagofwords import add_bag_of_word_feature

import os

android_train_file = "../data/android/training.csv"
android_test_file = "../data/android/test.csv"
android_comment_file = "../data/android/android-commit-messages.csv"

openstack_train_file = "../data/openstack/training.csv"
openstack_test_file = "../data/openstack/test.csv"
openstack_comment_file = "../data/openstack/openstack-commit-messages.csv"

def classify_and_report (classifier, train_file, test_file, comment_file):
    comment_df = pd.read_csv(comment_file, encoding="Windows-1252")
    
    # load the training data as a matrix
    train_dataset = pd.read_csv(train_file, header=0)
    
    # load the testing data
    test_dataset = pd.read_csv(test_file, header=0)

    train_dataset, test_dataset = add_bag_of_word_feature(train_dataset, test_dataset, comment_df)
    
    # separate the data from the target attributes
    train_data = train_dataset.drop('isReq', axis=1)

    # remove unnecessary features
    train_data = train_data.drop('Id', axis=1)

    # the lables of training data. `label` is the title of the  last column in your CSV files
    train_target = train_dataset.isReq 

    # separate the data from the target attributes
    test_data = test_dataset.drop('isReq', axis=1)

    # remove unnecessary features
    test_data = test_data.drop('Id', axis=1)

    # the lables of test data
    test_target = test_dataset.isReq
    
    # gnb = GaussianNB()
    model = classifier.fit(train_data.astype('Sparse'), train_target.astype('Sparse'))
    test_pred = model.predict(test_data.astype('Sparse'))
    
    f1 = metrics.f1_score(test_target, test_pred, average="weighted", labels=[1])
    
    return f1

def get_generator_parameters(p_dict):
    keys = p_dict.keys()
    vals = p_dict.values()
    for instance in itertools.product(*vals):
        yield dict(zip(keys, instance))
        
def get_classifier_parameter_tuning_chart(label, train_file, test_file, comment_file, model_params, classifier):
    df_columns = [*model_params.keys()]
    df_columns.append("f1_score")
    chart = pd.DataFrame (columns=df_columns)
    chart = chart.rename_axis(label, axis="columns")
    
    for idx, param_dict in enumerate(get_generator_parameters(model_params), start=1):
        
        f1_score = classify_and_report(classifier(**param_dict), train_file, test_file, comment_file)
        chart_dict = param_dict
        chart_dict["f1_score"] = f1_score
        chart.loc[idx] = chart_dict
    
    return chart

def make_decision_tree_chart(label, train_file, test_file, comment_file):
    def get_parameters():
        return {
            "max_depth": [None],
            "max_leaf_nodes": [None],
            "min_samples_split": [32],
        }
    
    return get_classifier_parameter_tuning_chart(label, train_file, test_file, comment_file, get_parameters(), DecisionTreeClassifier)

def classify_then_export_result(label, train_file, test_file, comment_file):
    print(f"Working on {label} decision tree...")
    tree_chart = make_decision_tree_chart(label, train_file, test_file, comment_file)                 
    tree_chart.to_csv(f"./result/{label}_decision_tree.csv", index=False)
    print(f"Done {label} decision tree!")
    


os.system("rm -rf ./result/*") # purge previous results
classify_then_export_result("Android", android_train_file, android_test_file, android_comment_file)
classify_then_export_result("Openstack", openstack_train_file, openstack_test_file, openstack_comment_file)

print("Done!")

