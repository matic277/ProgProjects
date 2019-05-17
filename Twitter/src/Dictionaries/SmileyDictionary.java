package Dictionaries;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;

import Words.IWord;
import Words.Smiley;

public class SmileyDictionary extends AbsDictionary {

	public SmileyDictionary(String relativeFilePath) throws IOException {
		super();
		buildHashtable(relativeFilePath, "smileys", 3, 110);
		checkIntegrity();
	}
	
	@Override
	public void processLine(String line) {
		// data structure: smiley,plesantness
		String[] tokens = line.split(",");
		
		hashTable.put(tokens[0], new Smiley(tokens[0], tokens[1]));
	}
}
