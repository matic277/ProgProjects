package Dictionaries;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;
import Words.IWord;
import Words.NegationWord;

public class NegationwordDictionary extends AbsDictionary {
	
	public NegationwordDictionary(String relativeFilePath) throws IOException {
		super();
		buildHashtable(relativeFilePath, "negation-words", 0, 30);
		checkIntegrity();
	}
	
	@Override
	public void processLine(String line) {
		// data structure: word
		// ' -> ’ (file reading and encoding problem)
		String processedLine = line.toLowerCase();
		processedLine = processedLine.replace("’", "'");

		hashTable.put(processedLine, new NegationWord(processedLine));
	}
}