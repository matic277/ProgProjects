package Words;

import Dictionaries.DictionaryCollection;
import Dictionaries.INode;

public class NegationWord implements IWord, INode {
	
	String sourceText;
	
	public NegationWord(String sourceText) {
		this.sourceText = sourceText;
	}
	
	public static boolean isType(String s) {
		return DictionaryCollection.getDictionaryCollection().getNegationwordDictionary().contains(s);
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<NEG>";
	}

	@Override
	public boolean checkIntegrity() {
		if (sourceText.length() < 2 || sourceText.length() > 10) return false;
		return true;
	}
	
	@Override
	public double getActivation() {
		return -2;
	}

	@Override
	public double getImagery() {
		return -2;
	}

	@Override
	public double getPleasantness() {
		return -2;
	}

	@Override
	public String getString() {
		return sourceText;
	}
	
	@Override
	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "']";
	}

	@Override
	public void setPleasantness(double pleasantness) {
		
	}
	
	@Override
	public void setFlipPleasantness() {

	}
}
