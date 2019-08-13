package Tokenizer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import AbstractWordClasses.AbsMeasurableWord;
import AbstractWordClasses.AbsWord;
import Dictionaries.AbsDictionary;
import Dictionaries.DictionaryCollection;
import Dictionaries.IDictionary;
import Dictionaries.NGramDictionary;
import Words.Emoji;
import Words.Hashtag;
import Words.NegationWord;
import Words.Target;
import Words.URL;
import Words.NGramEntry;


public class Tweet {
	
	// source where newline chars are removed
	String cleanSource;
	
	// source with original text
	String sourceText;
	
	private String username;

	private ArrayList<AbsWord> words;
	
	private int numOfNegativeWords = 0;
	private int numOfNeutralWords = 0;
	private int numOfPositiveWords = 0;
	
	private double sumOfNegativeWords = 0;
	private double sumOfNeutralWords = 0;
	private double sumOfPositiveWords = 0;
	
	// not used atm
	private int sentiment;
	
	private int[] ngramFeatures;
	
	public Tweet(String sourceText, String username) {
		this.sourceText = sourceText;
		this.username = (username == null)? "{UNKNOWN}" : username;
	}
	
	private void test() {
		// if a word has a negator in front of it,
		// flip its pleasantness value
		AbsWord word, nextWord;
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
		// magnify its pleasantness value, except for emojis
		// (how close is defined in upper-case function)
		for (AbsWord w : words) {
			if (w instanceof AbsMeasurableWord && !(w instanceof Emoji) && isUpperCase(w.getSourceText())) {
				AbsMeasurableWord mw = (AbsMeasurableWord) w;
				mw.magnifyPleasantness();
			}
		}
	}
	
	public void processTweet() {
		Tokenizer t = new Tokenizer(sourceText);
		t.tokenizeTweet();
		
		words = t.getTokens();
		cleanSource = t.cleanSourceText;
		
		test();
		doSomeStatistics();
		buildNGramFeatures();
	}
	
	private void buildNGramFeatures() {
		ArrayList<String> ngramWords = new ArrayList<String>(words.size());
		words.forEach(w -> {
			if (w instanceof Emoji) return;
			if (w instanceof URL) return;
			if (w instanceof Target) return;
			if (w instanceof Hashtag) return;
			ngramWords.add((w.getProcessedText() == null || w.getProcessedText().length() == 0)? 
					w.getSourceText() : w.getProcessedText());
		});
		
		NGramDictionary dictionary = (NGramDictionary) DictionaryCollection.getDictionaryCollection().getNGramDictionary();
		int numberOfFeatures = dictionary.getHashmap().size();
		ArrayList<Integer> featureSeq = new ArrayList<Integer>(numberOfFeatures);
		
		for (int i=1; i<4; i++) {
			NGram ngram = new NGram(i, ngramWords);
			ArrayList<Gram> list = ngram.getListOfNGrams();
			list.forEach(g -> {
				if (dictionary.contains(g.ngram)) {
					NGramEntry entry = (NGramEntry) dictionary.getEntry(g.ngram);
					featureSeq.add(entry.getSequenceNumber());
				}
			});
		}
		
		int[] featureList = new int[numberOfFeatures];
		for (int i=0; i<featureList.length; i++) {
			if (featureSeq.contains(new Integer(i)))
				featureList[i] = 1;
			else
				featureList[i] = 0;
		}
		this.ngramFeatures = featureList;
	}
	
	private void doSomeStatistics() {
		for (AbsWord word : words) {
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
//		if (numOfNegativeWords > numOfNeutralWords)
//			if (numOfNegativeWords > numOfPositiveWords) return -1;
//			else return 1;
//		if (numOfNeutralWords > numOfPositiveWords)
//			return 0;
//		return 1;
		
		double sentiment = 0;
		AbsWord w;
		for (int i=0; i<words.size(); i++) {
			w = words.get(i);
			if (w instanceof AbsMeasurableWord) {
				AbsMeasurableWord mw = (AbsMeasurableWord) w;
				sentiment += mw.getPleasantness();
			}
		}

		
		
		if (sentiment >= positiveThreshold) return 1;
		else if (sentiment <= negativeThreshold) return -1;
		else return 0;
	}
	public static double positiveThreshold = 0.1;
	public static double negativeThreshold = -0.55;
	
	public String getSourceText() {
		return sourceText;
	}
	
	public String getCleanSource() {
		return cleanSource;
	}
	
	public ArrayList<AbsWord> getTokens() {
		return this.words;
	}
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format.setDecimalFormatSymbols(symbols);
		
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
		s += "\t|-> Sum of pos words: " + format.format(sumOfPositiveWords) + "\n";
		s += "\t\\-> NGram features:  " + getNGramFeatures();
		
		s += "---------------------------\n";
		
		return s;
	}
	
	public String getNGramFeatures() {
		String s = "";
		for (int i=0; i<ngramFeatures.length-1; i++) {
			s += ngramFeatures[i] + ",";
		}
		s+= ngramFeatures[ngramFeatures.length - 1];
		return s;
	}
	
	public String getStatistics() {
		DecimalFormat format = new DecimalFormat("#.####");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format.setDecimalFormatSymbols(symbols);
		String s = "";
		s = numOfPositiveWords + "," + numOfNeutralWords + "," + numOfNegativeWords + ",";
		s += format.format(sumOfPositiveWords) + "," + format.format(sumOfNeutralWords) + "," + format.format(sumOfNegativeWords);
		return s;
	}
}
