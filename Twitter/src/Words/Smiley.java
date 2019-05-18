package Words;

import java.text.DecimalFormat;

import Dictionaries.INode;

public class Smiley extends AbsMeasurableWord implements IWord, INode {
	
	String sourceText;
	
	public Smiley(String sourceText, double pleasantness) {
		this.sourceText = sourceText;
		this.pleasantness = pleasantness;
	}
	public Smiley(String sourceText, String plesantness) {
		this.sourceText = sourceText;
		this.pleasantness = Double.parseDouble(plesantness);
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
		return "[" + getTag() + ", '" + sourceText + "', P: " + format.format(pleasantness) + "]";
	}

}
