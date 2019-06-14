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

	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "']";
	}
}
