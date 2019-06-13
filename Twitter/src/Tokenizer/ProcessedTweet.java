package Tokenizer;

import java.math.BigDecimal;
import java.util.ArrayList;

import StreamConsumer.Tweet;
import Words.AffectionWord;
import Words.IWord;
import Words.NegationWord;
import Words.Other;
import Words.Smiley;

public class ProcessedTweet {
	
	// source where newline chars are removed
	String cleanSource;
	
	// source with original text
	String sourceText;
	
	String username;

	ArrayList<IWord> words;
	
	int numOfNegativeWords = 0;
	int numOfNeutralWords = 0;
	int numOfPositiveWords = 0;
	
	int sumOfNegativeWords = 0;
	int sumOfNeutralWords = 0;
	int sumOfPositiveWords = 0;
	
	int sentiment;
	
	public ProcessedTweet(Tweet tweet, ArrayList<IWord> words) {
		this.cleanSource = tweet.cleanSource;
		this.sourceText = tweet.sourceText;
		this.username = tweet.username;
		this.words = words;
		
		doSomeStatistics();
		test();
	}
	
	private void test() {
		IWord word;
		for (int i=0; i<words.size(); i++) {
			word = words.get(i);
			
			if (word instanceof NegationWord) {
				if (words.get(i+1) instanceof AffectionWord || words.get(i+1) instanceof Smiley) {
					words.get(i+1).setFlipPleasantness();
					i++;
				}
			}
		}
	}
	
	private void doSomeStatistics() {
		for (IWord w : words) {
			double pleasantness = w.getPleasantness();
			
			if (w instanceof AffectionWord) {
				AffectionWord word = (AffectionWord) w;
				if (word.isNegativePleasantness()) {
					numOfNegativeWords++;
					sumOfNegativeWords += pleasantness;
				} else if (word.isNeutralPleasantness()) {
					numOfNeutralWords++;
					sumOfNeutralWords += pleasantness;
				} else {
					numOfPositiveWords++;
					sumOfPositiveWords += pleasantness;
				}
			}
			
			else if (w instanceof Smiley) {
				Smiley smiley = (Smiley) w;
				 if (smiley.isNeutralPleasantness()){
					numOfNeutralWords++;
					sumOfNeutralWords += pleasantness;
				}
				 else if (smiley.isNegativePleasantness()) {
					numOfNegativeWords++;
					sumOfNegativeWords += pleasantness;
				} else {
					numOfPositiveWords++;
					sumOfPositiveWords += pleasantness;
				}
			}
			
			else if (w instanceof Other){
				numOfNeutralWords++;
			}
 		}
	}
	
	// placeholder function for sentiment, returning biggest
	// number of numOf_x_Words
	public int getSentiment() {
		if (numOfNegativeWords > numOfNeutralWords)
			if (numOfNegativeWords > numOfPositiveWords) return -1;
			else return 1;
		if (numOfNeutralWords > numOfPositiveWords)
			return 0;
		return 1;
	}
	
	public String getSourceText() {
		return sourceText;
	}
	
	public String getCleanSource() {
		return cleanSource;
	}
	
	public String toString() {
		String s = "";
		s += "---------------------------\n";
		s += "Poster: " + username + "\n";
		s += "Source tweet:\n" + sourceText + "\n\n";
		s += "Clean tweet:\n" + cleanSource + "\n\n";
		s += "Tree of tweet:\n";
		
		for (int i=0; i<words.size()-1; i++) {
			s += "\t|-> " + words.get(i).toString() + "\n";
		}
		s += "\t\\-> " + words.get(words.size()-1).toString() + "\n\n";
		
		s += "Some statistics:\n";
		s += "\t|-> Num of neg words: " + numOfNegativeWords + "\n";
		s += "\t|-> Num of neu words: " + numOfNeutralWords + "\n";
		s += "\t|-> Num of pos words: " + numOfPositiveWords + "\n";
		s += "\t|-> Sum of neg words: " + sumOfNegativeWords + "\n";
		s += "\t|-> Sum of neu words: " + sumOfNeutralWords + "\n";
		s += "\t\\-> Sum of pos words: " + sumOfPositiveWords + "\n";
			
		s += "---------------------------\n";
		
		return s;
	}
	
	// not needed anymore?
	public void print() {
		System.out.println("Tree of tweet:");
		System.out.println("\t|-> Source text: '" + sourceText + "'");
		System.out.println("\t|-> Tokens:");
		
		for (IWord w : words) {
			System.out.println("\t|\t|-> " + w.toString());
		}
		
		System.out.println("\t|-> Stats:");
		System.out.println("\t\t|-> Num of neg words: " + numOfNegativeWords);
		System.out.println("\t\t|-> Num of neu words: " + numOfNeutralWords);
		System.out.println("\t\t|-> Num of pos words: " + numOfPositiveWords);
		System.out.println("\t\t|-> Sum of neg words: " + sumOfNegativeWords);
		System.out.println("\t\t|-> Sum of neu words: " + sumOfNeutralWords);
		System.out.println("\t\t|-> Sum of pos words: " + sumOfPositiveWords);
	}
}
