package Words;


public abstract class AbsMeasurableWord implements IWord {
	
	// magnitude, how much should
	// pleasantness get amplified by
	double magnitude = 1.3;
	
	double positiveThreshold = 0.3;
	double neutralThreshold = -0.3;
	
	// for all: [-1, 1]
	double pleasantness = 0;	// -1 (unpleasant) to 1 (pleasant)
	double activation = 0;		// -1 (passive) to 1 (active)
	double imagery = 0;			// -1 (difficult to form a mental picture of this word) to 1 (easy to form a mental picture)
	
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
}
