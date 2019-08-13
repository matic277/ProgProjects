package datasetCollector2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import StreamConsumer.Consumer;
import Tokenizer.Tokenizer;
import Tokenizer.Tweet;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class Listener implements StatusListener {
	
	static ArrayList<String> list = new ArrayList<String>(1000);
	String relativePath_OutputFile = "output/tweets.txt";
	
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
        
        int sentiment = 0;
        if (tweet.contains(":)") && tweet.contains(":(")) return;
        if (tweet.contains(":)")) sentiment = 1;
        else if (tweet.contains(":(")) sentiment = -1;
        else return;

        Tweet t = new Tweet(tweet, null);
        t.processTweet();
        tweet = t.getCleanSource();
        
        list.add(sentiment + "," + tweet);
        
        if (list.size() > 1000) {
        	saveResults();
        	list.clear();
        }
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		System.out.println("Track limitation notice: " + arg0);
		try {
			Thread.sleep(60*5*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveResults() {
		System.out.print("-> Logger writing file: '"+relativePath_OutputFile+"'... ");
		
		File file = new File(relativePath_OutputFile);
		BufferedWriter writer = null;
		
		// this is only appending text to the file
		// no need to worry about overwriting
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i=0; i<list.size(); i++) {
				writer.write(list.get(i));
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
	
	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {}
	@Override
	public void onScrubGeo(long arg0, long arg1) {}
	@Override
	public void onStallWarning(StallWarning arg0) {}
	@Override
	public void onException(Exception arg0) { System.out.println("Exception!" + arg0.getStackTrace()); }


}
