package StreamConsumer;

import Tokenizer.Tokenizer;

public class Consumer implements Runnable {
	
	//BlockingQueue queue;
	Tweet tweet;
	
	StreamHandler ref;
	
	public Consumer(Tweet tweet, StreamHandler ref) {
		this.tweet = tweet;
		this.ref = ref;
	}

	public void run() {
		//Stream.findSentiment(tweet);
		//ref.queue_size.decrementAndGet();
		
		//String shortenedTweet = tweet.substring(0, 25);
		//System.out.println("Processing tweet -> " + shortenedTweet);
		
		Tokenizer t = new Tokenizer(tweet);
		ref.processedTweets.add(t.processTweet());
			
		//sleep(1000);
	}
	
	// debugging purpose
	private void sleep(int t) {
		try { Thread.sleep((long)t); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}
}
