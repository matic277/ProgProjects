package Dictionaries;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;

import Words.IWord;
import Words.Smiley;

public class SmileyDictionary implements IDictionary {

	Hashtable<String, Smiley> hashTable;
	
	public SmileyDictionary(String relativeFilePath) throws IOException {
		System.out.println("Creating hashtable for smileys...");
		
		// hashtable, key -> word, value -> node(word and statistics)
		hashTable = new Hashtable<String, Smiley>(110);
		
		Stream<String> linesToRead;
		try (Stream<String> lines = Files.lines(Paths.get(relativeFilePath), Charset.defaultCharset())) {
			linesToRead = lines.skip(3);
			linesToRead.forEachOrdered(
				line -> processLine(line)
			);
		}
		
		System.out.println("\t-> Numer of entries: " + hashTable.size());
		
		checkIntegrity();

		System.out.println("\t-> Done.");
		System.out.println();
	}
	
	private void processLine(String line) {
		// data structure: smiley,plesantness
		String[] tokens = line.split(",");
		
		hashTable.put(tokens[0], new Smiley(tokens[0], tokens[1]));
	}

	private void checkIntegrity() {
		System.out.println("\t-> Checking integrity...");
		hashTable.forEach((word, node) -> {
			if (!node.checkIntegrity()) {
				System.out.println("\t\t-> Maybe a bad node: " + node.toString());
			}
		});
	}

	@Override
	public boolean contains(String key) {
		return hashTable.containsKey(key);
	}

	@Override
	public IWord getEntry(String key) {
		return hashTable.get(key);
	}

}
