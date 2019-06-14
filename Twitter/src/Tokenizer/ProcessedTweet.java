package Tokenizer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import StreamConsumer.Tweet;
import Words.AbsMeasurableWord;
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
	
	double sumOfNegativeWords = 0;
	double sumOfNeutralWords = 0;
	double sumOfPositiveWords = 0;
	
	int sentiment;
	
	public ProcessedTweet(Tweet tweet, ArrayList<IWord> words) {
		this.cleanSource = tweet.cleanSource;
		this.sourceText = tweet.sourceText;
		this.username = tweet.username;
		this.words = words;
		
		test();
		doSomeStatistics();
	}
	
	private void test() {
		IWord word, nextWord;
		for (int i=0; i<words.size()-1; i++) {
			word = words.get(i);
			nextWord = words.get(i+1);
			
			if (word instanceof NegationWord) {
				if (nextWord instanceof AbsMeasurableWord) {
					AbsMeasurableWord w = (AbsMeasurableWord) nextWord;
					w.setFlipPleasantness();
					words.set(i+1, w);
					i++;
				}
			}
		}
	}
	
	private void doSomeStatistics() {
		for (IWord word : words) {
			// AffectionWord, Hastag, Smiley, Acronym, Phrase, (Emoji)
			if (word instanceof AbsMeasurableWord) {
				AbsMeasurableWord mw = (AbsMeasurableWord) word;
				double pleasantness = mw.getPleasantness();
				
				if (mw.isNegativePleasantness()) {
					numOfNegativeWords++;
					sumOfNegativeWords += pleasantness;
				} else if (mw.isNeutralPleasantness()) {
					numOfNeutralWords++;
					sumOfNeutralWords += pleasantness;
					
				} else {
					numOfPositiveWords++;
					sumOfPositiveWords += pleasantness;
				}
			}
			
			// URL, Target, StopWord, Other, NegationWord
			else {
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
		DecimalFormat format = new DecimalFormat("#.###");
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
		s += "\t|-> Sum of neg words: " + format.format(sumOfNegativeWords) + "\n";
		s += "\t|-> Sum of neu words: " + format.format(sumOfNeutralWords) + "\n";
		s += "\t\\-> Sum of pos words: " + format.format(sumOfPositiveWords) + "\n";
			
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
