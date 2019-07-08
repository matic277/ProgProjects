package Tokenizer;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Words.AbsMeasurableWord;
import Words.IWord;
import Words.NegationWord;


public class Tweet {
	
	// source where newline chars are removed
	String cleanSource;
	
	// source with original text
	String sourceText;
	
	private String username;

	private ArrayList<IWord> words;
	
	private int numOfNegativeWords = 0;
	private int numOfNeutralWords = 0;
	private int numOfPositiveWords = 0;
	
	private double sumOfNegativeWords = 0;
	private double sumOfNeutralWords = 0;
	private double sumOfPositiveWords = 0;
	
	// not used atm
	private int sentiment;
	
	public Tweet(String sourceText, String username) {
		this.sourceText = sourceText;
		this.username = username;
	}
	
	private void test() {
		// if a word has a negator in front of it,
		// flip its pleasantness value
		IWord word, nextWord;
		for (int i=0; i<words.size()-1; i++) {
			word = words.get(i);
			
			if (word instanceof NegationWord) {
				// find next measurable word and flip its value
				for (int j=i+1; j<words.size(); j++) {
					nextWord = words.get(j);
					if (nextWord instanceof AbsMeasurableWord) {
						AbsMeasurableWord w = (AbsMeasurableWord) nextWord;
						w.setFlipPleasantness();
						words.set(j, w);
						break;
					}
				}
			}
		}
		
		// if a word is upper-case (or close to)
		// magnify its pleasantness value
		// (how close is defined in upper-case function)
		for (IWord w : words) {
			if (w instanceof AbsMeasurableWord && isUpperCase(w.getSourceText())) {
				AbsMeasurableWord mw = (AbsMeasurableWord) w;
				mw.magnifyPleasantness();
			}
		}
	}
	
	public void processTweet() {
		Tokenizer t = new Tokenizer(sourceText);
		words = t.tokenizeTweet();
		cleanSource = t.cleanSourceText;
		
		test();
		doSomeStatistics();
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
	
	private boolean isUpperCase(String word) {
		int uppercaseLettersNum = 0;
		for (int i=0; i<word.length(); i++) {
			if (Character.isUpperCase(word.charAt(i))) {
				uppercaseLettersNum++;
			}
		}
		double ratio = (double)uppercaseLettersNum / sourceText.length();
		return (ratio >= 0.4);
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
	
	public String getStatistics() {
		DecimalFormat format = new DecimalFormat("#.####");
		String s = "";
		s = numOfPositiveWords + "," + numOfNeutralWords + "," + numOfNegativeWords + ",";
		s += format.format(sumOfPositiveWords) + ", " + format.format(sumOfNeutralWords) + ", " + format.format(sumOfNegativeWords);
		return s;
	}
	
	// not needed anymore?
//	public void print() {
//		System.out.println("Tree of tweet:");
//		System.out.println("\t|-> Source text: '" + sourceText + "'");
//		System.out.println("\t|-> Tokens:");
//		
//		for (IWord w : words) {
//			System.out.println("\t|\t|-> " + w.toString());
//		}
//		
//		System.out.println("\t|-> Stats:");
//		System.out.println("\t\t|-> Num of neg words: " + numOfNegativeWords);
//		System.out.println("\t\t|-> Num of neu words: " + numOfNeutralWords);
//		System.out.println("\t\t|-> Num of pos words: " + numOfPositiveWords);
//		System.out.println("\t\t|-> Sum of neg words: " + sumOfNegativeWords);
//		System.out.println("\t\t|-> Sum of neu words: " + sumOfNeutralWords);
//		System.out.println("\t\t|-> Sum of pos words: " + sumOfPositiveWords);
//	}
}
