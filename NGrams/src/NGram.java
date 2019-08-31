
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class NGram {
	
	// SINGLE THREADED
	
	int n;
	
	String[] wordsarr;
	ArrayList<String> wordslist;
	
	final int numberOfGrams;
	
	HashMap<String, Gram> table;
	ArrayList<Gram> list;
	
	boolean print = true;
	long t;
	
	public NGram(int n, String[] words, boolean printingOption) {
		this.n = n;
		this.print = printingOption;
		this.wordsarr = words;
		this.table = new HashMap<String, Gram>(words.length);
		this.list = new ArrayList<Gram>(words.length);
		this.numberOfGrams = words.length - n;
		
		computeNGramsFromStringArray();
	}
	public NGram(int n, ArrayList<String> words,  boolean printingOption) {
		this.n = n;
		this.print = printingOption;
		this.wordslist = words;
		this.table = new HashMap<String, Gram>(words.size());
		this.list = new ArrayList<Gram>(words.size());
		this.numberOfGrams = words.size() - n;
		
		computeNGramsFromArrayList();
	}
	
	public void computeNGramsFromArrayList() {
		if (print) {
			System.out.println("Creating " + n + "-grams...");
			t = System.currentTimeMillis();
		}
		
		for (int i=0; i<wordslist.size()-n; i++) {
			String ngram = "";
			for (int j=0; j<n-1; j++) {
				ngram += wordslist.get(i+j) + " ";
			}
			ngram += wordslist.get(i+n-1);
			
			Gram gram = new Gram(ngram);
			
			table.computeIfPresent(ngram, (k, v) -> {v.occurrences++; return v;});
			table.putIfAbsent(ngram, gram);
		}
		
		if (print) {
			t = System.currentTimeMillis() - t;
			System.out.println("\t|-> Time spent: " + t + "ms");
			System.out.println("\t|-> Number of unique n-grams: " + table.size());
		}
		
		calculateProbabilities();
		sort();
	}
	
	public void computeNGramsFromStringArray() {
		if (print) {
			System.out.println("Creating " + n + "-grams...");
			t = System.currentTimeMillis();
		}

		for (int i=0; i<wordsarr.length-n; i++) {
			String ngram = "";
			for (int j=0; j<n-1; j++) {
				ngram += wordsarr[i+j] + " ";
			}
			ngram += wordsarr[i+n-1];
			
			Gram gram = new Gram(ngram);
			
			table.computeIfPresent(ngram, (k, v) -> {v.occurrences++; return v;});
			table.putIfAbsent(ngram, gram);
		}
		
		if (print) {
			t = System.currentTimeMillis() - t;
			System.out.println("\t|-> Time spent: " + t + "ms");
			System.out.println("\t|-> Number of unique n-grams: " + table.size());
		}
		
		calculateProbabilities();
		sort();
	}

	private void calculateProbabilities() {
		if (print) {
			System.out.print("\t|-> Calculating probabilities... ");
			t = System.currentTimeMillis();
		}
		
		// loop through table entries and set probability
		// and add them to list (this could be done better?)
		table.forEach((key, value) -> {
			value.probability = (value.occurrences / numberOfGrams) * 100.0;
			list.add(value);
		});
	
		if (print) {
			t = System.currentTimeMillis() - t;
			System.out.println(" done in: " + t + "ms");	
		}
	}
	
	private void sort() {
		if (print) {
			System.out.print("\t|-> Sorting by occurrences... ");
			t = System.currentTimeMillis();
		}
		
		// sort list based on occurrences (or probabilities)
		list.sort((g1, g2) -> {
			return Double.compare(g2.occurrences, g1.occurrences);
		});
		
		if (print) {
			t = System.currentTimeMillis() - t;
			System.out.println(" done in: " + t + "ms");	
		}
	}
	
	public void printCommon(int n) {
		
		System.out.println("\nTop " + n + " most frequent " + this.n + "-grams:");
		System.out.println("\t|-> [n-gram, occurrences, probability]");
		for (int i=0; i<n; i++) {
			System.out.println("\t|-> " + list.get(i).toString());
		}
		
		System.out.println("\nTop " + n + " least frequent " + this.n + "-grams:");
		System.out.println("\t|-> [n-gram, occurrences, probability]");
		for (int i=list.size()-1; i>list.size()-n; i--) {
			System.out.println("\t|-> " + list.get(i).toString());
		}
	}
}

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
	
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#.#######");
		return "['" + ngram + "', " + (int)occurrences + ", " + f.format(probability) + "%]";
	}
}
