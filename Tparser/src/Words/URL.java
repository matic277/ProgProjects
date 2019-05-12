package Words;

public class URL implements IWord {
	
	String sourceText;
	
	public URL(String source) {
		sourceText = source;
	}

	@Override
	public String getSourceWord() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<URL>";
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
