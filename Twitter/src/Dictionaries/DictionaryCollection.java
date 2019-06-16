package Dictionaries;

import java.io.IOException;

public class DictionaryCollection {
	
	// paths to dictionaries
	private final String relativeFilePath_Whissell = "dictionary/dictionary_English.txt";
	private final String relativeFilePath_Stopwords = "dictionary/stopwords.txt";
	private final String relativeFilePath_Smileys = "dictionary/smileys.txt";
	private final String relativeFilePath_Negationwords = "dictionary/negationwords.txt";
	private final String relativeFilePath_Acronym = "dictionary/acronyms.txt";

	private WhissellDictionary whissell;
	private StopwordDictionary stopwords;
	private SmileyDictionary smileys;
	private NegationwordDictionary negationwords;
	private AcronymDictionary acronym;
	
	private static DictionaryCollection dictionaries;
	
	private DictionaryCollection() { }
	
	public static DictionaryCollection getDictionaryCollection() {
		if (dictionaries == null) {
			dictionaries = new DictionaryCollection();
			dictionaries.constructSmileyDictionary();
			dictionaries.constructStopwordDictionary();
			dictionaries.constructWhissellDictionary();
			dictionaries.constructNegationwordDictionary();
			dictionaries.constructAcronymDictionary();
			return dictionaries;
		}
		return dictionaries;
	}
	
	private void constructSmileyDictionary() {
		try { smileys = new SmileyDictionary(relativeFilePath_Smileys); }
		catch (IOException e) { e.printStackTrace(); }
	}

	private void constructStopwordDictionary() {
		try { stopwords = new StopwordDictionary(relativeFilePath_Stopwords); }
		catch (IOException e) { e.printStackTrace(); }
	}

	private void constructWhissellDictionary() {
		try { whissell = new WhissellDictionary(relativeFilePath_Whissell); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	private void constructNegationwordDictionary() {
		try { negationwords = new NegationwordDictionary(relativeFilePath_Negationwords); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	private void constructAcronymDictionary() {
		try { acronym = new AcronymDictionary(relativeFilePath_Acronym); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public IDictionary getSmileyDictionary() {
		return smileys;
	}
	public IDictionary getWhissellDictionary() {
		return whissell;
	}
	public IDictionary getStopwordDictionary() {
		return stopwords;
	}
	public IDictionary getNegationwordDictionary() {
		return negationwords;
	}
	public IDictionary getAcronymDictionary() {
		return acronym;
	}
}
