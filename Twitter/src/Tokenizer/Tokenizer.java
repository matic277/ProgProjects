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
	
	// don't remove ' or - or !
	private String charsToRemove = " ¨\"#$%&/()=*ÐŠÈÆŽŠðšæèž:;,_~¡^¢°²`ÿ´½¨¸.*\"<>¤ßè×÷\\â€¦™«";

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
		boolean containsExclamation = false;
		for (String token : tokens)
		{
			
			if (token.length() == 0) continue;
			
			// for checking: smiley, url, hastag or target 
			String loweredToken = token.toLowerCase();
			
			// for checking: all the rest
			String cleanedToken = cleanToken(token);
			String cleanedLoweredToken = cleanToken(loweredToken);
			
			// if a token contains one or more !, then
			// magnify its' pleasantness, also, remove
			// any ? chars
			containsExclamation = cleanedLoweredToken.contains("!");
			cleanedLoweredToken = cleanedLoweredToken.replace("!", "");
			cleanedLoweredToken = cleanedLoweredToken.replace("?", "");
			cleanedToken = cleanedToken.replace("!", "");
			cleanedToken = cleanedToken.replace("?", "");

			// NOTE:
			// always probe hash-table with lowered strings
			// *cleanedLoweredToken*, but save words as they are,
			// thats the *cleanedToken* variable
			
			
			// SMILEY
			// first, check for smileys since
			// they have the special characters
			if (Smiley.isType(loweredToken)) {
				words.add(new Smiley(token));
			}
			
			// URL
			else if (URL.isType(loweredToken)) {
				words.add(new URL(token));
			}
			
			// HASHTAG
			else if (Hashtag.isType(loweredToken)) {
				words.add(new Hashtag(token));
			}
			
			// TARGET
			else if (Target.isType(loweredToken)) {
				words.add(new Target(token));
			}
			
			// using *cleanedLoweredToken* from this point forward
			// since we know its not a target, hashtag, url or smiley
			
			// NEGATION WORD
			else if (NegationWord.isType(cleanedLoweredToken)) {
				words.add(new NegationWord(cleanedToken));
			}
			
			// ACRONYM
			else if (Acronym.isType(cleanedLoweredToken)) {
				Acronym acr = new Acronym(cleanedToken);
				if (containsExclamation) acr.magnifyPleasantness();
				words.add(acr);
			}
			
			// STOP WORD
			else if (StopWord.isType(cleanedLoweredToken)) {
				words.add(new StopWord(cleanedToken));
			}
			
			// AFFECTION WORD
			else if (AffectionWord.isType(cleanedLoweredToken)) {
				AffectionWord aw = new AffectionWord(cleanedToken);
				if (containsExclamation) aw.magnifyPleasantness();
				words.add(aw);
			}
			
			// else, unknown/other word
			else words.add(new Other(cleanedToken));
		}
	}
}
