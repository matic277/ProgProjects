package multi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class Worker implements Runnable, Callable<HashMap<String, Gram>> {
	
	int n; // ngram size
	int start;
	int end;
	int id;
	
	ArrayList<String> words;
	
	NGram ngram;
	
	public Worker(int id, ArrayList<String> words, int start, int end, int n) {
		this.words = words;
		this.start = start;
		this.end = end;
		this.n = n;
		this.id = id;
		
		System.out.println("Thread no. " + id +": s=" + start + ", e=" + end);
	}

	@Override
	public void run() {
		// first, pre-process words
		preprocessText();
		
		ngram = new NGram(start, end, n, words);
	}
	
	private void preprocessText() {
		String[] replacementChars = {
			".", ",", ";", ":",	"'", "!",
			"?", "\r", "\"", "/", "*",
			"(", ")", "[", "]", "{", "}"
		};
		for (int i=start; i<end; i++) {
			words.set(i, words.get(i).toLowerCase());
			for (String s : replacementChars) words.set(i, words.get(i).replace(s, ""));
		}
	}
	
	public void printngrams() {
		System.out.println("Thread no." + id);
		ngram.printngrams();
	}

	@Override
	public HashMap<String, Gram> call() throws Exception {
		// TODO Auto-generated method stub
		return ngram.table;
	}
}
