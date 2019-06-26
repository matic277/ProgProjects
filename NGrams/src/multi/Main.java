package multi;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		int ngramSize = 3;
		int nthreads = 4;
		
		
		long t1 = System.currentTimeMillis();
		Text tx = new Text();
		System.out.println("Reading file: " + (System.currentTimeMillis() - t1)/1000 + "s");
		
//		NGram ng = new NGram(ngramSize, tx.words);
//		System.out.println("\nTime spent processing "+ngramSize+"-grams: " + ((System.currentTimeMillis() - t1)/1000.0) + " s");
		
//		ArrayList<String> words = tx.getWords();
		ArrayList<String> words = new ArrayList<String>(10);
		
		String s = "Today it was sunny and nice outside.";
		String[] t = s.split(" ");
		for (String st : t) words.add(st);
		
		int chunk = words.size() / nthreads ;
		int lastchunk = 0;
		
		System.out.println("Threads initing...");
		t1 = System.currentTimeMillis();
		
		Thread[] threads = new Thread[nthreads];
		Worker[] workers = new Worker[nthreads];
		for (int i=0; i<threads.length-1; i++, lastchunk=i*chunk) {
			workers[i] = new Worker(
				i,
				words,
				i*chunk,
				i*chunk + chunk + ngramSize - 1,
				ngramSize
			);
			threads[i] = new Thread(workers[i]);
		}
		workers[nthreads-1] = new Worker(
			nthreads-1,
			words,
			lastchunk,
			words.size(),
			ngramSize
		);
		threads[nthreads-1] = new Thread(workers[nthreads-1]);
		
		System.out.println("Threads starting...");
		for (int i=0; i<threads.length; i++) threads[i].start();
		for (int i=0; i<threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Threads done in: " + ((System.currentTimeMillis() - t1)/1000) + "s");
		System.out.println();
		
		for (int i=0; i<workers.length; i++) workers[i].printngrams();
		
	}

}
