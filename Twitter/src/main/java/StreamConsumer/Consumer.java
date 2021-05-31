package StreamConsumer;

import Tokenizer.Tweet;

public class Consumer implements Runnable {
    
    //BlockingQueue queue;
    private Tweet tweet;
    
    private StreamHandler ref;
    
    public Consumer(Tweet tweet, StreamHandler ref) {
        this.tweet = tweet;
        this.ref = ref;
    }

    public void run() {
        //Stream.findSentiment(tweet);
        //ref.queue_size.decrementAndGet();
        
        tweet.processTweet();
        ref.processedTweets.add(tweet);
        
        //sleep(1000);
    }
    
    // debugging purpose
    private void sleep(int t) {
        try { Thread.sleep((long)t); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
