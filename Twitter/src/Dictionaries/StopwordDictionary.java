package Dictionaries;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;

import Words.IWord;
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
