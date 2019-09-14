package common;

import java.text.DecimalFormat;

public class Gram {
	
	public String ngram;
	
	public double occurrences = 1;
	public double probability = 0;
	
	public Gram(String ngram) {
		this.ngram = ngram;
	}
	
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#.#######");
		return "['" + ngram + "', " + (int)occurrences + ", " + f.format(probability) + "%]";
	}
	
	public String encode() {
		return ngram + "|" + occurrences + "|" + probability;
	}
}