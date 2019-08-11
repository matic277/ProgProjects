package datasetGetter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class GetTextByIDs {
	
	static String relativeFilePath_apiKeys = "apikeys/keys.txt";
	static String[] apiKeys = new String[4];
	
	static final Twitter twitter = new TwitterFactory().getInstance();
	
	static ArrayList<String> ids = new ArrayList<String>(6000);
	static ArrayList<String> tweets = new ArrayList<String>(6000);
	
	static String tweetsoutput = "combinedENGLISHONLY.txt";
	
	public static void main(String[] args) {
		
		new Thread(() -> {
			sleep(10*1000);
			while (true) {
				System.out.println(" -> processed: " + tweets.size() + "/"+ids.size());
				if (!tweets.isEmpty()) System.out.println(" -> example:"+ tweets.get(tweets.size()-1));
				sleep(60 * 3 * 1000);
			}
		}).start();
		
		readIds();
		configureStream();
		
		query();
		writeToFile();
	}
	
	public static void writeToFile() {
		System.out.print("\t|-> Logger writing file: '"+tweetsoutput+"'... ");
		
		File file = new File(tweetsoutput);
		BufferedWriter writer = null;
		
		// this is only appending text to the file
		// no need to worry about overwriting
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i=0; i<tweets.size(); i++) {
				writer.write(tweets.get(i));
				writer.newLine();
			}
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error at writing file!\n");
			e.printStackTrace();
		}

		System.out.println("File written.");
		System.out.println("\t|-> Lines written: " + tweets.size());
	}

	public static void readIds() {
		System.out.print("Reading IDs....");
		try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\V2\\Desktop\\incompleteDataset2.txt"))) {
			lines.forEachOrdered(line -> {
				String[] tokens = line.split(",");
				ids.add(tokens[0]+","+tokens[1]);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" done, size: " + ids.size());
	}
	
	static public void configureStream() {
		readKeysFromFile();
		// Twitter4J setup
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(apiKeys[0])
                .setOAuthConsumerSecret(apiKeys[1])
                .setOAuthAccessToken(apiKeys[2])
                .setOAuthAccessTokenSecret(apiKeys[3])
                .setTweetModeExtended(true)
        		.setJSONStoreEnabled(true);    
        
        twitter.setOAuthConsumer(apiKeys[0], apiKeys[1]);
        AccessToken accessToken = new AccessToken(apiKeys[2], apiKeys[3]);
        twitter.setOAuthAccessToken(accessToken);
	}
	
	public static void query() {
		for (int i=0; i<ids.size(); ) {
			int remaining = getRemaining();
			if (remaining < 1) {
				System.out.println("Limit reached, sleeping");
//				System.out.println("Current processed: " + tweets.size() + "/" + ids.size());
				sleep(5 * 62 * 1000); // sleep for 5 min
				continue;
			}
			
			try
			{	
				String[] tokens = ids.get(i).split(","); // 0=sentiment, 1=id
	            Status status = twitter.showStatus(Long.parseLong(tokens[1]));
	            if (status == null) {
	            	System.out.println(" -> ("+i+") status is null, not adding, remaining: + " + remaining);
	            } else {
	            	String tweet = "";
	                if (status.isRetweet()) {
	                	tweet = status.getRetweetedStatus().getText();
	                } else {
	                	 tweet = status.getText();
	                }
	                if (status.getLang().equals("en")) {
	                	System.out.println(" -> ("+i+") english tweet, adding, remaining: " + remaining);
	                	tweets.add(tokens[0] + "," + tokens[1] + "," + tweet);
	                } else {
	                	System.out.println(" -> ("+i+") non-english tweet, not adding, remaining: " + remaining);
	                }
	            }
			}
			catch (TwitterException e) {
	        	System.out.println(" -> ("+i+") something went wrong for id: "+ ids.get(i));
	        	//e.printStackTrace();
	        }
			i++;
			sleep(333); // sleep a little
		}
	}
	
	// returns true if user is out of queries
	// returns false if there is more than 1 query left
	public static int getRemaining() {
		String key = "/statuses/show/:id";
		Map<String, RateLimitStatus> map = null;
		RateLimitStatus rate = null;
		try {
			map = twitter.getRateLimitStatus();
			rate = map.get(key);
			return rate.getRemaining();
		} catch (TwitterException e) {
			System.out.print(" -> Couldnt get map or limit has been reached, returning 0");
			if (map == null) System.out.print(" map was null ");
			else System.out.print(" map wasnt null ");
			if (rate == null) System.out.println("rate was null");
			else System.out.println("rate wasnt null");
			return 0;
		}
	}
	
	public static void sleep(int t) {
		try { Thread.sleep(t); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

	private static int index = 0;
	public static void readKeysFromFile() {
		try (Stream<String> lines = Files.lines(Paths.get(relativeFilePath_apiKeys), Charset.defaultCharset())) {
			//System.out.println(lines.count());
			lines.forEachOrdered(
				line -> {
					apiKeys[index] = line;
					index++;
				}
			);
		} catch (IOException e) {
			System.out.println("Error reading api keys!");
			e.printStackTrace();
		}
	}
}
