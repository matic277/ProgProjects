package Tokenizer;
import java.io.IOException;
import java.util.Iterator;

import Dictionaries.DictionaryCollection;
import StreamConsumer.StreamHandler;

public class Main {

	static DictionaryCollection dictionaries;
	
	public static void main(String[] args) throws IOException {
		// paths to dictionaries
		String relativeFilePath_Whissell = "dictionary/dictionary_English.txt";
		String relativeFilePath_Stopwords = "dictionary/stopwords.txt";
		String relativeFilePath_Smileys = "dictionary/smileys.txt";
		
		dictionaries = new DictionaryCollection();
		dictionaries.constructWhissellDictionary(relativeFilePath_Whissell);
		dictionaries.constructSmileyDictionary(relativeFilePath_Smileys);
		dictionaries.constructStopwordDictionary(relativeFilePath_Stopwords);
		
		StreamHandler stream = new StreamHandler();
		
		new Thread() {
			public void run() {
				System.out.println("debugger started");
				
				while (true){
					System.out.println("Size of list of tweets: " + stream.tweets.size());
					
					if (stream.tweets.size() > 0)  {
						System.out.println("Latest tweet from the list: ");
						System.out.println(stream.tweets.getLast());
					}
					
					try { Thread.sleep(1000); }
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}.start();

		//test();
	}
	
	public static void test() {
		String tweet = "This is a  brand new, nice test tweet. www.sample.si @testTweet :)";
		
		Tokenizer t = new Tokenizer(tweet, dictionaries);
		t.classify();
		t.print();
	}

}
