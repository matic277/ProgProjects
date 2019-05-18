package Words;

import java.text.DecimalFormat;

import Dictionaries.INode;

public class AffectionWord extends AbsMeasurableWord implements INode, IWord {
	
	String sourceText = "";
	
	public AffectionWord(String sourceText, double pleasantness, double activation, double imagery) {
		this.sourceText = sourceText;
		this.pleasantness = pleasantness;
		this.activation = activation;
		this.imagery = imagery;
	}
	
	public AffectionWord(String sourceText, String pleasantness, String activation, String imagery) {
		this.sourceText = sourceText;
		this.pleasantness = Double.parseDouble(pleasantness) - 2;
		this.activation = Double.parseDouble(activation) - 2;
		this.imagery = Double.parseDouble(imagery) - 2;
	}
	
	public boolean checkIntegrity() {
		if (sourceText.length() > 1 && checkValidValue(pleasantness) && checkValidValue(activation) && checkValidValue(imagery)) {
			return true;
		}
		return false;
	}
	
	private boolean checkValidValue(double value) {
		if (value >= -1 && value <= 1) return true;
		return false;
	}

	@Override
	public String getString() {
		return sourceText;
	}

	@Override
	public String getSourceWord() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<AFT>";
	}

	@Override
	public double getPleasantness() {
		return pleasantness;
	}

	@Override
	public double getActivation() {
		return activation;
	}

	@Override
	public double getImagery() {
		return imagery;
	}
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", '" + sourceText + "', P:" + format.format(pleasantness) + ", A:" + format.format(activation) + ", I:" + format.format(imagery) + "]";
	}
}
