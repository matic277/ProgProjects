package Main;
import java.io.IOException;

import Dictionaries.SmileyDictionary;
import Dictionaries.StopwordDictionary;
import Dictionaries.WhisselDictionary;

public class Main {

	public static WhisselDictionary whissell;
	public static StopwordDictionary stopwords;
	public static SmileyDictionary smileys;
	
	public static void main(String[] args) throws IOException {
		// paths to dictionaries
		String relativeFilePath_Whissell = "dictionary/dictionary_English.txt";
		String relativeFilePath_Stopwords = "dictionary/stopwords.txt";
		String relativeFilePath_Smileys = "dictionary/smileys.txt";
		
		whissell = new WhisselDictionary(relativeFilePath_Whissell);
		stopwords = new StopwordDictionary(relativeFilePath_Stopwords);
		smileys = new SmileyDictionary(relativeFilePath_Smileys);
		
		test();
	}
	
	public static void test() {
		String tweet = "This is a brand new, nice test tweet. @testTweet :)";
		
		WordTree tree = new WordTree(tweet);
		tree.classify();
		tree.printTree();
	}

}
