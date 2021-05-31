package StreamConsumer;


// !!!
// CLASS NOT USED
// !!!

public class Stream {
	
//	public static AtomicInteger queue_size;
//	public static ExecutorService executor;
//	
//	public static BlockingQueue queue = new ArrayBlockingQueue(10000);
//	
//	//public static StanfordCoreNLP pipeline;
//	
//	public static void main(String[] args) {
//		
//		//pipeline = new StanfordCoreNLP("MyPropFile.properties");
//		
//		int numThreads = 4;
//		queue_size = new AtomicInteger(0);
//			
//		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
//        configurationBuilder.setOAuthConsumerKey("")
//                .setOAuthConsumerSecret("")
//                .setOAuthAccessToken("")
//                .setOAuthAccessTokenSecret("")
//                .setTweetModeExtended(true);
//        
//        executor = Executors.newWorkStealingPool(12);
//        
//        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
//        
//        twitterStream.addListener(new StatusListener () {
//        	
//            public void onStatus(Status status) {
//               executor.execute(new Consumer(status.getText()));
//               queue_size.incrementAndGet();
//
//               String tweet = "";
//               if (status.isRetweet()) {
//            	   tweet = "(re-tweet): " + status.getUser().getScreenName() + "\n" + status.getRetweetedStatus().getText();
//               } else {
//            	   tweet = status.getText();
//               }
//               System.out.println("STATUS: " + tweet);
//               System.out.println("LANGUAGE: " + status.getLang()+"\n");
//               
//            }
//
//			@Override
//			public void onException(Exception arg0) {}
//			public void onDeletionNotice(StatusDeletionNotice arg0) {}
//			public void onScrubGeo(long arg0, long arg1) {}
//			public void onStallWarning(StallWarning arg0) {}
//			public void onTrackLimitationNotice(int arg0) {}
//        });
//        
//        FilterQuery tweetFilterQuery = new FilterQuery(); // See 
//        
//        tweetFilterQuery.track(new String[]{"#work"}); // OR on keywords
//        
//        //Note that not all tweets have location metadata set.
//        twitterStream.filter(tweetFilterQuery);
//        
//        //Application.launch(Stats.class, args);
//
//	}
	
	
//	public static int findSentiment(String tweet) {
//
//		int mainSentiment = 0;
//		if (tweet != null && tweet.length() > 0) {
//			int longest = 0;
//			Annotation annotation = pipeline.process(tweet);
//			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
//				Tree tree = sentence.get(SentimentAnnotatedTree.class);
//				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
//				String partText = sentence.toString();
//				if (partText.length() > longest) {
//					mainSentiment = sentiment;
//					longest = partText.length();
//				}
//
//			}
//		}
//		System.out.println(mainSentiment);
//		return mainSentiment;
//	}
	
}
