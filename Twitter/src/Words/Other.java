package Words;

import AbstractWordClasses.AbsWord;

public class Other extends AbsWord {
	
	public Other(String source, String processed) {
		super(source, processed);
		super.tag = "OTR";
	}
}
