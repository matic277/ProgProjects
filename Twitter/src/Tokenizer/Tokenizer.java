package Tokenizer;
import java.util.ArrayList;

import AbstractWordClasses.AbsWord;
import Words.Acronym;
import Words.AffectionWord;
import Words.Hashtag;
import Words.NegationWord;
import Words.Other;
import Words.Smiley;
import Words.StopWord;
import Words.Target;
import Words.URL;

public class Tokenizer {
	
	String sourceText;
	String cleanSourceText;
	
	private ArrayList<AbsWord> words;
	
	private String suspects = "-'!? ¨\"#$%&/()=*ÐŠÈÆŽŠðšæèž:;,_~¡^¢°²`ÿ´½¨¸.*\"<>¤ßè×÷\\â€¦™«";

	public Tokenizer(String cleanTweetText) {
		this.sourceText = cleanTweetText;
	}
	
	public void tokenizeTweet() {
		// clean strings of new line chars and stuff
		cleanSourceText = sourceText.replaceAll("(\\n)+", " ");
		
		// split by space and init the
		// size of list of words
		String[] tokens = cleanSourceText.split(" ");		
		words = new ArrayList<AbsWord>(tokens.length);
		
		classifyWords(tokens);
	}
	
	private String completelyCleanToken(String token) {
		for (int i=0; i<suspects.length(); i++) {
			token = token.replace(suspects.charAt(i) + "", "");
		}
		return token;
	}
	
	// don't remove -
	private String cleanToken1(String token) {
		String chars = suspects.replace("-", "");
		for (int i=0; i<chars.length(); i++) {
			token = token.replace(chars.charAt(i) + "", "");
		}
		return token;
	}
	
	private String cleanToken2(String token) {
		for (int i=0; i<suspects.length(); i++) {
			token = token.replace(suspects.charAt(i) + "", "");
		}
		return token;
	}
	
	private void classifyWords(String[] tokens) {
		for (String token : tokens)
		{
			
			if (token.length() == 0) continue;
			
			String rawToken = token;
			
			/*
			TYPE CHECKING:
			Remove all suspect characters from *rawToken*, but don't
			remove "-". These two are somewhat important.
			Use *checkToken1* and *checkToken2* for checking types!
			*/
		
			String checkToken1 = cleanToken1(rawToken).toLowerCase();	// without - 
			String checkToken2 = cleanToken2(rawToken).toLowerCase(); 	// with - 

			// SMILEY
			// first, check for smileys since
			// they have the special characters
			if (Smiley.isType(rawToken)) {
				words.add(new Smiley(rawToken));
			}
			
			// URL
			else if (URL.isType(rawToken)) {
				words.add(new URL(rawToken));
			}
			
			// HASHTAG
			else if (Hashtag.isType(rawToken)) {
				words.add(new Hashtag(rawToken, null));
			}
			
			// TARGET
			else if (Target.isType(rawToken)) {
				words.add(new Target(rawToken));
			}
			
			
			
			
			// NEGATION WORD
			else if (NegationWord.isType(checkToken1)) {
				words.add(new NegationWord(rawToken, checkToken1));

			}
			else if (NegationWord.isType(checkToken2)) {
				words.add(new NegationWord(rawToken, checkToken2));
			}
			
			// ACRONYM
			else if (Acronym.isType(checkToken1)) {
				words.add(new Acronym(rawToken, checkToken1));

			}
			else if (Acronym.isType(checkToken2)) {
				words.add(new Acronym(rawToken, checkToken2));
			}
			
			// STOP WORD
			else if (StopWord.isType(checkToken1)) {
				words.add(new StopWord(rawToken, checkToken1));

			}
			else if (StopWord.isType(checkToken2)) {
				words.add(new StopWord(rawToken, checkToken2));
			}
			
			// AFFECTION WORD
			else if (AffectionWord.isType(checkToken1)) {
				words.add(new AffectionWord(rawToken, checkToken1));

			}
			else if (AffectionWord.isType(checkToken2)) {
				words.add(new AffectionWord(rawToken, checkToken2));
			}
			
			// else, unknown/other word
			else words.add(new Other(rawToken, null));
		}
	}
	
//	private void classifyWords(String[] tokens) {
//		boolean containsExclamation = false;
//		for (String token : tokens)
//		{
//			
//			if (token.length() == 0) continue;
//			
//			// for checking: smiley, url, hastag or target 
//			String loweredToken = token.toLowerCase();
//			
//			// for checking: all the rest
//			String cleanedToken = cleanToken(token);
//			String cleanedLoweredToken = cleanToken(loweredToken);
//			
//			// if a token contains one or more !, then
//			// magnify its' pleasantness, also, remove
//			// any ? chars
//			containsExclamation = cleanedLoweredToken.contains("!");
//			cleanedLoweredToken = cleanedLoweredToken.replace("!", "");
//			cleanedLoweredToken = cleanedLoweredToken.replace("?", "");
//			cleanedToken = cleanedToken.replace("!", "");
//			cleanedToken = cleanedToken.replace("?", "");
//
//			// NOTE:
//			// always probe hash-table with lowered strings
//			// *cleanedLoweredToken*, but save words as they are,
//			// thats the *cleanedToken* variable
//			
//			
//			// SMILEY
//			// first, check for smileys since
//			// they have the special characters
//			if (Smiley.isType(loweredToken)) {
//				words.add(new Smiley(token));
//			}
//			
//			// URL
//			else if (URL.isType(loweredToken)) {
//				words.add(new URL(token));
//			}
//			
//			// HASHTAG
//			else if (Hashtag.isType(loweredToken)) {
//				words.add(new Hashtag(token));
//			}
//			
//			// TARGET
//			else if (Target.isType(loweredToken)) {
//				words.add(new Target(token));
//			}
//			
//			// using *cleanedLoweredToken* from this point forward
//			// since we know its not a target, hashtag, url or smiley
//			
//			// NEGATION WORD
//			else if (NegationWord.isType(cleanedLoweredToken)) {
//				words.add(new NegationWord(cleanedToken));
//			}
//			
//			// ACRONYM
//			else if (Acronym.isType(cleanedLoweredToken)) {
//				Acronym acr = new Acronym(cleanedToken);
//				if (containsExclamation) acr.magnifyPleasantness();
//				words.add(acr);
//			}
//			
//			// STOP WORD
//			else if (StopWord.isType(cleanedLoweredToken)) {
//				words.add(new StopWord(cleanedToken));
//			}
//			
//			// AFFECTION WORD
//			else if (AffectionWord.isType(cleanedLoweredToken)) {
//				AffectionWord aw = new AffectionWord(cleanedToken);
//				if (containsExclamation) aw.magnifyPleasantness();
//				words.add(aw);
//			}
//			
//			// else, unknown/other word
//			else words.add(new Other(cleanedToken));
//		}
//	}
	
	public ArrayList<AbsWord> getTokens() {
		return words;
	}
}
