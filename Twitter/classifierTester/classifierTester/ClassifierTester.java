package classifierTester;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import Tokenizer.Classifier;
import Tokenizer.Tweet;


public class ClassifierTester {

	static String datasetPath;
	
	static ArrayList<ProcessedTweet> annotatedTweets = new ArrayList<ProcessedTweet>(130000);
	static ArrayList<ProcessedTweet> learningdataset = new ArrayList<ProcessedTweet>(50000);
	
	static final double learningPercentage = 1;
	
	public static final Random r = new Random();
	
	public static void main(String[] args) {
		
//		special();

//		threeWay();
		
		twoWay();
		
		
		
		testDataset();
		
		
//		testAgain();
	}
	
	public static void special() {
		// read entire dataset
		datasetPath = "datasets/processedDatasetEnglishTweetsOnly.txt";
		readAndCreateDatasets();
		
		System.out.println("Old dataset size: " + annotatedTweets.size());
		ArrayList<ProcessedTweet> at = new ArrayList<ProcessedTweet>(5000);
		annotatedTweets.forEach(t -> {
			if (t.annotatedSentiment != 0) at.add(t);
		});
		annotatedTweets = at;
		
		// re-create learning dataset
		System.out.print("Creating learning dataset...");
		
		learningdataset.clear();
		annotatedTweets.forEach(t -> {
			if (r.nextFloat() <= learningPercentage) 
				learningdataset.add(t);
		});
		System.out.println(" done. Learning dataset size: " + learningdataset.size());
		
		
		datasetPath = "datasets/tweets.txt";
		
		System.out.println("New dataset size: " + annotatedTweets.size());
	}
	
//	public static void testAgain() {
//		System.out.print("Testing again...");
//		Tweet.threshold = 0.05;
//		int c = 0;
//		for (TweetInstance t : annotatedTweets) {
//			Tweet pt = new Tweet(t.text, null);
//			pt.processTweet();
//			int annotatedSentiment = t.sentiment;
//			int processedSentiment = pt.getSentimentTwoWay();
//			
//			if (annotatedSentiment == processedSentiment) c++;
//		}
//		double a = (double) c / annotatedTweets.size();
//		System.out.println("done. \n Accuracy: " + a);
//	}

	public static void testDataset() {	
		System.out.print("Testing accuracy (using thresholds: ("+Tweet.positiveThreshold+", "+Tweet.threshold+", "+Tweet.negativeThreshold+"))...");
		
		// get sentiment and see if it matches
		int correctlyClassified = 0;
		double accuracy;
		for (ProcessedTweet t : annotatedTweets) {
			int sentiment = (datasetPath.contains("Only"))? t.tweet.getSentimentThreeWay() : t.tweet.getSentimentTwoWay();
			if (sentiment == t.annotatedSentiment) correctlyClassified++;
		}
		
		accuracy = (double) correctlyClassified / annotatedTweets.size();
		
		System.out.println(" done. \nSize of testing dataset: " + annotatedTweets.size());
		System.out.println("Accuracy -> " + accuracy);
		
		
		int p = 0, n = 0, neg = 0;
		for (ProcessedTweet t : annotatedTweets) 
			if (t.annotatedSentiment == 1) 
				p++; 
			else if (t.annotatedSentiment == 0) n++;
			else neg++;
		
		System.out.println("poz, neu, neg -> " + p + ", " + n + ", " + neg);
	}
	
	private static void twoWay() {
		datasetPath = "output/tweets.txt";
		readAndCreateDatasets();
		
		int correctlyClassified = 0;
		double increaseValue = 0.05;

		Tweet.threshold = -3;

		ArrayList<Result> results = new ArrayList<Result>(1000);
		
		int output = 0;
		for (Tweet.threshold=-3; Tweet.threshold<3; Tweet.threshold+=increaseValue) {
			for (int i=0; i<learningdataset.size(); i++) {
				ProcessedTweet tweet = learningdataset.get(i);
				int sentiment = tweet.tweet.getSentimentTwoWay();
				if (tweet.annotatedSentiment == sentiment) {
					correctlyClassified++;
				}
			}
			double accuracy = (double) correctlyClassified / (double) learningdataset.size();
			
			if (output % 10 == 0) {
				System.out.println("For value (threshold) -> (" + Tweet.threshold + ")");
				System.out.println(correctlyClassified + "/" + learningdataset.size() + " -> " + accuracy);
				System.out.println();
			}
			results.add(new Result(accuracy, correctlyClassified, Tweet.threshold, -99));
			correctlyClassified = 0;
			output++;
		}		
		
		results.sort((r1, r2) -> -Double.compare(r1.accuracy, r2.accuracy));
		
		System.out.println("Number of results: " + results.size());
		System.out.println("-------------------");
		System.out.println("Top 5 best performing thresholds:");
		for (int i=0; i<5; i++) System.out.println(results.get(i).toString());
		
		System.out.println("-------------------");
		System.out.println("Top 5 worst performing thresholds:");
		for (int i=results.size()-1; i>=(results.size()-5); i--) System.out.println(results.get(i).toString());
		System.out.println();
		
		// set the treshold to the best value (is saved at positivelimit)
		Tweet.threshold = results.get(0).positivelimit;
		Tweet.positiveThreshold = -99;
		Tweet.negativeThreshold = -99;
		
		System.out.println("Best threshold -> " + Tweet.threshold + "\n");
	}
	
