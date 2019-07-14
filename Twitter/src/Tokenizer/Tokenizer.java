package Tokenizer;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import AbstractWordClasses.AbsWord;
import Words.Acronym;
import Words.AffectionWord;
import Words.Emoji;
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
		
		// replace emojis encoded as ?, as emoji code-points
		findEmojis();
		
		// split by space and init the
		// size of list of words
		String[] tokens = cleanSourceText.split(" ");		
		words = new ArrayList<AbsWord>(tokens.length);	
		
		classifyWords(tokens);
	}
	
	private void findEmojis() {
		String src = cleanSourceText;
//        System.out.println(src);
//		
//        src.codePoints().filter(cp -> cp >= 256).forEach(cp -> {
//            System.out.printf("0x%X = %s%n",
//                cp, Character.getName(cp));
//        });
        
        IntStream s = src.codePoints();
        int[] arr = s.toArray();
        
        String str = "";
        int pointsI = 0;
        for (int i=0; i<arr.length; i++) {
        	if(arr[i] >= 256) {
        		str += " 0x" + Integer.toHexString(arr[i]).toLowerCase() + " ";
        		// points.get(pointsI)
        		pointsI++;
        	} else {
        		str += (char)arr[i];
        	}
        }

        // change these to normal characters:
        // U+8217 - RIGHT SINGLE QUOTATION MARK
        // U+201C - LEFT DOUBLE QUOTATION MARK
        // U+201D - RIGHT DOUBLE QUOTATION MARK
        // U+2018 - LEFT SINGLE QUOTATION MARK
        // (0x instead od U+)
        
        str = str.replace("0x8217", "'");
        str = str.replace("0x201C", "'");
        str = str.replace("0x201D", "\"");
        str = str.replace("0x2018", "\"");
        
//        System.out.println("-----OUTPUT: ");
//        
//        System.out.println(str);
        
        cleanSourceText = str;
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
			remove "-".
			Use *checkToken1* and *checkToken2* for checking types!
			Use *rawToken* when checking for emojis!
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
			
			// EMOJI
			else if (Emoji.isType(rawToken)) {
				words.add(new Emoji(rawToken, null));
			}
			
			
			
			
			// from here on out, the order of type checking is important
			// always check first for negation words or stop-words, since
			// Whissell dictionary contains some of them
			
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
			else words.add(new Other(rawToken, checkToken2));
		}
	}
	
	public ArrayList<AbsWord> getTokens() {
		return words;
	}
}
