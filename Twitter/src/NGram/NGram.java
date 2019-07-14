package NGram;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class NGram {
	
	int n;
	
	String[] wordsarr;
	ArrayList<String> wordslist;
	
	final int numberOfGrams;
	
	HashMap<String, Gram> table;
	ArrayList<Gram> list;
	
	public NGram(int n, String[] words) {
		this.wordsarr = words;
		this.n = n;
		this.table = new HashMap<String, Gram>(words.length);
		this.list = new ArrayList<Gram>(words.length);
		this.numberOfGrams = words.length - n;
		
		computeNGramsFromStringArray();
	}
	public NGram(int n, ArrayList<String> words) {
		this.wordslist = words;
		this.n = n;
		this.table = new HashMap<String, Gram>(words.size());
		this.list = new ArrayList<Gram>(words.size());
		this.numberOfGrams = words.size() - n;
		
		computeNGramsFromArrayList();
	}
	
	public void computeNGramsFromArrayList() {
		for (int i=0; i<wordslist.size()-n; i++) {
			String ngram = "";
			for (int j=0; j<n-1; j++) {
				ngram += wordslist.get(i+j) + " ";
			}
			ngram += wordslist.get(i+n-1);
			
			Gram gram = new Gram(ngram);
			
			if (table.containsKey(ngram)) {
				table.get(ngram).occurrences++;
			} else {
				table.put(ngram, gram);
			}
		}
		calculateProbabilities();
	}
	
	public void computeNGramsFromStringArray() {
		for (int i=0; i<wordsarr.length-n; i++) {
			String ngram = "";
			for (int j=0; j<n-1; j++) {
				ngram += wordsarr[i+j] + " ";
			}
			ngram += wordsarr[i+n-1];
			
			Gram gram = new Gram(ngram);
			
			if (table.containsKey(ngram)) {
				table.get(ngram).occurrences++;
			} else {
				table.put(ngram, gram);
			}
		}
		calculateProbabilities();
	}

	private void calculateProbabilities() {
		// loop through table entries and set probability
		// and add them to list (this could be done better?)
		table.forEach((key, value) -> {
			value.probability = (value.occurrences / numberOfGrams) * 100.0;
			list.add(value);
		});
	}
	
	public void orderAndPrintProbabilities() {
		// sort list based on occurrences (or probabilities)
		list.sort((g1, g2) -> {
			return Double.compare(g2.occurrences, g1.occurrences);
		});
		
		int printnum = 10;
		
		System.out.println("Size of list: " + list.size());
		System.out.println("Size of table: " + table.size());
		
		System.out.println("\nTop "+printnum+" most frequent " + n + "-grams:");
		for (int i=0; i<printnum && i<list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
		
		System.out.println("\nTop "+printnum+" least frequent " + n + "-grams:");
		for (int i=list.size()-1; i>=0 && i>=list.size()-printnum; i--) {
			System.out.println(list.get(i).toString());
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
