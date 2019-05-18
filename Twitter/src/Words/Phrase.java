package Words;

import java.text.DecimalFormat;

import Dictionaries.INode;

public class Phrase implements IWord, INode {
	
	String sourceText;
	double pleasantness;
	
	public Phrase(String sourceText, double pleasantness) {
		this.sourceText = sourceText;
		this.pleasantness = pleasantness;
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<PHR>";
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
		return pleasantness;
	}

	@Override
	public boolean checkIntegrity() {
		if (sourceText.length() > 5 && sourceText.contains(" ") && pleasantness >= -1 && pleasantness <= 1)
			return true;
		return false;
	}

	@Override
	public String getString() {
		return sourceText;
	}
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", '" + sourceText + "', P: " + format.format(pleasantness) + "]";
	}
}
