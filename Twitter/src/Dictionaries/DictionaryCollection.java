package Dictionaries;

import java.io.IOException;

public class DictionaryCollection {
	
	// paths to dictionaries
	private static String relativeFilePath_Whissell = "dictionary/dictionary_English.txt";
	private static String relativeFilePath_Stopwords = "dictionary/stopwords.txt";
	private static String relativeFilePath_Smileys = "dictionary/smileys.txt";

	private WhissellDictionary whissell;
	private StopwordDictionary stopwords;
	private SmileyDictionary smileys;
	
	private static DictionaryCollection dictionaries;
	
	private DictionaryCollection() { }
	
	public static DictionaryCollection getDictionaryCollection() {
		if (dictionaries == null) {
			dictionaries = new DictionaryCollection();
			dictionaries.constructSmileyDictionary(relativeFilePath_Smileys);
			dictionaries.constructStopwordDictionary(relativeFilePath_Stopwords);
			dictionaries.constructWhissellDictionary(relativeFilePath_Whissell);
			return dictionaries;
		} else {
			return dictionaries;
		}
	}
	
	private void constructSmileyDictionary(String path) {
		try { smileys = new SmileyDictionary(path); }
		catch (IOException e) { e.printStackTrace(); }
	}

	private void constructStopwordDictionary(String path) {
		try { stopwords = new StopwordDictionary(path); }
		catch (IOException e) { e.printStackTrace(); }
	}

	private void constructWhissellDictionary(String path) {
		try { whissell = new WhissellDictionary(path); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public IDictionary getSmileyDictionary() {
		return smileys;
	}
	public IDictionary getWhissellDictionary() {
		return whissell;
	}
	public IDictionary getStopwordsDictionary() {
		return stopwords;
	}
}
