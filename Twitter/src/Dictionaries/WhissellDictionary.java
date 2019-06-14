package Dictionaries;
import java.io.IOException;


import Words.AffectionWord;
import Words.IWord;
import Words.AbsMeasurableWord;

public class WhissellDictionary extends AbsDictionary {
	
	public WhissellDictionary(String relativeFilePath) throws IOException {
		super();
		buildHashtable(relativeFilePath, "Whissell Affection Words", 8, 9000);
		checkIntegrity();
	}

	@Override
	public void processLine(String s) {
		// structure:
		// word,pleasantness,activation,imagery
		String tokens[];
		tokens = s.split(",");
		
		// words *should* already be lower case, but still
		tokens[0] = tokens[0].toLowerCase();

		hashTable.put(tokens[0], new AffectionWord(tokens[0], tokens[1], tokens[2], tokens[3]));
	}
	
	public AbsMeasurableWord getEntry(String key) {
		return (AbsMeasurableWord) hashTable.get(key);
	}
}
