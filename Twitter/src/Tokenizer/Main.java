package Tokenizer;

import java.io.IOException;

import Dictionaries.DictionaryCollection;
import Dictionaries.IDictionary;
import Dictionaries.SmileyDictionary;
import StreamConsumer.StreamHandler;
import StreamConsumer.Tweet;
import Words.AffectionWord;
import Words.Smiley;

public class Main {
	
	public static void main(String[] args) throws IOException {
		// build dictionaries first, not necessary
		// but when tweets start streaming in, its
		// nice to have them already built
		DictionaryCollection.getDictionaryCollection();
		
		// twitter stream collector
		StreamHandler stream = new StreamHandler();
		
		// debugging purpose thread
		new Thread() {
			public void run() {
				System.out.println("debugger started");
				
				while (true){
					logTweetsIfNeccessary();

					System.out.println("Tweets in queue:  " + stream.tweets.size());
					System.out.println("Processed tweets: " + stream.processedTweets.size());
					
					try { Thread.sleep(2000); }
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
			
			// if there are more than _x_ tweets processed,
			// log them to file and clear the list
			public void logTweetsIfNeccessary() {
				if (stream.processedTweets.size() > 30) {
					ProcessedTweet[] tweets = stream.processedTweets.toArray(new ProcessedTweet[stream.processedTweets.size()]);
//					new Logger(stream.processedTweets.toArray());
					Logger logger = new Logger(tweets);
					logger.saveResults();
					logger.saveResultsAsCsv();
					stream.processedTweets.clear();
				}
			}
			
		}.start();
		

		
//		test();
	}
	
	public static void test() {
		String tweet = "This is a  brand new, nice test tweet. www.sample.si @testTweet :)";
		
		//tweet = "not good lol :/";
		
		Tokenizer t = new Tokenizer(new Tweet(tweet, "TEST_TWEET"));
		System.out.println(t.processTweet().toString());
	}

}
