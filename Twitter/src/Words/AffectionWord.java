package Words;

import AbstractWordClasses.AbsMeasurableWord;
import Dictionaries.DictionaryCollection;
import Dictionaries.IDictionary;
import Dictionaries.INode;

public class AffectionWord extends AbsMeasurableWord implements INode {
	
	// use this class when reading from file and building hashtable
	public AffectionWord(String sourceText, String pleasantness, String activation, String imagery) {
		super(sourceText, null);
		
		// -2 for normalizing from [1, 3] to [-1, 1]
		this.pleasantness = Double.parseDouble(pleasantness) - 2;
		this.activation = Double.parseDouble(activation) - 2;
		this.imagery = Double.parseDouble(imagery) - 2;
	}
	
	// use this constructor when tokenizing
	public AffectionWord(String sourceText, String processedText) {
		super(sourceText, processedText);
		super.tag = "AFT";
		
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		AbsMeasurableWord word = (AbsMeasurableWord) dictionary.getEntry(processedText);
		
		this.pleasantness = word.getPleasantness();
		this.activation = word.getActivation();
		this.imagery = word.getImagery();
	}

	public static boolean isType(String s) {
		return DictionaryCollection.getDictionaryCollection().getWhissellDictionary().contains(s);
	}
	
	public boolean checkIntegrity() {
		if (sourceText.length() > 1 && checkValidValue(pleasantness) && checkValidValue(activation) && checkValidValue(imagery)) {
			return true;
		}
		return false;
	}

	@Override
	public String getString() {
		return sourceText;
	}

	@Override // INode funct
	public String getSourceText() {
		return sourceText;
	}

	@Override// INode funct
	public String getTag() {
		return super.getTag();
	}
}
