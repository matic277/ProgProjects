package ngramStatistics;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DatasetReader {
	
	String relativeDatasetPath = "datasets/processedDatasetEnglishTweetsOnly.txt";
	ArrayList<String> positiveTweets;
	ArrayList<String> neutralTweets;
	ArrayList<String> negativeTweets;	
	
	public DatasetReader() {
		positiveTweets = new ArrayList<String>(800);
		negativeTweets = new ArrayList<String>(800);
		neutralTweets = new ArrayList<String>(800);
		
		readFromFile();
	}
	
	private void readFromFile() {
		System.out.print("Reading dataset '" + relativeDatasetPath + "'...");
		try (Stream<String> lines = Files.lines(Paths.get(relativeDatasetPath), Charset.defaultCharset())) {
			lines.forEachOrdered(line -> {
				// sentiment,tweetID,tweetText
				String[] tokens = line.split(",");
				String sentiment = tokens[0];
				String tweet = "";
				
				// maybe tweetText contains ',' chars so we have to 
				// potentially combine them
				for (int i=2; i<tokens.length; i++) {
					tweet += tokens[i];
				}
				tweet = tweet.replace("   ", " ");
				tweet = tweet.replace("  ", " ");
				if (sentiment.equals("1")) positiveTweets.add(tweet);
				else if (sentiment.equals("0")) neutralTweets.add(tweet);
				else if (sentiment.equals("-1")) negativeTweets.add(tweet);
				else throw new java.lang.Error("ERROR: Unknown sentiment! -> " + sentiment);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" done.");
		System.out.println("\t|-> Positive tweets: " + positiveTweets.size());
		System.out.println("\t|-> Neutral tweets: " + neutralTweets.size());
		System.out.println("\t\\-> Negative tweets: " + negativeTweets.size());
		System.out.println();
	}
	

}
