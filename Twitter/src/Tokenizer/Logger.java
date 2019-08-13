package Tokenizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger{
	
	Tweet[] tweets;
	
	String relativePath_OutputFile = "results.txt";
	String relativePath_OutputFile2 = "results_csv.txt";
	String relativePath_OutputFile3 = "resultsfull_csv.txt";
	
	public Logger(Tweet[] tweets) {
		this.tweets = tweets;
		System.out.println("\nLogger created, number of tweets waiting to log: " + tweets.length);
	}
	
	public void saveResultsAsCsv() {
		System.out.print("\t|-> Logger writing file: '"+relativePath_OutputFile2+"'... ");
		
		File file = new File(relativePath_OutputFile2);
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i=0; i<tweets.length; i++) {
				writer.write(tweets[i].getCleanSource() + ",");
				writer.write(tweets[i].getSentiment() + ""); // convert to string because BufferedWriter doesn't treat ints as expected
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
	
	public void saveResultsWithStatisticsAsCsv() {
		System.out.print("\t\\-> Logger writing file: '"+relativePath_OutputFile3+"'... ");
		
		File file = new File(relativePath_OutputFile3);
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i=0; i<tweets.length; i++) {
				writer.write(tweets[i].getCleanSource() + ",");
				writer.write(tweets[i].getStatistics() + ",");
				writer.write(tweets[i].getSentiment() + "");
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

}
