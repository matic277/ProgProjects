package Tokenizer;
import java.util.ArrayList;

import Dictionaries.DictionaryCollection;
import StreamConsumer.Tweet;
import Words.Acronym;
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
	
	Tweet tweet;
	
	ArrayList<IWord> words;
	String sourceText;
	
	DictionaryCollection dictionaries;
	
	int numOfNegativeWords = 0;
	int numOfNeutralWords = 0;
	int numOfPositiveWords = 0;
	
	int sumOfNegativeWords = 0;
	int sumOfNeutralWords = 0;
	int sumOfPositiveWords = 0;
	
	// don't remove ' or -
	String charsToRemove = " ¨\"#$%&/()=*ÐŠÈÆŽŠðšæèž:;,_~¡^¢°²`ÿ´½¨¸.*\"<>¤ßè×÷\\â€¦™«";
	String importantChars = "!?";

	public Tokenizer(Tweet tweet) {
		this.tweet = tweet;
		sourceText = tweet.sourceText;
		dictionaries = DictionaryCollection.getDictionaryCollection();
	}
	
	public ProcessedTweet processTweet() {
		// clean strings of new line chars and stuff
		String newLine = System.getProperty("line.separator");
		
		String cleanSource = sourceText;
		cleanSource = cleanSource.replace("\n\n", " ");
		cleanSource = cleanSource.replace("\n", " ");
//		cleanSource = cleanSource.replace("\n\t", "");
//		cleanSource = cleanSource.replace("\t", "");
//		cleanSource = cleanSource.replace(newLine, "");
		tweet.cleanSource = cleanSource;
		
		// init size of list of words
		String[] tokens = cleanSource.split(" ");		
		words = new ArrayList<IWord>(tokens.length);
		
		classifyWords(tokens);
		
		return new ProcessedTweet(tweet, words);
	}
	
	private String cleanToken(String token) {
		for (int i=0; i<charsToRemove.length(); i++) {
			token = token.replace(charsToRemove.charAt(i) + "", "");
		}
		return token;
	}
	
	private void classifyWords(String[] tokens) {
		
		
		for (String token : tokens)
		{
			if (token.length() == 0) continue;
			
			String loweredToken = token.toLowerCase();
			
			// SMILEY
			// first, check for smileys since
			// they have the special characters
			if (Smiley.isType(loweredToken)) {
				words.add(new Smiley(token));
				continue;
			}
			
			// URL
			if (URL.isType(loweredToken)) {
				words.add(new URL(token));
				continue;
			}
			
			// HASHTAG
			if (Hashtag.isType(loweredToken)) {
				words.add(new Hashtag(token));
				continue;
			}
			
			// now that we know it's not any relevant
			// tag, we can remove strange character
			String cleanedToken = cleanToken(token);
			String cleanedLoweredToken = cleanToken(loweredToken);
			
			// NOTE:
			// always probe hash-table with lowered strings
			// but save words *as they are*, not lowered!
			
			// NEGATION WORD
			if (NegationWord.isType(cleanedLoweredToken)) {
				words.add(new NegationWord(cleanedToken));
				continue;
			}
			
			// ACRONYM
			if (Acronym.isType(cleanedLoweredToken)) {
				words.add(new Acronym(cleanedToken));
				continue;
			}
			
			// STOP WORD
			if (StopWord.isType(cleanedLoweredToken)) {
				words.add(new StopWord(cleanedToken));
				continue;
			}
			
			// AFFECTION WORD
			if (AffectionWord.isType(cleanedLoweredToken)) {
				words.add(new AffectionWord(cleanedToken));
				continue;
			}
			
			// else, unknown/other word
			words.add(new Other(cleanedToken));
		}
		
		
		
//		
//		// classify words
//		for (String token : tokens) {
//			if (token.length() == 0) continue;
//			
//			// preprocessing ????
//			token = token.replace(",", "");
//			token = token.replace("/", "");
//			token = token.replace("!", "");
//			token = token.replace("\\", "");
//			token = token.replace("\t", "");
//			token = token.replace("\n", "");
//			
//			String originalCaseToken = token; // not lower-cased
//			token = token.toLowerCase();
//			
//			// hashtags
//			if (Hashtag.isType(token)) {
//				words.add(new Hashtag(token));
//			}
//			
//			// smileys
//			else if (dictionaries.getSmileyDictionary().contains(token)) {
//				IWord word = dictionaries.getSmileyDictionary().getEntry(token);
//				words.add(new Smiley(
//					word.getSourceText(),
//					word.getPleasantness()
//				));
//			}
//			
//			// @ tags
//			else if (Target.isType(token)) {
//				words.add(new Target(token));
//			}
//			
//			// urls
//			else if (URL.isType(token)) {
//				words.add(new URL(token));
//			}
//			
//			// negation words
//			else if (dictionaries.getNegationwordsDictionary().contains(token)) {
//				IWord word = dictionaries.getNegationwordsDictionary().getEntry(token);
//				words.add(new NegationWord(
//					word.getSourceText()
//				));
//			}
//
//			// whissell words
//			else if (dictionaries.getWhissellDictionary().contains(token)) {
//				IWord word = dictionaries.getWhissellDictionary().getEntry(token);
//				words.add(new AffectionWord(
//						word.getSourceText(),
//						word.getPleasantness(),
//						word.getActivation(),
//						word.getImagery()
//				));
//			}
//			
//			// stop words
//			else if (dictionaries.getStopwordsDictionary().contains(token)) {
//				IWord word = dictionaries.getStopwordsDictionary().getEntry(token);
//				words.add(new StopWord(
//					word.getSourceText()
//				));
//			}
//			
//			// other
//			else {
//				words.add(new Other(token));
//			}
//		}
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
