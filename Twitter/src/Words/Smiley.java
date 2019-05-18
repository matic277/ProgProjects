package Words;

import java.text.DecimalFormat;

import Dictionaries.INode;

public class Smiley extends AbsMeasurableWord implements IWord, INode {
	
	String sourceWord;
	
	public Smiley(String source, double pleasantness) {
		sourceWord = source;
		this.pleasantness = pleasantness;
	}
	public Smiley(String source, String plesantness) {
		sourceWord = source;
		this.pleasantness = Double.parseDouble(plesantness);
	}

	@Override
	public String getSourceWord() {
		return sourceWord;
	}

	@Override
	public String getTag() {
		return "<SML>";
	}

	@Override
	public double getPleasantness() {
		return pleasantness;
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
	public boolean checkIntegrity() {
		if (sourceWord.length() > 1 && pleasantness >= -1 && pleasantness <= 1) return true;
		return false;
	}

	@Override
	public String getString() {
		return sourceWord;
	}
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", '" + sourceWord + "', P: " + format.format(pleasantness) + "]";
	}

}
