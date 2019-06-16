package Words;

import java.text.DecimalFormat;

import Dictionaries.DictionaryCollection;
import Dictionaries.WhissellDictionary;

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
	public void setPleasantness(double pleasantness) {
		this.pleasantness = pleasantness;
		if (this.pleasantness < -1) this.pleasantness = -1;
		else if (this.pleasantness > 1) this.pleasantness = 1;
	}
	
	@Override
	public void setFlipPleasantness() {
		this.pleasantness *= -1;
	}

	
	// should split by upper-case chars: #HelloWorld -> [hello, word]
	// then find the most polarizing word and return its value??
	// (return the value of the most positive word or the most negative word)
	@Override
	public double getPleasantness() {
		WhissellDictionary dictionary = (WhissellDictionary) DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		if (dictionary.contains(word)) {
			return dictionary.getEntry(word).getPleasantness();
		}
		return 0;
	}
	
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", " + getSentimentTag() + ", '" + sourceText + "', P: " + format.format(pleasantness) + "]";
	}

}
