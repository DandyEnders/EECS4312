import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ArffLoader;



public class NiveBayes {
	public String TrainingPath; // path to training data
	public String TestPath; // path to test data
	
	public  Instances TrainingData;
	public  Instances TestingData;

	public Map<String, String> mappings = new HashMap<String, String>();
	public  Map<Integer, Double> testscores = new HashMap<Integer, Double>();

		
	
	public NiveBayes(String trainingPath, String testPath) {
		super();
		this.TrainingPath = trainingPath;
		this.TestPath = testPath;

	}
	
	/**
	 * @desc loading training and testing data
	 * @throws Exception
	 */
	public void loadData() throws Exception {
		ArffLoader loader = new ArffLoader();
		loader.setSource(new File(this.TrainingPath));
		Instances trainingSet = loader.getDataSet();
		trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
		TrainingData = trainingSet;
		
		loader = new ArffLoader();
		loader.setSource(new File(this.TestPath));
		Instances testSet = loader.getDataSet();
		testSet.setClassIndex(testSet.numAttributes() - 1);
		TestingData = testSet;
		
	}


	
	public  void niveBayes() throws Exception{
		 NaiveBayes naive = new NaiveBayes();   
		 
		 Classifier cls = naive;
		 cls.buildClassifier(TrainingData);
		 Evaluation eval = new Evaluation(TrainingData);
		 
		 //10-fold cross-validation
		 eval.crossValidateModel(cls, TrainingData, 10, new Random(1));
		 eval.evaluateModel(cls, TestingData);
		 
		 //Focus on evaluating performance on identifying label "1", i.e., isReq
		 System.out.println("======Naive Bayes======");
		 System.out.println("Precision: "+eval.precision(1));
		 System.out.println("Recall: " +eval.recall(1));
		 System.out.println("F1: "+eval.fMeasure(1));
		 
	}
	
	
		
	public static void main(String[]args) throws Exception{
			String TrainingPath = "./data/ant-1.9.2.arff";
			String TestPath = "./data/ant-1.9.3.arff";
				
			NiveBayes adtree = new NiveBayes(TrainingPath, TestPath);
			adtree.loadData();
			adtree.niveBayes();
	}
}
