package Words;

public class Other implements IWord {
	
	String word;
	
	public Other(String source) {
		word = source;
	}

	@Override
	public String getSourceWord() {
		return word;
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
		return "[" + getTag() + ", '" + word + "']";
	}

}
