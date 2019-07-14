import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

import Tokenizer.Classifier;
import Tokenizer.Tweet;


public class ClassifierTester {

	static String datasetPath = "datasets/processedDatasetEnglishTweetsOnly.txt";
	
	static ArrayList<TweetInstance> annotatedTweets = new ArrayList<TweetInstance>(3500);
	
	public static void main(String[] args) {
		readDataset();
		int correctlyClassified = 0;
		double increaseValue = -0.05;

//		while (Tweet.negativeThreshold >= -1) {
			
			for (int i=0; i<annotatedTweets.size(); i++) {
				TweetInstance tweet = annotatedTweets.get(i);
				Classifier c = new Classifier(tweet.text);
				
				int sentiment = c.getSentiment();
				if (tweet.sentiment == sentiment) {
					correctlyClassified++;
				}
			}
//			System.out.println("For value " + Tweet.negativeThreshold);
			System.out.println(correctlyClassified + "/" + annotatedTweets.size() + " -> " + ((double)correctlyClassified/annotatedTweets.size()));
//			System.out.println();
//			Tweet.negativeThreshold += increaseValue;
//			correctlyClassified = 0;
//		}
	}
	
	public static void readDataset() {
		System.out.print("Reading dataset....");
		try (Stream<String> lines = Files.lines(Paths.get(datasetPath), Charset.defaultCharset())) {
			lines.forEachOrdered(line -> {
				// sentiment,tweetID,tweetText
				String[] tokens = line.split(",");
				annotatedTweets.add(new TweetInstance(tokens[2], tokens[0]));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" done.");
	}
}

class TweetInstance {
	
	int sentiment;
	String text;
	
	public TweetInstance(String text, String sentiment) {
		this.text = text;
		this.sentiment = Integer.parseInt(sentiment);
	}
}
