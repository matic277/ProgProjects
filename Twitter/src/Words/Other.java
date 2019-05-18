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
	public double getPleasantness() {
		return -1;
	}

	@Override
	public double getActivation() {
		return -1;
	}

	@Override
	public double getImagery() {
		return -1;
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "']";
	}

}
