package AbstractWordClasses;

import java.text.DecimalFormat;

public abstract class AbsMeasurableWord extends AbsWord implements IWord {
	
	// magnitude, how much should
	// pleasantness get amplified by
	protected double magnitude = 1.3;
	
	protected double positiveThreshold = 0.3;
	protected double neutralThreshold = -0.3;
	
	// for all: [-1, 1]
	protected double pleasantness = 0;	// (unpleasant) to (pleasant)
	protected double activation = 0;	// passive) to (active)
	protected double imagery = 0;		// (difficult to form a mental picture of this word) to (easy to form a mental picture)
	
	public AbsMeasurableWord(String source, String processed) {
		super(source, processed);
	}
	
	public boolean isPositivePleasantness() {
		if (pleasantness > positiveThreshold) return true;
		return false;
	}
	
	public boolean isNeutralPleasantness() {
		if (pleasantness >= neutralThreshold && pleasantness <= positiveThreshold) return true;
		return false;
	}
	
	public boolean isNegativePleasantness() {
		if (pleasantness < neutralThreshold) return true;
		return false;
	}
	
	public void magnifyPleasantness() {
		pleasantness *= magnitude;
		if (pleasantness > 1) {
			pleasantness = 1;
		}
		else if (pleasantness < -1) {
			pleasantness = -1;
		}
	}
	
	protected String getSentimentTag() {
		if (isNeutralPleasantness()) return "(NEU)";
		if (isPositivePleasantness()) return "(POS)";
		return "(NEG)";
	}
	
	protected boolean checkValidValue(double value) {
		if (value >= -1 && value <= 1) return true;
		return false;
	}
	
	public void setPleasantness(double pleasantness) {
		this.pleasantness = pleasantness;
		if (this.pleasantness < -1) this.pleasantness = -1;
		else if (this.pleasantness > 1) this.pleasantness = 1;
	}
	
	public void setFlipPleasantness() {
		pleasantness *= -1;
	}
	
	public double getPleasantness() {
		return pleasantness;
	}
	
	public double getActivation() {
		return activation;
	}
	
	public double getImagery() {
		return imagery;
	}
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", " + getSentimentTag() + ", src:'" + sourceText + "', prc:'" + processedText + "', P:" 
				+ format.format(pleasantness) + ", A:" + format.format(activation) + ", I:" + format.format(imagery) + "]";
	}
}
