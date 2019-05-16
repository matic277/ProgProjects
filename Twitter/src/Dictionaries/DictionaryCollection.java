package Dictionaries;

import java.io.IOException;

import Words.IWord;

public class DictionaryCollection {

	public WhissellDictionary whissell;
	public StopwordDictionary stopwords;
	public SmileyDictionary smileys;
	
	public DictionaryCollection() {
		
	}
	
	public void constructSmileyDictionary(String path) {
		try { smileys = new SmileyDictionary(path); }
		catch (IOException e) { e.printStackTrace(); }
	}

	public void constructStopwordDictionary(String path) {
		try { stopwords = new StopwordDictionary(path); }
		catch (IOException e) { e.printStackTrace(); }
	}

	public void constructWhissellDictionary(String path) {
		try { whissell = new WhissellDictionary(path); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	
	public boolean smileysContains(String key) {
		return smileys.contains(key);
	}
	public IWord smileysGetEntry(String key) {
		return smileys.getEntry(key);
	}
	
	
	public boolean whissellContains(String key) {
		return whissell.contains(key);
	}
	public IWord whissellGetEntry(String key) {
		return whissell.getEntry(key);
	}
	
	
	public boolean stopwordsContains(String key) {
		return stopwords.contains(key);
	}
	public IWord stopwordsGetEntry(String key) {
		return stopwords.getEntry(key);
	}
}
