package Dictionaries;

import AbstractWordClasses.AbsWord;

public interface IDictionary {
	
	boolean contains(String key);
	AbsWord getEntry(String key);

}
