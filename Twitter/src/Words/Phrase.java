package Words;

import java.text.DecimalFormat;

import Dictionaries.INode;

public class Phrase extends AbsMeasurableWord implements INode {
	
	String sourceText;
	double pleasantness;
	
	public Phrase(String sourceText, double pleasantness) {
		this.sourceText = sourceText;
		this.pleasantness = pleasantness;
	}

	@Override
	public String getTag() {
		return "<PHR>";
	}

	@Override
	public boolean checkIntegrity() {
		if (sourceText.length() > 5 && sourceText.contains(" ") && pleasantness >= -1 && pleasantness <= 1)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", " + getSentimentTag() + ", '" + sourceText + "', P: " + format.format(pleasantness) + "]";
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}
	
	@Override
	public String getString() {
		return sourceText;
	}
}
