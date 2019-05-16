package Words;

import java.util.Hashtable;

import Dictionaries.IDictionary;

public class Target implements IWord {
	
	String sourceWord;
	String word;
	
	IDictionary dictionary;
	
	public Target(String source, IDictionary dictionary) {
		sourceWord = source;
		this.dictionary = dictionary;
		
		word = sourceWord.substring(1);
	}
	
	public static boolean isType(String s) {
		if (s.charAt(0) == '@') return true;
		return false;
	}

	@Override
	public String getSourceWord() {
		return sourceWord;
	}

	@Override
	public String getTag() {
		return "<TRG>";
	}

	@Override
	public double getPleasantness() {
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getPleasantness();
		}
		return -1;
	}

	@Override
	public double getActivation() {
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getActivation();
		}
		return -1;
	}

	@Override
	public double getImagery() {
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getImagery();
		}
		return -1;
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + sourceWord + "', " + "P:" + getPleasantness() + "]";
	}

}
