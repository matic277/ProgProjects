package classifierTester;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.stream.Stream;

import Tokenizer.Tweet;

public class CreateDataset {
	
	static ArrayList<TweetInstance2> tweets = new ArrayList<TweetInstance2>(3000);
	static ArrayList<String> ptweets = new ArrayList<String>(3000);
	
	public static void main(String[] args) {
		readDataset();
		processDataset();
		saveResults();
	}
	
	public static void saveResults() {
		System.out.print("Writing file... ");
		
		File file = new File("output/output.txt");
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i=0; i<ptweets.size(); i++) {
				writer.write(ptweets.get(i));
				if (i == 0) {
					System.out.println(ptweets.get(i));
				}
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
	
	public static void processDataset() {
		tweets.forEach(t -> {
			Tweet pt = new Tweet(t.text, null);
			pt.processTweet();
			String s = "";
//			s += pt.getStatistics() + "," + pt.getNGramFeatures();
			s+= "," + t.sentiment;
			ptweets.add(s);
		});
	}
	
	public static void readDataset() {
		System.out.print("Reading dataset....");
		try (Stream<String> lines = Files.lines(
				Paths.get("C:/Users/V2/Desktop/data/processedDatasetEnglishTweetsOnly.txt"),
				Charset.defaultCharset())) {
			lines.forEachOrdered(line -> {
				// sentiment,tweetID,tweetText
				String[] tokens = line.split(",");
				tweets.add(new TweetInstance2(tokens[2], tokens[0]));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(" done.");
	}
}

class TweetInstance2 {
	
	int sentiment;
	String text;
	
	public TweetInstance2(String text, String sentiment) {
		this.text = text;
		this.sentiment = Integer.parseInt(sentiment);
	}
}

