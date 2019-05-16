package StreamConsumer;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
	
	BlockingQueue queue;
	String tweet;
	
	StreamHandler ref;
	
	public Consumer(String tweet, StreamHandler ref) {
		this.tweet = tweet;
		this.ref = ref;
	}

	public void run() {
		//Stream.findSentiment(tweet);
		ref.queue_size.decrementAndGet();
	}
}
