package Words;

public class URL implements IWord {
	
	String sourceText;
	
	public URL(String source) {
		sourceText = source;
	}
	
	public static boolean isType(String s) {
		// https://rubular.com/r/eGPe4bGlwMd98E
		String regExPattern = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";
		return s.matches(regExPattern);
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<URL>";
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "']";
	}
}
