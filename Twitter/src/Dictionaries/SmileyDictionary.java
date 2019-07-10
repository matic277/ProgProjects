package Dictionaries;

import java.io.IOException;

import AbstractWordClasses.AbsMeasurableWord;
import Words.Smiley;

public class SmileyDictionary extends AbsDictionary {

	public SmileyDictionary(String relativeFilePath) throws IOException {
		super();
		buildHashtable(relativeFilePath, "smileys", 3, 150);
		checkIntegrity();
	}
	
	private void checkIntegrity() {
		String strangeChars = " �!\"#$%&?Њ�Ǝ����~����`�����+�����…��";
		super.checkIntegrity(strangeChars);
	}
	
	@Override
	public void processLine(String line) {
		// data structure: smiley,plesantness
		// ‑ -> - (encoding problems)
		String[] tokens = line.split(",");
		tokens[0] = tokens[0].replace("‑", "-");

		
		hashTable.put(tokens[0], new Smiley(tokens[0], tokens[1]));
	}
	
	public AbsMeasurableWord getEntry(String key) {
		return (AbsMeasurableWord) hashTable.get(key);
	}
}
