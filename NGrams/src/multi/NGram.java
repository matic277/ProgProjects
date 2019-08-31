package multi;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class NGram {
	
	// MULTI THREAED
	
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
		for (int i=start; i<end-n+1; i++) {
			String ngram = "";
			for (int j=0; j<n-1; j++) {
				ngram += wordslist.get(i+j) + " ";
			}
			ngram += wordslist.get(i+n-1);
			
			Gram gram = new Gram(ngram);

			table.computeIfPresent(ngram, (k, v) -> {v.occurrences++; return v;});
			table.putIfAbsent(ngram, gram);
		}
	}
	
	public void printngrams() {
		for (int i=0; i<list.size(); i++) {
			System.out.println("\t" + list.get(i).toString());
		}
	}
}
