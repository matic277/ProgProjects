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
		
		System.out.println("Ammount of words (tokenized by spaces and newlines): " + words.size());
		
		long ngramTime = System.currentTimeMillis();
		System.out.println("\nProcessing text for " + ngramSize + "-grams:");
		
		Worker[] workers = initWorkers(ngramSize, nthreads, words);
		runAndJoinThreads(workers);
		HashMap<String, Gram> mergedTables = collectAndJoinTables(workers);
		ArrayList<Gram> list = toListAndProbabilities(mergedTables);
		sortList(list);
		printStats(list);
		
		System.out.println("\t -> Processing ngrams took(with sorting by ngram frequency) " + ((System.currentTimeMillis() - ngramTime)/1000.0) + "s");
		
		System.out.println("\nTotal time spent: " + ((System.currentTimeMillis() - totalTime)/1000.0) + "s");
		
//		for (int i=0; i<workers.length; i++) workers[i].printngrams();
		
		
		// collectAndJoinTables();
//		System.out.print("Combining hash-tables...");
//		t1 = System.currentTimeMillis();
//		// collect all hash tables and combine them
//		HashMap<String, Gram>[] tables = new HashMap[nthreads];
//		for (int i=0; i<workers.length; i++) tables[i] = workers[i].ngram.table;
//		
//		HashMap<String, Gram> merged = tables[0];
//		for (int i=1; i<tables.length; i++) {
//			HashMap<String, Gram> selected = tables[i];
//			selected.entrySet().forEach(entry -> {
//				merged.compute(entry.getKey(), (k, v) -> (v == null)? entry.getValue() : entry.getValue().combine(v));
//			});
//		}
//		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		
		// toListAndProbabilities()
		// size is words.size()
//		System.out.print("Putting into list and calculating probabilities...");
//		t1 = System.currentTimeMillis();
//		ArrayList<Gram> combined = new ArrayList<Gram>(words.size());
//		
//		final int numberOfGrams = merged.size();
//		merged.forEach((k, v) -> {
//			v.probability = (v.occurrences / numberOfGrams) * 100;
//			combined.add(v);
//		});
//		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		
		// sortList() pass in combined
//		System.out.print("Sorting list by probabilities...");
//		t1 = System.currentTimeMillis();
//		combined.sort((g1, g2) -> {
//			return Double.compare(g1.occurrences, g2.occurrences);
//		});
//		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		
		// printstats();
//		System.out.println("\nCombined arraylist size: " + combined.size());
//		System.out.println("Top 20 most frequent: ");
//		for (int i=combined.size()-1, ind = 1; i>combined.size()-20; i--, ind++) {
//			if (i < 0) break;
//			System.out.println("\t ("+ind+") " + (ind>9? "":" ") + combined.get(i).toString());
//		}
		
		
//		System.out.println("\n -> Total time spent: " + ((System.currentTimeMillis() - totalTime)/1000.0) + "s");
	}
	
//	private static void updateWorkerInstructions(Worker[] workers, int ngramSize) {
//		// just update how big the ngrams are supposed to be
//		for (Worker w : workers) w.n = ngramSize;
//	}

	private static Worker[] initWorkers(int ngramSize, int nthreads, ArrayList<String> words) {
		System.out.print("\t -> Threads initing....");
		long t1 = System.currentTimeMillis();
		int chunk = words.size() / nthreads ;
		int lastchunk = 0;
		Worker[] workers = new Worker[nthreads];
		for (int i=0; i<workers.length-1; i++, lastchunk=i*chunk) {
			workers[i] = new Worker(
				i,
				words,
				i*chunk,
				i*chunk + chunk + ngramSize - 1,
				ngramSize
			);
		}
		workers[nthreads-1] = new Worker(
			nthreads-1,
			words,
			lastchunk,
			words.size(),
			ngramSize
		);
		System.out.println(" done in " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		return workers;
	}

	private static void runAndJoinThreads(Worker[] workers) {
		long t1 = System.currentTimeMillis();
		Thread[] threads = new Thread[workers.length];
		for (int i=0; i<threads.length; i++) {
			threads[i] = new Thread(workers[i]);
		}
		System.out.print("\t -> Threads started and running...");
		for (int i=0; i<threads.length; i++) threads[i].start();
		for (int i=0; i<threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
	}

	private static void printStats(ArrayList<Gram> list) {
		System.out.println("\t -> Top 30 most frequent (of total "+list.size()+" ngrams): ");
		for (int i=list.size()-1, ind = 1; i>list.size()-31; i--, ind++) {
			if (i < 0) break;
			System.out.println("\t\t ("+ind+") " + (ind>9? "":" ") + list.get(i).toString());
		}
	}
	
	private static void sortList(ArrayList<Gram> list) {
		System.out.print("\t -> Sorting list by probabilities...");
		long t1 = System.currentTimeMillis();
		list.sort((g1, g2) -> {
			return Double.compare(g1.occurrences, g2.occurrences);
		});
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
	}
	
	private static ArrayList<Gram> toListAndProbabilities(HashMap<String, Gram> mergedTables) {
		System.out.print("\t -> Creating a list and calculating probabilities...");
		long t1 = System.currentTimeMillis();
		final int numberOfGrams = mergedTables.size();
		ArrayList<Gram> combined = new ArrayList<Gram>(numberOfGrams);
		
		mergedTables.forEach((k, v) -> {
			v.probability = (v.occurrences / numberOfGrams) * 100;
			combined.add(v);
		});
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		return combined;
	}
	
	private static HashMap<String, Gram> collectAndJoinTables(Worker[] workers) {
		System.out.print("\t -> Combining hash-tables...");
		long t1 = System.currentTimeMillis();
		// collect all hash tables and combine them
		HashMap<String, Gram>[] tables = new HashMap[workers.length];
		for (int i=0; i<workers.length; i++) tables[i] = workers[i].ngram.table;
		
		HashMap<String, Gram> merged = tables[0];
		for (int i=1; i<tables.length; i++) {
			HashMap<String, Gram> selected = tables[i];
			selected.entrySet().forEach(entry -> {
				merged.compute(entry.getKey(), (k, v) -> (v == null)? entry.getValue() : entry.getValue().combine(v));
			});
		}
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		return merged;
	}

}
