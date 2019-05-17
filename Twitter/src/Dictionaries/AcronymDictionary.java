package Dictionaries;

import java.io.IOException;

import Words.Acronym;

public class AcronymDictionary extends AbsDictionary {
	
	public AcronymDictionary(String relativeFilePath) throws IOException {
		super();
		buildHashtable(relativeFilePath, "acronyms", 0, 100);
		checkIntegrity();
	}
	
	public void processLine(String line) {
		// data: acronym,full text
		line = line.toLowerCase();
		String[] tokens = line.split(",");
		
		hashTable.put(tokens[0], new Acronym(tokens[0], tokens[1]));
	}
}
