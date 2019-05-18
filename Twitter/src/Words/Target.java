package Words;

import java.text.DecimalFormat;

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
	public String getSourceText() {
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
		return -2;
	}

	@Override
	public double getActivation() {
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getPleasantness();
		}
		return -2;
	}

	@Override
	public double getImagery() {
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getPleasantness();
		}
		return -2;
	}
	
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", '" + sourceWord + "', " + "P:" + format.format(getPleasantness()) + "]";
	}

}
