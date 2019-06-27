package multi;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	
	public static void main(String[] args) {
		long totalTime = System.currentTimeMillis();
		
		int ngramSize = 3;
		int nthreads = 4;
		
		long t1 = System.currentTimeMillis();
		Text tx = new Text();
		System.out.println("Reading file: " + (System.currentTimeMillis() - t1)/1000.0 + "s");
		
//		NGram ng = new NGram(ngramSize, tx.words);
//		System.out.println("\nTime spent processing "+ngramSize+"-grams: " + ((System.currentTimeMillis() - t1)/1000.0) + " s");
		
		ArrayList<String> words = tx.getWords();
//		ArrayList<String> words = new ArrayList<String>(10);
		
//		String s = "Today it was sunny and nice outside. This is more text. Sample text.";
//		s += " Yesterday was sunny and nice outside too. More sample text yesterday. More sample text. even more sample text.";
//		String[] t = s.split(" ");
//		for (String st : t) words.add(st);
		
		System.out.println("Ammount of words: " + words.size());
		
		
		int chunk = words.size() / nthreads ;
		int lastchunk = 0;
		
		System.out.println("Chunk size: " + chunk);
		System.out.println("Threads initing...");
		t1 = System.currentTimeMillis();
		
		Thread[] threads = new Thread[nthreads];
		Worker[] workers = new Worker[nthreads];
		for (int i=0; i<threads.length-1; i++, lastchunk=i*chunk) {
			Worker w = new Worker(
				i,
				words,
				i*chunk,
				i*chunk + chunk + ngramSize - 1,
				ngramSize
			);
			threads[i] = new Thread(w);
			workers[i] = w;
		}
		Worker w = new Worker(
			nthreads-1,
			words,
			lastchunk,
			words.size(),
			ngramSize
		);
		threads[nthreads-1] = new Thread(w);
		workers[nthreads-1] = w;
		
		// -----
		
		System.out.println("Threads starting...");
		for (int i=0; i<threads.length; i++) threads[i].start();
		for (int i=0; i<threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		threads[0].start();
		
		System.out.println("Threads done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		System.out.println();
		
//		for (int i=0; i<workers.length; i++) workers[i].printngrams();
		
		
		System.out.print("Combining hash-tables...");
		t1 = System.currentTimeMillis();
		// collect all hash tables and combine them
		HashMap<String, Gram>[] tables = new HashMap[nthreads];
		for (int i=0; i<workers.length; i++) tables[i] = workers[i].ngram.table;
		
		ArrayList<Gram> combined = new ArrayList<Gram>(words.size());
		
		HashMap<String, Gram> merged = tables[0];
		for (int i=1; i<tables.length; i++) {
			HashMap<String, Gram> selected = tables[i];
			selected.entrySet().forEach(entry -> {
				merged.compute(entry.getKey(), (k, v) -> (v == null)? entry.getValue() : entry.getValue().combine(v));
			});
		}
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		
		
		System.out.print("Putting into list and calculating probabilities...");
		t1 = System.currentTimeMillis();
		
		final int numberOfGrams = merged.size();
		merged.forEach((k, v) -> {
			v.probability = (v.occurrences / numberOfGrams) * 100;
			combined.add(v);
		});
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		
		
		System.out.print("Sorting list by probabilities...");
		t1 = System.currentTimeMillis();
		combined.sort((g1, g2) -> {
			return Double.compare(g1.occurrences, g2.occurrences);
		});
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		
		
		System.out.println("\nCombined arraylist size: " + combined.size());
		System.out.println("Top 20 most frequent: ");
		for (int i=combined.size()-1, ind = 1; i>combined.size()-20; i--, ind++) {
			if (i < 0) break;
			System.out.println("\t ("+ind+") " + (ind>9? "":" ") + combined.get(i).toString());
		}
		
		
		System.out.println("\n -> Total time spent: " + ((System.currentTimeMillis() - totalTime)/1000.0) + "s");
	}
	
	public static HashMap<String, NGram> collectAndJoinTables() {
		
		return null;
	}

}
