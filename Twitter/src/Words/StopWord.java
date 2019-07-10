package Words;

import AbstractWordClasses.AbsWord;
import Dictionaries.DictionaryCollection;
import Dictionaries.INode;

public class StopWord extends AbsWord implements INode {
	
	public StopWord(String source, String processed) {
		super(source, processed);
		super.tag = "STP";
	}
	
	public static boolean isType(String s) {
		return DictionaryCollection.getDictionaryCollection().getStopwordDictionary().contains(s);
	}

	@Override
	public boolean checkIntegrity() {
		if (sourceText.length() < 2 || sourceText.length() > 10) return false;
		return true;
	}
	
	@Override // INode
	public String getString() {
		return sourceText;
	}
}
