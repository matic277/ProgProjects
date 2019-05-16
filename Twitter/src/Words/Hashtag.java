package Words;

import Dictionaries.IDictionary;

public class Hashtag implements IWord {
	
	String sourceText;
	String word;
	
	IDictionary dictionary;
	
	public Hashtag(String source, IDictionary dictionary) {
		sourceText = source;
		word = "";
		this.dictionary = dictionary;
		
		// removes # from hashtag:
		// #something -> something
		word = source.substring(1);
	}
	
	public static boolean isType(String s) {
		if (s.charAt(0) == '#') return true;
		return false;
	}

	@Override
	public String getSourceWord() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<HTG>";
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
		return -1;
	}

	@Override
	public double getImagery() {
		return -1;
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "', P: " + getPleasantness() + "]";
	}

}
