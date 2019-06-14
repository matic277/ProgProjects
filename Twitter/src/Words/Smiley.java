package Words;

import java.text.DecimalFormat;

import Dictionaries.DictionaryCollection;
import Dictionaries.IDictionary;
import Dictionaries.INode;
import Dictionaries.SmileyDictionary;

public class Smiley extends AbsMeasurableWord implements INode {
	
	String sourceText;
	
	public Smiley(String sourceText, double pleasantness) {
		this.sourceText = sourceText;
		this.pleasantness = pleasantness;
	}
	public Smiley(String sourceText, String plesantness) {
		this.sourceText = sourceText;
		this.pleasantness = Double.parseDouble(plesantness);
	}
	public Smiley(String sourceText) {
		this.sourceText = sourceText;
		SmileyDictionary dictionary = (SmileyDictionary) DictionaryCollection.getDictionaryCollection().getSmileyDictionary();
		this.pleasantness = dictionary.getEntry(sourceText).getPleasantness();
	}
	
	public static boolean isType(String s) {
		return DictionaryCollection.getDictionaryCollection().getSmileyDictionary().contains(s);
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<SML>";
	}

	@Override
	public boolean checkIntegrity() {
		if (sourceText.length() > 1 && pleasantness >= -1 && pleasantness <= 1) return true;
		return false;
	}

	@Override
	public String getString() {
		return sourceText;
	}
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", " + getSentimentTag() + ", '" + sourceText + "', P: " + format.format(pleasantness) + "]";
	}
}
