package Words;

import AbstractWordClasses.AbsMeasurableWord;

public class Emoji extends AbsMeasurableWord {

	public Emoji(String source, String processed) {
		super(source, processed);
		super.tag = "EMJ";
	}


}
