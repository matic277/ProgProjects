package Tokenizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Logger {
    
    Tweet[] tweets;
    
    String relativePath_OutputFile = "results.txt";
    String relativePath_OutputFile2 = "results_csv.txt";
    String relativePath_OutputFile3 = "resultsfull_csv.txt";
    String relativePath_OutputFile4 = "results_2way_csv_1.6.2021.txt";
    String relativePath_OutputFile5 = "results_3way_csv_1.6.2021.txt";
    String seenUsers = "seen_users.txt";
    
    public Logger(Tweet[] tweets, String folderPath) {
        this.tweets = tweets;
        System.out.println("\nLogger created, number of tweets waiting to log: " + tweets.length);
        
        relativePath_OutputFile = folderPath + "results.txt";
        relativePath_OutputFile2 = folderPath + "results_csv.txt";
        relativePath_OutputFile3 = folderPath + "resultsfull_csv.txt";
        relativePath_OutputFile4 = folderPath + "results_2way_csv_1.6.2021.txt";
        relativePath_OutputFile5 = folderPath + "results_3way_csv_1.6.2021.txt";
    }
    
    
    File file_users = new File(seenUsers);
    BufferedWriter writer_users = null;
    // saving seen usernames
    public Logger() {
        
        file_users = new File(seenUsers);
        try {
            writer_users = new BufferedWriter(new FileWriter(file_users, true));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void saveSeenUsers(String user) {
        System.out.print("\t\\-> Logger writing file: '"+seenUsers+"'... ");
        
        try {
            writer_users.write(user);
            writer_users.newLine();
            
            writer_users.flush();
        } catch (IOException e) {
            System.out.println("Error at writing file!\n");
            e.printStackTrace();
        }
        
        System.out.println("File written.");
    }
    
    public void saveResults() {
        System.out.print("\t|-> Logger writing file: '"+relativePath_OutputFile+"'... ");
        
        File file = new File(relativePath_OutputFile);
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            
            for (int i=0; i<tweets.length; i++) {
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
    
//	public void saveResultsAsCsv() {
//		System.out.print("\t|-> Logger writing file: '"+relativePath_OutputFile2+"'... ");
//		
//		File file = new File(relativePath_OutputFile2);
//		BufferedWriter writer = null;
//		
//		try {
//			writer = new BufferedWriter(new FileWriter(file, true));
//			
//			for (int i=0; i<tweets.length; i++) {
//				writer.write(tweets[i].getCleanSource() + ",");
//				writer.write(tweets[i].getSentiment() + ""); // convert to string because BufferedWriter doesn't treat ints as expected
//				writer.newLine();
//			}
//			writer.flush();
//			writer.close();
//			
//		} catch (IOException e) {
//			System.out.println("Error at writing file!\n");
//			e.printStackTrace();
//		}
//
//		System.out.println("File written.");
//	}

//	public void saveResultsWithStatisticsAsCsv() {
//		System.out.print("\t\\-> Logger writing file: '"+relativePath_OutputFile3+"'... ");
//		
//		File file = new File(relativePath_OutputFile3);
//		BufferedWriter writer = null;
//		
//		try {
//			writer = new BufferedWriter(new FileWriter(file, true));
//			
//			for (int i=0; i<tweets.length; i++) {
//				writer.write(tweets[i].getCleanSource() + ",");
//				writer.write(tweets[i].getStatistics() + ",");
//				writer.write(tweets[i].getSentiment() + "");
//				writer.newLine();
//			}
//			writer.flush();
//			writer.close();
//			
//		} catch (IOException e) {
//			System.out.println("Error at writing file!\n");
//			e.printStackTrace();
//		}
//
//		System.out.println("File written.");
//	}
    
    public void saveResults2way() {
        System.out.print("\t\\-> Logger writing file: '"+relativePath_OutputFile4+"'... ");
        
        File file = new File(relativePath_OutputFile4);
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            
            for (int i=0; i<tweets.length; i++) {
                writer.write(tweets[i].getSentimentTwoWay() + ",");
                writer.write(tweets[i].getDate() + ",");
                writer.write(tweets[i].getCleanSource());
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
    
    public void saveResults3way() {
        System.out.print("\t\\-> Logger writing file: '"+relativePath_OutputFile5+"'... ");
        
        File file = new File(relativePath_OutputFile5);
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            
            for (int i=0; i<tweets.length; i++) {
                writer.write(tweets[i].getSentimentThreeWay() + ",");
                writer.write(tweets[i].getDate() + ",");
                writer.write(tweets[i].getCleanSource());
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
