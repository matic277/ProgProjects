package Dictionaries;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;

import Words.IWord;
import Words.StopWord;

public class StopwordDictionary implements IDictionary {
	
	Hashtable<String, StopWord> hashTable;
	
	public StopwordDictionary(String relativeFilePath) throws IOException {
		System.out.println("Creating hashtable for stop-words...");
		
		// hashtable, key -> word, value -> node(word and statistics)
		hashTable = new Hashtable<String, StopWord>(200);
		
		try (Stream<String> lines = Files.lines(Paths.get(relativeFilePath), Charset.defaultCharset())) {
			lines.forEachOrdered(
				line -> hashTable.put(line, new StopWord(line))
			);
		}
		
		System.out.println("\t|-> Numer of entries: " + hashTable.size());
		
		checkIntegrity();

		System.out.println("\t|-> Done.");
		System.out.println();
	}
	
	private void checkIntegrity() {
		System.out.println("\t|-> Checking integrity...");
		hashTable.forEach((word, node) -> {
			if (!node.checkIntegrity()) {
				System.out.println("\t|\t|-> Maybe a bad node: " + node.toString());
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
