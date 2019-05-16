import java.util.concurrent.BlockingQueue;


public class Consumer implements Runnable {
	BlockingQueue queue;
	String tweet;
	

	public Consumer(String tweet) {
		this.tweet = tweet;
	}

	public void run() {
		//Stream.findSentiment(tweet);
		Stream.queue_size.decrementAndGet();
	}


}
