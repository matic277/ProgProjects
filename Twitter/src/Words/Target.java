package Words;

public class Target implements IWord {
	
	String sourceWord;
	String word;
	
	public Target(String source) {
		sourceWord = source;
		word = sourceWord.substring(1);
	}
	
	public static boolean isType(String s) {
		if (s.charAt(0) == '@') return true;
		return false;
	}

	@Override
	public String getSourceText() {
		return sourceWord;
	}

	@Override
	public String getTag() {
		return "<TRG>";
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + sourceWord + "']";
	}

}
