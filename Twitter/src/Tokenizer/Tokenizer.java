package Tokenizer;
import java.util.ArrayList;

import Dictionaries.DictionaryCollection;
import Words.AffectionWord;
import Words.Hashtag;
import Words.IWord;
import Words.NegationWord;
import Words.Other;
import Words.Smiley;
import Words.StopWord;
import Words.Target;
import Words.URL;

public class Tokenizer {
	
	ArrayList<IWord> words;
	String sourceText;
	
	DictionaryCollection dictionaries;
	
	int numOfNegativeWords = 0;
	int numOfNeutralWords = 0;
	int numOfPositiveWords = 0;
	
	int sumOfNegativeWords = 0;
	int sumOfNeutralWords = 0;
	int sumOfPositiveWords = 0;

	public Tokenizer(String source) {
		sourceText = source;
		dictionaries = DictionaryCollection.getDictionaryCollection();
	}
	
	public ProcessedTweet processTweet() {
		// init size of list of words
		String[] tokens = sourceText.split(" ");
		words = new ArrayList<IWord>(tokens.length);
		
		classifyWords(tokens);
		
		return new ProcessedTweet(sourceText, words);
	}
	
	private void classifyWords(String[] tokens) {
		// classify words
		for (String token : tokens) {
			if (token.length() == 0) continue;
			
			// preprocessing ????
			token = token.toLowerCase();
			token = token.replace(",", "");
			token = token.replace("/", "");
			token = token.replace("\\", "");
			token = token.replace("\t", "");
			
			// hashtags
			if (Hashtag.isType(token)) {
				words.add(new Hashtag(token));
			}
			
			// smileys
			else if (dictionaries.getSmileyDictionary().contains(token)) {
				IWord word = dictionaries.getSmileyDictionary().getEntry(token);
				words.add(new Smiley(
					word.getSourceText(),
					word.getPleasantness()
				));
			}
			
			// @ tags
			else if (Target.isType(token)) {
				words.add(new Target(token));
			}
			
			// urls
			else if (URL.isType(token)) {
				words.add(new URL(token));
			}
			
			// negation words
			else if (dictionaries.getNegationwordsDictionary().contains(token)) {
				IWord word = dictionaries.getNegationwordsDictionary().getEntry(token);
				words.add(new NegationWord(
					word.getSourceText()
				));
			}

			// whissell words
			else if (dictionaries.getWhissellDictionary().contains(token)) {
				IWord word = dictionaries.getWhissellDictionary().getEntry(token);
				words.add(new AffectionWord(
					word.getSourceText(),
					word.getPleasantness(),
					word.getActivation(),
					word.getImagery()
				));
			}
			
			// stop words
			else if (dictionaries.getStopwordsDictionary().contains(token)) {
				IWord word = dictionaries.getStopwordsDictionary().getEntry(token);
				words.add(new StopWord(
					word.getSourceText()
				));
			}
			
			// other
			else {
				words.add(new Other(token));
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
		return (ratio > 0.4);
	}

}
