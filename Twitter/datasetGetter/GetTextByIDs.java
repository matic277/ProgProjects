import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import StreamConsumer.StreamListener;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class GetTextByIDs {
	
	static String relativeFilePath_apiKeys = "apikeys/keys.txt";
	static String[] apiKeys = new String[4];
	
	static final Twitter twitter = new TwitterFactory().getInstance();
	
	static ArrayList<String> ids = new ArrayList<String>(6000);
	static ArrayList<String> combined = new ArrayList<String>(6000);
	static ArrayList<String> tweets = new ArrayList<String>(6000);
	
	static String tweetsoutput2 = "combined.txt";
	
	public static void main(String[] args) {
		readIds();
		configureStream();
		
		query();
		writeToFile();
	}
	
	public static void writeToFile() {
		System.out.print("\t|-> Logger writing file: '"+tweetsoutput2+"'... ");
		
		File file = new File(tweetsoutput2);
		BufferedWriter writer = null;
		
		// this is only appending text to the file
		// no need to worry about overwriting
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i=0; i<combined.size(); i++) {
				writer.write(combined.get(i));
				writer.newLine();
			}
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error at writing file!\n");
			e.printStackTrace();
		}

		System.out.println("File written.");
	}

	public static void readIds() {
		System.out.print("Reading IDs....");
		try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\V2\\Desktop\\tweets.txt"), Charset.defaultCharset())) {
			lines.forEachOrdered(line -> {
				String[] tokens = line.split(",");
				ids.add(tokens[1]);
				combined.add(line);
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
			if (getLimit() < 1) {
				System.out.println("Limit reached, sleeping");
				System.out.println("Current processed: " + tweets.size() + "/" + ids.size());
				sleep(5 * 60 * 1000); // sleep for 5 min
				continue;
			}
			
			try {
	            Status status = twitter.showStatus(Long.parseLong(ids.get(i)));
	            if (status == null) { // 
	            	tweets.add(ids.get(i) + "," + "null");
	                combined.add(combined.get(i) + "," + "null");
	            } else {
	            	String tweet = "";
	                if (status.isRetweet()) {
	              	   tweet = status.getRetweetedStatus().getText();
	                 } else {
	              	   tweet = status.getText();
	                 }
	                tweets.add(ids.get(i) + "," + tweet);
	                combined.add(combined.get(i) + "," + tweet);
	            }
	        } catch (Exception e) {
	        	System.out.println(i+"/"+ids.size()+", Something went wrong for id: "+ ids.get(i));
	        	tweets.add(ids.get(i) + "," + "error");
                combined.add(combined.get(i) + "," + "error");
	        }
			i++;
			sleep(333); // sleep alittle
		}
	}
	
	// returns true if user is out of queries
	// returns false if there is more than 1 query left
	public static int getLimit() {
		String key = "/statuses/show/:id";
		Map<String, RateLimitStatus> map;
		try {
			map = twitter.getRateLimitStatus();
			RateLimitStatus rate = map.get(key);
			return rate.getRemaining();
		} catch (TwitterException e) {
			System.out.println("Couldnt get map or limit has been reach, returning 0");
			return 0;
			
		}
	}
	
	public static void sleep(int t) {
		try {
			Thread.sleep((long)t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
