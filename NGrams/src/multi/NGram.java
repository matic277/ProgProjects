package multi;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class NGram {
	
	int n; // ngram size
	int start, end;
	
	ArrayList<String> wordslist;
	
	final int numberOfGrams;
	
	HashMap<String, Gram> table;
	ArrayList<Gram> list;
	
	public NGram(int start, int end, int n, ArrayList<String> words) {
		this.wordslist = words;
		this.n = n;
		this.start = start;
		this.end = end;
		this.table = new HashMap<String, Gram>(end - start);
		this.list = new ArrayList<Gram>(end - start);
		this.numberOfGrams = (end - start) - n;
		
		computeNGramsFromArrayList();
	}
	
	public void computeNGramsFromArrayList() {
		for (int i=start; i<end-n; i++) {
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
		orderAndPrintProbabilities();
	}

	private void calculateProbabilities() {
		// loop through table entries and set probability
		// and add them to list (this could be done better?)
		table.forEach((key, value) -> {
			value.probability = (value.occurrences / numberOfGrams) * 100.0;
			list.add(value);
		});
	}
	
	private void orderAndPrintProbabilities() {
		// sort list based on occurrences (or probabilities)
		list.sort((g1, g2) -> {
			return Double.compare(g2.occurrences, g1.occurrences);
		});
		
//		System.out.println("\nTop 20 most frequent " + n + "-grams:");
//		for (int i=0; i<20; i++) {
//			if (i >= list.size()) break;
//			System.out.println(list.get(i).toString());
//		}
//		
//		System.out.println("\nTop 20 least frequent " + n + "-grams:");
//		for (int i=list.size()-1; i>list.size()-20; i--) {
//			if (i < 0) break;
//			System.out.println(list.get(i).toString());
//		}
	}
	
	public void printngrams() {
		for (int i=0; i<list.size(); i++) {
			System.out.println("\t" + list.get(i).toString());
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
