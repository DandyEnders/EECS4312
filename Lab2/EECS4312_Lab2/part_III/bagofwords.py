# 1. remove special characters like \n :
# 2. remove unique stence like "Change-Id : ..."
# 3. apply stemming : plays -> play
# 4. remove non-meaingful words like "for"
# 5. build bag of word table
# 6. remove rare token ( 3 or less frequency )
# 7. combine with original 

import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
import nltk
from nltk.corpus import stopwords 
from nltk.tokenize import word_tokenize 
from nltk.tokenize import RegexpTokenizer 
from nltk.stem import WordNetLemmatizer

nltk.download('stopwords')
nltk.download('wordnet')


def clean_function(p_string):
    stop_words = set(stopwords.words('english')) 
    custom_stop_words = ["", ":", "\\n", "\n", ".", "#", "--", '"', "'", "[", "]", "-", "()", "(", ")", '\\"',
                         "*"]
    
    stop_words = stop_words.union(custom_stop_words)
    splitting_word = ["\\n", "\\u003"]
    
    repetative_end_word = ["Orig-Change-Id", "Change-Id:", "Signed-off-by"]
    
    lem = WordNetLemmatizer()
    
    result = p_string
    
    for rew in repetative_end_word:
        if rew in result:
            reg_tok = RegexpTokenizer(f"(.*){rew}")
            result = " ".join(reg_tok.tokenize(result))

    for sw in splitting_word:
        result = result.replace(sw, " ")
    
    word_tokens = word_tokenize(result)
    
    filtered_sentence = [w for w in word_tokens if not w in stop_words] 
    lemminized_setence = [lem.lemmatize(w) for w in filtered_sentence]
    
    result = " ".join(lemminized_setence)
    
    return result

def bag_of_wordify(message_df):
    comments = message_df['Comment']
    
    count = CountVectorizer()
    bag_of_words_vector = count.fit_transform(comments)
    
    feature_names = count.get_feature_names()
    bag_of_words_df = pd.DataFrame(bag_of_words_vector.toarray(), columns=feature_names)
    
    # remove all columns sum <= 3
    bag_of_words_df.drop([col for col, val in bag_of_words_df.sum().iteritems() if val <= 3], axis=1, inplace=True)
    return bag_of_words_df
    

def add_bag_of_word_feature(train_data_df, test_data_df, message_df):
    # Clean messages
    message_df["Comment"] = message_df["Comment"].apply(clean_function)
    # Bag of wordify
    bag_of_word_df = bag_of_wordify(message_df)
    # Insert their respective ID
    bag_of_word_df.insert(0, "Id", train_data_df.loc[:, "Id"]) 
    
    # Left merge the bag of word data
    train_data_df = train_data_df.merge(bag_of_word_df, how="left", on="Id")
    test_data_df = test_data_df.merge(bag_of_word_df, how="left", on="Id")
    
    return train_data_df, test_data_df

    
if __name__ == "__main__":
    a = pd.read_csv("./../data/android/training.csv")
    b = pd.read_csv("./../data/android/test.csv")
    c = pd.read_csv("./../data/android/android-commit-messages.csv", encoding="Windows-1252")
    
    d, _ = add_bag_of_word_feature(a, b, c)
    d.to_csv("result.csv")
    # c = add_bag_of_word_feature(a, b)