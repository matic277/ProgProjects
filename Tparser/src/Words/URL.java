package Words;

public class URL implements IWord {
	
	String sourceText;
	
	public URL(String source) {
		sourceText = source;
	}
	
	public static boolean isType(String s) {
		String regExPattern = "(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
		return s.matches(regExPattern);
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
