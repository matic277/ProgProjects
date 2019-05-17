package Tokenizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements Runnable {
	
	Object[] tweets;
	
	String relativePath_OutputFile = "results.txt";
	
	public Logger(Object[] tweets) {
		this.tweets = tweets;
		System.out.println("Logger created and ran, number of tweets to log: " + tweets.length);
	}
	
	@Override
	public void run() {
		System.out.println("Logger writing file: '"+relativePath_OutputFile+"'");
		
		File file = new File(relativePath_OutputFile);
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i=0; i<tweets.length; i++) {
				writer.write("Tweet no."+i);
				writer.newLine();
				writer.write(tweets[i].toString());
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
