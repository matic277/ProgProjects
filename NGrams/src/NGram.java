import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class NGram {
	
	int n;
	String[] words;
	
	final int numberOfGrams;
	
	HashMap<String, Gram> table;
	ArrayList<Gram> list;
	
	public NGram(int n, String[] words) {
		this.words = words;
		this.n = n;
		this.table = new HashMap<String, Gram>(words.length);
		this.list = new ArrayList<Gram>(words.length);
		this.numberOfGrams = words.length - n;
		
		computeNGrams();
	}
	
	public void computeNGrams() {
		for (int i=0; i<words.length-n; i++) {
			String ngram = "";
			for (int j=0; j<n-1; j++) {
				ngram += words[i+j] + " ";
			}
			ngram += words[i+n-1];
			
			Gram gram = new Gram(ngram);
			
			if (table.containsKey(ngram)) {
				table.get(ngram).occurences++;
			} else {
				table.put(ngram, gram);
			}
		}
		
		// loop through table entries and set probability
		// and add them to list (this could be done better)
		table.forEach((key, value) -> {
			value.probability = (double)value.occurences / numberOfGrams;
			list.add(value);
		});
		
		// sort list based on occurences (or probabilities)
		list.sort((g1, g2) -> {
			return Double.compare(g2.occurences, g1.occurences);
		});
		
		System.out.println("\nTop 20 most frequent " + n + "-grams:");
		for (int i=0; i<20; i++) {
			System.out.println(list.get(i).toString());
		}
		
		System.out.println("\nTop 20 least frequent " + n + "-grams:");
		for (int i=list.size()-20; i<list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
	}
}

class Gram {
	
	String[] words;
	String ngram;
	
	int occurences = 1;
	double probability = 0;
	
	public Gram(String[] words) {
		this.words = words;
	}
	public Gram(String ngram) {
		this.ngram = ngram;
	}
	
	@Override
	public String toString() {
		DecimalFormat f = new DecimalFormat("#.#####");
		return "['" + ngram + "', " + occurences + ", " + f.format(probability) + "%]";
	}
}
