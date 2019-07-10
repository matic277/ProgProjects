package Words;

import AbstractWordClasses.AbsWord;
import Dictionaries.DictionaryCollection;
import Dictionaries.INode;

public class NegationWord extends AbsWord implements INode {

	public NegationWord(String source, String processed) {
		super(source, processed);
		super.tag = "NEG";
	}
	
	public static boolean isType(String s) {
		return DictionaryCollection.getDictionaryCollection().getNegationwordDictionary().contains(s);
	}

	@Override // INode
	public boolean checkIntegrity() {
		if (sourceText.length() < 2 || sourceText.length() > 10) return false;
		return true;
	}

	@Override // INode
	public String getString() {
		return sourceText;
	}
}
