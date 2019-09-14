package Tokenizer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import AbstractWordClasses.AbsMeasurableWord;
import AbstractWordClasses.AbsWord;

public class Sentence {
	
	String sentence;
	ArrayList<AbsWord> words;
	
	private double sentiment;
	
	private final double magnifyValue = 1.3;
	
	public Sentence(String sentence) {
		this.sentence = sentence;
		processSentence();
		calculateSentiment();
	}
	
	private void processSentence() {
		Tokenizer t = new Tokenizer();
		
		// create word tokens
		String[] tokens = sentence.split(" ");
		words = t.classifyAndGetWords(tokens);
	}
	
	public ArrayList<AbsWord> getWords() {
		return words;
	}
	
	// only call this if you changed sentiment
	// of words after creating this class
	public void calculateSentiment() {
		double sentiment = 0;
		for (int i=0; i<words.size(); i++) {
			AbsWord w = words.get(i);
			if (w instanceof AbsMeasurableWord) {
				AbsMeasurableWord mw = (AbsMeasurableWord) w;
				sentiment += mw.getPleasantness();
			}
		}
		this.sentiment = sentiment;
//		
//		if (sentence.equals("Not bad.")) {
//			double sent = 0;
//			for (int i=0; i<words.size(); i++) {
//				AbsWord w = words.get(i);
//				if (w instanceof AbsMeasurableWord) {
//					AbsMeasurableWord mw = (AbsMeasurableWord) w;
//					sent += mw.getPleasantness();
//					System.out.println("adding : " + mw.getSourceText() + "val: " + mw.getPleasantness());
//				}
//			}
//			System.out.println("end sent: " + sent);
//		}
	}
	
	public double getSentiment() {
		return sentiment;
	}
	
	public String getSentence() {
		return sentence;
	}
	
	public String toStringLast() {
		DecimalFormat format = new DecimalFormat("#.###");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format.setDecimalFormatSymbols(symbols);
		
		StringBuilder sb = new StringBuilder("\t\t|-> Sentence: ");
		sb.append(sentence);
		sb.append("\n");
		
		for (AbsWord w : words) {
			sb.append("\t\t|-> ");
			sb.append(w.toString());
			sb.append("\n");
		}
		sb.append("\t\t|-> Sentence sentiment value: " + format.format(sentiment));
		return sb.toString();
	}
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		format.setDecimalFormatSymbols(symbols);
		
		StringBuilder sb = new StringBuilder("\t|\t|-> Sentence: ");
		sb.append(sentence);
		sb.append("\n");
		

		for (AbsWord w : words) {
			sb.append("\t|\t|-> ");
			sb.append(w.toString());
			sb.append("\n");
		}
		sb.append("\t|\t|-> Sentence sentiment value: " + format.format(sentiment));			
		
		sb.append("\n\t|");
		return sb.toString();
	}
	
	public void magnifySentiment() {
		this.sentiment *= magnifyValue;
	}

}
