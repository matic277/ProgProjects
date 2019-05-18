package Words;

import Dictionaries.INode;

public class NegationWord implements IWord, INode {
	
	String sourceText;
	
	public NegationWord(String sourceText) {
		this.sourceText = sourceText;
	}

	@Override
	public String getSourceWord() {
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
		return -1;
	}

	@Override
	public double getImagery() {
		return -1;
	}

	@Override
	public double getPleasantness() {
		return -1;
	}

	@Override
	public String getString() {
		return sourceText;
	}
	
	@Override
	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "']";
	}


}
