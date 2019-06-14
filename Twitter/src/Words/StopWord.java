package Words;

import Dictionaries.DictionaryCollection;
import Dictionaries.INode;

public class StopWord implements INode, IWord {
	
	String sourceText = "";
	
	public StopWord(String sourceText) {
		this.sourceText = sourceText;
	}
	
	public static boolean isType(String s) {
		return DictionaryCollection.getDictionaryCollection().getStopwordDictionary().contains(s);
	}

	@Override
	public boolean checkIntegrity() {
		if (sourceText.length() < 2 || sourceText.length() > 10) return false;
		return true;
	}

	@Override
	public String getString() {
		return sourceText;
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<STP>";
	}
	
	@Override
	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "']";
	}
}
