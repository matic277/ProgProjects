package multi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

class Gram {
	String[] words;
	String ngram;
	
	int occurrences = 1;
	double probability = 0;
	
	public Gram(String[] words) {
		this.words = words;
		ngram = "";
		for (int i=0; i<words.length-1; i++) ngram += words[i] + " ";
		ngram += words[words.length-1];
	}
	public Gram(String ngram) {
		this.ngram = ngram;
	}
	
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#.#######");
		return "['" + ngram + "', " + occurrences + ", " + f.format(probability) + "%]";
	}
}
