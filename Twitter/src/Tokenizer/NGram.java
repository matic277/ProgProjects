package Tokenizer;

import java.util.HashMap;

public class NGram {
	
	int n;
	
	HashMap<String, Gram> table;
	
	public NGram(int n) {
		this.n = n;
	}

}

class Gram {
	
	String text;
	double probability;
	
}
