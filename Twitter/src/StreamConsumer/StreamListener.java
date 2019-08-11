package StreamConsumer;

import java.lang.Character.UnicodeBlock;

import Tokenizer.Tweet;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class StreamListener implements StatusListener {
	
	StreamHandler ref;
	
	public StreamListener(StreamHandler ref) {
		this.ref = ref;
	}
	
	boolean containsEmoji(String s) {
	    return s.codePoints().anyMatch(cp -> UnicodeBlock.of(cp).equals(UnicodeBlock.EMOTICONS));
	}
	
	@Override
	public void onStatus(Status status) {
        // throw away non-english tweets
        if (!status.getLang().equals("en")) return;
        
        String tweet = "";
        if (status.isRetweet()) {
     	   tweet = status.getRetweetedStatus().getText();
        } else {
     	   tweet = status.getText();
        }

        // getting RAW json string, still, emoticons are questionmarks
        //String raw = DataObjectFactory.getRawJSON(status);
        //System.out.println(raw);

        // add tweet to list of tweets
        ref.tweets.add(new Tweet(tweet, status.getUser().getScreenName()));
        
        // take the tweet that has been in the list of the longest and process it
        // there are two queues, one of tweets and one of executors, make sure
        // the executors one is as close to empty as possible, so that there
        // is only one queue (stupid but whatever)
        // calling .pollFirst() method means the tweet gets removed from the 
        // list as well
        if (!ref.tweets.isEmpty() && ref.executor.getQueue().size() == 0)
        	ref.executor.submit(new Consumer(ref.tweets.pollFirst(), ref));
        
        //ref.queue_size.incrementAndGet();
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {}
	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {}
	@Override
	public void onScrubGeo(long arg0, long arg1) {}
	@Override
	public void onStallWarning(StallWarning arg0) {}
	@Override
	public void onException(Exception arg0) { System.out.println("Exception!" + arg0.getStackTrace()); }

}
