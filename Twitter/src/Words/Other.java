package Words;

public class Other implements IWord {
	
	String sourceText;
	
	public Other(String sourceText) {
		this.sourceText = sourceText;
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<OTR>";
	}
	
	@Override
	public void setPleasantness(double pleasantness) {
		
	}

	@Override
	public double getPleasantness() {
		return -2;
	}

	@Override
	public double getActivation() {
		return -2;
	}

	@Override
	public double getImagery() {
		return -2;
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "']";
	}
	
	@Override
	public void setFlipPleasantness() {
		
	}
}
