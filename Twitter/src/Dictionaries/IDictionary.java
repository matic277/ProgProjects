package Dictionaries;

import AbstractWordClasses.IWord;

public interface IDictionary {
	
	boolean contains(String key);
	IWord getEntry(String key);

}
