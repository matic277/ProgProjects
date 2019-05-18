package Words;


abstract class AbsMeasurableWord {
	
	// magnitude, how much should
	// pleasantness get amplified by
	double magnitude = 1.2;
	
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
	
	public double magnifyPleasantness() {
		pleasantness *= magnitude;
		if (pleasantness > 1) {
			pleasantness = 1;
			return 1;
		}
		if (pleasantness < -1) {
			pleasantness = -1;
			return -1;
		}
		return pleasantness;
	}
}
