package Tokenizer;
import java.util.ArrayList;

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
	
	String sourceText;
	String cleanSourceText;
	
	private ArrayList<IWord> words;
	
	// don't remove ' or -
	private String charsToRemove = " ¨\"#$%&/()=*ÐŠÈÆŽŠðšæèž:;,_~¡^¢°²`ÿ´½¨¸.*\"<>¤ßè×÷\\â€¦™«";
	private String importantChars = "!?";

	public Tokenizer(String cleanTweetText) {
		this.sourceText = cleanTweetText;
	}
	
	public ArrayList<IWord> tokenizeTweet() {
		// clean strings of new line chars and stuff
		// String newLine = System.getProperty("line.separator");
		
//		String cleanSource = sourceText;
//		cleanSource = cleanSource.replace("\n\n", " ");
//		cleanSource = cleanSource.replace("\n", " ");
//		cleanSourceText = cleanSource;
		cleanSourceText = sourceText.replaceAll("(\\n)+", " ");
		
		// init size of list of words
		String[] tokens = cleanSourceText.split(" ");		
		words = new ArrayList<IWord>(tokens.length);
		
		classifyWords(tokens);
		
		return words;
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
			
			// TARGET
			if (Target.isType(loweredToken)) {
				words.add(new Target(token));
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
	}
}
