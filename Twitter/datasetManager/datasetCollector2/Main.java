package datasetCollector2;

import StreamConsumer.StreamHandler;

/*
 * Constructs dataset using twitter API. TWeets containing
 * :) are classified as positive, :( as negative.
 * Saved to a txt file as: sentiment,tweet.
 * Can be run at any time, as file-writer class only appends
 * to the end of file, to expand the dataset.
 * No tweets classified as neutral can be output.
 */
public class Main {
	
	public static void main(String[] args) {
		StreamHandler stream = new StreamHandler();
		
		// status
		new Thread() {
			@Override
			public void run() {
				while (true){
					System.out.println("Size of list:  " + Listener.list.size());
					
					try { Thread.sleep(10*1000); }
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}.run();
	}

}
