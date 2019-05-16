package StreamConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class StreamHandler {
	
	public AtomicInteger queue_size;
	public ExecutorService executor;
	public BlockingQueue queue = new ArrayBlockingQueue(10000);
	public StreamListener listener;
	
	public ConcurrentLinkedDeque<String> tweets = new ConcurrentLinkedDeque<String>();
	
	public StreamHandler() {
		openAndListenStream();
	}
	
	public void openAndListenStream() {
		// Twitter4J setup
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey("")
        		.setOAuthConsumerSecret("")
        		.setOAuthAccessToken("")
        		.setOAuthAccessTokenSecret("")
        		.setTweetModeExtended(true);

        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        
        // thread pool
        executor = Executors.newWorkStealingPool(12);
        queue_size = new AtomicInteger(0);
        
        listener = new StreamListener(this);
        twitterStream.addListener(listener);
        
        // query filter
        FilterQuery tweetFilterQuery = new FilterQuery();
        tweetFilterQuery.track(new String[]{"work"}); // OR on keywords
        
        // note that not all tweets have location metadata set.
        twitterStream.filter(tweetFilterQuery);
	}

}
