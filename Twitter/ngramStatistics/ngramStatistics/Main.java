package ngramStatistics;

import Dictionaries.DictionaryCollection;

public class Main {
	
	public static void main(String[] args) {
		DictionaryCollection.setPrintingOption(false);
		NGramBuilder nb = new NGramBuilder(3);
	}

}
