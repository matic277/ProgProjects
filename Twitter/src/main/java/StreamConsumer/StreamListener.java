package StreamConsumer;

import java.lang.Character.UnicodeBlock;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    
    HashSet<byte[]> seen = new HashSet<>(10000);
    MessageDigest digest;
    {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    static final SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
//    static final DateTimeFormatter form2 = new SimpleDateFormat("dd-MM-yyyy");
    
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
        
        if (seen.contains(digest.digest(tweet.getBytes(StandardCharsets.UTF_8)))) return;
        // For some reason, some tweets don't even contain
        // any of the query words. This doesn't happen often.
        // Check manually.
        boolean contains = false;
        for (String s : ref.queries) {
            if (tweet.contains(s)) {
                contains = true;
                break;
            }
        }
        if (!contains) return;
        
//		System.out.println(form.format(status.getCreatedAt()));
        // getting RAW json string, still, emoticons are questionmarks
        //String raw = DataObjectFactory.getRawJSON(status);
        //System.out.println(raw);
        // add tweet to list of tweets
//        LocalDate d = LocalDate.of(2021, 5, 11);
        ref.tweets.add(new Tweet(tweet, status.getUser().getScreenName(), form.format(status.getCreatedAt())));
        seen.add(digest.digest(tweet.getBytes(StandardCharsets.UTF_8)));
        
        
        // take the tweet that has been in the list the longest and process it
        // there are two queues, one of tweets and one of executors, make sure
        // the executors one is as close to empty as possible, so that there
        // is only one queue (stupid but whatever)
        // calling .pollFirst() method means the tweet gets removed from the 
        // list as well
        //if (!ref.tweets.isEmpty() && ref.executor.getQueue().size() == 0)
            ref.executor.submit(new Consumer(ref.tweets.pollFirst(), ref));
        
        //ref.queue_size.incrementAndGet();
    }
    
    boolean containsEmoji(String s) {
        return s.codePoints().anyMatch(cp -> UnicodeBlock.of(cp).equals(UnicodeBlock.EMOTICONS));
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
    public void onException(Exception arg0) { System.out.println("Exception!" + Arrays.toString(arg0.getStackTrace())); }

}
