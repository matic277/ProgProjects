package multi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;

import common.FileReader;

public class Main {
	
	public static void main(String[] args) {
		long totalTime = System.currentTimeMillis();
		
		int ngramSize = 3;
		int nthreads = 4;
		
		// https://sherlock-holm.es/stories/html/advs.html#Chapter-1
		String holeslong = "resources/holmes_5mb.txt";
		String holmesshort = "resources/holmes.txt";
		
		FileReader fr = new FileReader(holeslong);
		ArrayList<String> words = fr.getWords();
		
		System.out.println("Ammount of words: " + words.size());
		
		long ngramTime = System.currentTimeMillis();
		System.out.println("Processing text for " + ngramSize + "-grams:");

		// ---
		Worker[] workers = initWorkers(ngramSize, nthreads, words);
		runAndJoinThreads(workers);
		HashMap<String, Gram> mergedTables = collectAndJoinTables(workers);
		ArrayList<Gram> list = toListAndProbabilities(mergedTables);
		sortList(list);
		printStats(list);
		
		
		System.out.println("\t|-> Processing ngrams took(with sorting by ngram frequency) " 
				+ ((System.currentTimeMillis() - ngramTime)/1000.0) + "s");
		
		System.out.println("\nTotal time spent: " 
				+ ((System.currentTimeMillis() - totalTime)/1000.0) + "s");	
		
		double sum = 0;
		for (Gram g : list) sum += g.probability;
		System.out.println("\nSum of probabilities: " + sum);
	}

	private static Worker[] initWorkers(int ngramSize, int nthreads, ArrayList<String> words) {
		System.out.print("\t|-> Threads initing....");
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
		System.out.print("\t|-> Threads started and running...");
		for (int i=0; i<threads.length; i++) threads[i].start();
		for (int i=0; i<threads.length; i++) 
			try { threads[i].join(); } catch (InterruptedException e) { e.printStackTrace(); }
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
	}

	private static void printStats(ArrayList<Gram> list) {
		System.out.println("\t|-> Top 30 most frequent (of total "+list.size()+" ngrams): ");
		for (int i=list.size()-1, ind = 1; i>list.size()-31; i--, ind++) {
			if (i < 0) break;
			System.out.println("\t|\t|-> ("+ind+") " + (ind>9? "":" ") + list.get(i).toString());
		}
	}
	
	private static void sortList(ArrayList<Gram> list) {
		System.out.print("\t|-> Sorting list by probabilities...");
		long t1 = System.currentTimeMillis();
		list.sort((g1, g2) -> {
			return Double.compare(g1.occurrences, g2.occurrences);
		});
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
	}
	
	private static ArrayList<Gram> toListAndProbabilities(HashMap<String, Gram> mergedTables) {
		System.out.print("\t|-> Creating a list and calculating probabilities...");
		long t1 = System.currentTimeMillis();
		int numberOfGrams = 0;
		ArrayList<Gram> combined = new ArrayList<Gram>(numberOfGrams);
		
		mergedTables.forEach((k, v) -> {
			combined.add(v);
		});
		for (Gram g : combined) numberOfGrams += g.occurrences;
		final int num = numberOfGrams;
		
		combined.forEach(g -> {
			g.probability = ((double)g.occurrences / num) * 100.0;
		});
		
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		return combined;
	}
	
	private static HashMap<String, Gram> collectAndJoinTables(Worker[] workers) {
		System.out.print("\t|-> Combining hash-tables...");
		long t1 = System.currentTimeMillis();
		
		HashMap<String, Gram>[] tables = new HashMap[workers.length];
		for (int i=0; i<workers.length; i++) tables[i] = workers[i].ngram.table;

		// combine all tables into table[0]
		// if two grams need to be combined
		// sum up their *occurrences* property
		for (int i=1; i<tables.length; i++) {
			tables[i].forEach((k, v) -> {
				tables[0].merge(k, v, (g1, g2) -> { g1.occurrences += g2.occurrences; return g1; });
			});
			// release some memory by setting all other hashmaps to null?
			tables[i] = null;
		}
		System.out.println(" done in: " + ((System.currentTimeMillis() - t1)/1000.0) + "s");
		return tables[0];
	}

}
