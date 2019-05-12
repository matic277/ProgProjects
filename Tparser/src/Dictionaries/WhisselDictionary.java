package Dictionaries;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;

import Words.AffectionWord;

public class WhisselDictionary implements IDictionary {
	
	Hashtable<String, AffectionWord> hashTable;
	
	public WhisselDictionary(String relativeFilePath) throws IOException {
		System.out.println("Creating hashtable of Whissel dictionary...");
		
		// hashtable, key -> word, value -> node(word and statistics)
		hashTable = new Hashtable<String, AffectionWord>(9000);
		
		Stream<String> linesToRead;
		
		// reading from 8-th line forward
		try (Stream<String> lines = Files.lines(Paths.get(relativeFilePath), Charset.defaultCharset())) {
			linesToRead = lines.skip(8);
			linesToRead.forEachOrdered(line -> processLine(line));
		}
		
		System.out.println("\t-> Numer of entries: " + hashTable.size());
		
		checkIntegrity();

		System.out.println("\t-> Done.");
		System.out.println();
	}
	
	private void processLine(String s) {
		// structure:
		// word,pleasantness,activation,imagery
		String tokens[];
		tokens = s.split(",");

		hashTable.put(tokens[0], new AffectionWord(tokens[0], tokens[1], tokens[2], tokens[3]));
	}
	
	private void checkIntegrity() {
		System.out.println("\t\t-> Checking integrity...");
		hashTable.forEach((word, node) -> {
			if (!node.checkIntegrity()) {
				System.out.println("\t\t-> Maybe a bad node: " + node.toString());
			}
		});
	}
	
	public boolean contains(String key) {
		return hashTable.containsKey(key);
	}
	
	public AffectionWord getEntry(String key) {
		return hashTable.get(key);
	}
}
