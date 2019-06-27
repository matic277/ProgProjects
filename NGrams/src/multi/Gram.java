package multi;

import java.text.DecimalFormat;

class Gram {
	String[] words;
	String ngram;
	
	double occurrences = 1;
	double probability = 0;
	
	public Gram(String[] words) {
		this.words = words;
	}
	public Gram(String ngram) {
		this.ngram = ngram;
	}
	
	public Gram combine(Gram g) {
		this.occurrences += g.occurrences;
		return this;
	}
	
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#.#######");
		return "['" + ngram + "', " + (int)occurrences + ", " + f.format(probability) + "%]";
	}
}
