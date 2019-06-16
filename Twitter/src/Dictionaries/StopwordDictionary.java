package Dictionaries;

import java.io.IOException;
import Words.StopWord;

public class StopwordDictionary extends AbsDictionary {
	
	public StopwordDictionary(String relativeFilePath) throws IOException {
		super();
		buildHashtable(relativeFilePath, "stop-words", 0, 120);
		checkIntegrity();
	}
	
	@Override
	public void processLine(String line) {
		hashTable.put(line, new StopWord(line));
	}

}
