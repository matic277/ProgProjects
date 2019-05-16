package Dictionaries;

import Words.IWord;

public interface IDictionary {
	
	boolean contains(String key);
	IWord getEntry(String key);

}
