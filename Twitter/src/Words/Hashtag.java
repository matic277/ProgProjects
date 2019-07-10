package Words;

import AbstractWordClasses.AbsMeasurableWord;

public class Hashtag extends AbsMeasurableWord {
	
	String[] words;

	public Hashtag(String source, String processed) {
		super(source, null);
		super.tag = "HTG";
		sourceText = source;

		// removes # from hashtag:
		// #something -> something
		processed = source.substring(1);
		super.processedText = processed;
		
		// splits by upper case
		words = processedText.split("(?=[A-Z])");
		
		// should split by upper-case chars: #HelloWorld -> [hello, word]
		// then find the most polarizing word and return its value??
		// (return the value of the most positive word or the most negative word)
		// TODO: implement something
		
	}
	
	public static boolean isType(String s) {
		if (s.charAt(0) == '#') return true;
		return false;
	}
}
