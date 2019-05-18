package Words;

import java.text.DecimalFormat;

import Dictionaries.DictionaryCollection;
import Dictionaries.IDictionary;

public class Hashtag extends AbsMeasurableWord implements IWord {
	
	String sourceText;
	String word;
	
	public Hashtag(String source) {
		sourceText = source;
		word = "";
		
		// removes # from hashtag:
		// #something -> something
		word = source.substring(1);
	}
	
	public static boolean isType(String s) {
		if (s.charAt(0) == '#') return true;
		return false;
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<HTG>";
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
		return -2;
	}

	@Override
	public double getImagery() {
		return -2;
	}
	
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", '" + sourceText + "', P: " + format.format(pleasantness) + "]";
	}

}