	private static void threeWay() {
		// read entire dataset
		datasetPath = "datasets/processedDatasetEnglishTweetsOnly.txt";
		readAndCreateDatasets();
		
		int correctlyClassified = 0;
		double increaseValue = 0.05;

		Tweet.positiveThreshold = -3;
		Tweet.negativeThreshold = -3;
		
		ArrayList<Result> results = new ArrayList<Result>(1000);

		int output = 0;
		for ( ; Tweet.positiveThreshold < 3; Tweet.positiveThreshold+=increaseValue) {
			for (Tweet.negativeThreshold=-3 ; Tweet.negativeThreshold < 3; Tweet.negativeThreshold+=increaseValue) {
				
				for (int i=0; i<learningdataset.size(); i++) {
					ProcessedTweet tweet = learningdataset.get(i);
					int sentiment = tweet.tweet.getSentimentThreeWay();
					if (tweet.annotatedSentiment == sentiment) {
						correctlyClassified++;
					}
				}
				double accuracy = (double) correctlyClassified / (double) learningdataset.size();
				
				if (output % 10 == 0) {
//					System.out.println("For value (pos, neg) -> (" + Tweet.positiveThreshold + ", " + Tweet.negativeThreshold + ")");
//					System.out.println(correctlyClassified + "/" + learningdataset.size() + " -> " + accuracy);
//					System.out.println();
				}
				
				results.add(new Result(accuracy, correctlyClassified, Tweet.positiveThreshold, Tweet.negativeThreshold));
				correctlyClassified = 0;
				output++;
			}
		}
		
		results.sort((r1, r2) -> -Double.compare(r1.accuracy, r2.accuracy));
		
		System.out.println("Number of processed tweets: " + learningdataset.size());
		System.out.println("Number of results: " + results.size());
		System.out.println("-------------------");
		System.out.println("Top 10 best performing thresholds:");
		for (int i=0; i<10; i++) System.out.println(results.get(i).toString());
		
		System.out.println("-------------------");
		System.out.println("Top 10 worst performing thresholds");
		for (int i=results.size()-1; i>=(results.size()-11); i--) System.out.println(results.get(i).toString());

		// set the tresholds to the best values
		Tweet.positiveThreshold = results.get(0).positivelimit;
		Tweet.negativeThreshold = results.get(0).negativelimit;
		Tweet.threshold = -99;
		
		System.out.println("Best thresholds: (pos, neg) -> (" + Tweet.positiveThreshold + ", " + Tweet.negativeThreshold + ")\n");
	}

	public static void readAndCreateDatasets() {
		System.out.print("Reading and processing dataset....");
		try (Stream<String> lines = Files.lines(Paths.get(datasetPath), Charset.defaultCharset())) {
			lines.forEachOrdered(line -> {
				// za file processedDatasetEnglishTweetsOnly
				// sentiment,tweetID,tweetText
				
				// za file tweets.txt
				// sentiment,tweetText
				String[] tokens = line.split(",");
				String sentiment = tokens[0];
				String tweet = "";
				
				int ind = (datasetPath.contains("processedDatasetEnglishTweetsOnly"))? 2 : 1;
				for (int i=ind; i<tokens.length; i++) tweet += tokens[i];
				
				Tweet tw = new Tweet(tweet, null);
				tw.processTweet();
				
				annotatedTweets.add(new ProcessedTweet(tw, Integer.parseInt(sentiment)));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" done.");
		
		
		// create learning dataset
		System.out.print("Creating learning dataset...");
		
		annotatedTweets.forEach(t -> {
			if (r.nextFloat() <= learningPercentage) 
				learningdataset.add(t);
		});
		System.out.println(" done. Learning dataset size: " + learningdataset.size());
	}
}

class Result {
	double accuracy;
	int correctlyClassified;
	
	double positivelimit, negativelimit;
	
	public Result(double a, int cc, double posth, double negth) {
		accuracy = a; correctlyClassified = cc;
		negativelimit = negth; positivelimit = posth;
	}
	
	public String toString() {
		return "Thresholds: (pos, neg) -> (" + positivelimit + ", " + negativelimit + "). Accuracy: " + accuracy;
	}
}

class ProcessedTweet {
	Tweet tweet;
	int annotatedSentiment;
	
	public ProcessedTweet(Tweet tweet, int annotatedSentiment) {
		this.tweet = tweet;
		this.annotatedSentiment = annotatedSentiment;
	}
}

//class TweetInstance {
//	
//	int sentiment;
//	String text;
//	
//	public TweetInstance(String text, String sentiment) {
//		this.text = text;
//		this.sentiment = Integer.parseInt(sentiment);
//	}
//}
