package Words;

import Dictionaries.DictionaryCollection;
import Dictionaries.IDictionary;

public class Target implements IWord {
	
	String sourceWord;
	String word;
	
	public Target(String source) {
		sourceWord = source;
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
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getPleasantness();
		}
		return -1;
	}

	@Override
	public double getActivation() {
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getPleasantness();
		}
		return -1;
	}

	@Override
	public double getImagery() {
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getPleasantness();
		}
		return -1;
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + sourceWord + "', " + "P:" + getPleasantness() + "]";
	}

}
