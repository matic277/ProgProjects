package StreamConsumer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class StreamListener implements StatusListener {
	
	StreamHandler ref;
	
	public StreamListener(StreamHandler ref) {
		this.ref = ref;
	}
	
	@Override
	public void onStatus(Status status) {
		ref.executor.execute(new Consumer(status.getText(), ref));
        ref.queue_size.incrementAndGet();
        
        // throw away non-english tweets
        if (!status.getLang().equals("en")) return;
        	
        String tweet = "";
        if (status.isRetweet()) {
     	   tweet = "(re-tweet): " + status.getUser().getScreenName() + "\n" + status.getRetweetedStatus().getText();
        } else {
     	   tweet = status.getText();
        }
        
        //System.out.println("STATUS: " + tweet);
        //System.out.println("LANGUAGE: " + status.getLang()+"\n");
        
        ref.tweets.add(tweet);
	}

	public void onTrackLimitationNotice(int arg0) {}
	public void onDeletionNotice(StatusDeletionNotice arg0) {}
	public void onScrubGeo(long arg0, long arg1) {}
	public void onStallWarning(StallWarning arg0) {}
	public void onException(Exception arg0) { System.out.println("Exception!" + arg0.getMessage()); }

}
