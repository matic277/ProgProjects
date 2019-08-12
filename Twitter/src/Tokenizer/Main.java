package Tokenizer;

import java.io.IOException;
import java.util.ArrayList;

import AbstractWordClasses.AbsWord;
import Dictionaries.DictionaryCollection;
import StreamConsumer.StreamHandler;
import ngramStatistics.NGramBuilder;

public class Main {
	
	public static void main(String[] args) throws IOException {
		DictionaryCollection.getDictionaryCollection();
		// build dictionaries first, not necessary
		// but when tweets start streaming in, its
		// nice to have them already built
		DictionaryCollection.getDictionaryCollection();

//		StreamHandler stream = new StreamHandler();
//		
//		// debugging purpose thread
//		new Thread() {
//			@Override
//			public void run() {
//				System.out.println("debugger started");
//				
//				while (true){
//					logTweetsIfNeccessary();
//
//					System.out.println("Tweets in queue:  " + stream.tweets.size());
//					System.out.println("Processed tweets: " + stream.processedTweets.size());
//					
//					try { Thread.sleep(2000); }
//					catch (InterruptedException e) { e.printStackTrace(); }
//				}
//			}
//			
//			// if there are more than x tweets processed,
//			// log them to file and clear the list
//			public void logTweetsIfNeccessary() {
//				if (stream.processedTweets.size() > 30) {
//					Tweet[] tweets = stream.processedTweets.toArray(new Tweet[stream.processedTweets.size()]);
////					new Logger(stream.processedTweets.toArray());
//					Logger logger = new Logger(tweets);
//					logger.saveResults();
//					logger.saveResultsAsCsv();
//					logger.saveResultsWithStatisticsAsCsv();
//					stream.processedTweets.clear();
//				}
//			}
//			
//		}.start();

		
		test();

	}
	
	public static void test() {
//		NGramBuilder nb = new NGramBuilder(2);
		
		
		
		String tweet = "my friend thank you nice real estate";
		Tweet t = new Tweet(tweet, "TEST_TWEET");
		t.processTweet();
		System.out.println(t.getNGramFeatures());
		
//		NGram n = new NGram(1, tweet.split(" "));
//		System.out.println("----");
//		n.getListOfNGrams().forEach(g -> System.out.println(g.ngram));
//		System.out.println("----");
		
//		String tweet = "This is not a 0x1f609 brand new, nice! test tweet. www.sample.si @testTweet #manyWordsToSplitHere #one :)";
//		
//		//tweet = "not good lol :/";
//		
//		Tweet t = new Tweet(tweet, "TEST_TWEET");
//		t.processTweet();
//		
//		System.out.println("----- OUTPUT -----");
//		System.out.println(t.toString());
//		
//		System.out.println("----- OUTPUT2-----");
//		Classifier c = new Classifier(tweet);
//		System.out.println("Sentiment: " + c.getSentiment());
//		
//		
//		ArrayList<String> string = new ArrayList<String>(20);
//		ArrayList<AbsWord> words = t.getTokens();
//		words.forEach(w -> {
//			if (w instanceof Emoji) return;
//			if (w instanceof URL) return;
//			if (w instanceof Target) return;
//			string.add((w.getProcessedText() == null)? w.getSourceText() : w.getProcessedText());
//		});
//		
//		string.forEach(s -> System.out.print(s + " "));
//		System.out.println();
//		
//		NGram ngram = new NGram(3, string);
//		
//		ngram.orderAndPrintProbabilities();
	}

}
