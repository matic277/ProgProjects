package Words;

import java.text.DecimalFormat;

import Dictionaries.DictionaryCollection;
import Dictionaries.IDictionary;
import Dictionaries.INode;

public class Acronym extends AbsMeasurableWord implements IWord, INode {
	
							// example:
	String sourceText;		// jk
	String fullText;		// just kidding
	String[] listOfWords;	// {just, kidding}
	
	double pleasantness;
	double imagery;
	double activation;

	public Acronym(String sourceText, String fullText) {
		this.sourceText = sourceText;
		this.fullText = fullText;
		
		// process the full-text pleasantness
		// with the use of Whissell dictionary
		calculatePleasantness();
	}
	public Acronym(String sourceText) {
		this.sourceText = sourceText;
		
		Acronym a = (Acronym)DictionaryCollection.getDictionaryCollection().getAcronymDictionary().getEntry(sourceText);
		this.fullText = a.getFullText();
		
		calculatePleasantness();
	}
	
	public static boolean isType(String s) {
		return DictionaryCollection.getDictionaryCollection().getAcronymDictionary().contains(s);
	}
	
	private void calculatePleasantness() {
		fullText = fullText.toLowerCase();
		listOfWords = fullText.split(" ");
		
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		
		int positiveWordsNum = 0;
		int negativeWordsNum = 0;
		int neutralWordsNum = 0;
		
		double negativeWordsSum = 0;
		double positiveWordsSum = 0;
		double neutralWordsSum = 0;
		
		double negativeWordsAvg = 0;
		double positiveWordsAvg = 0;
		double neutralWordsAvg = 0;
		
		// TODO: CHANGED HERE
		for (String word : listOfWords) {
			if (dictionary.contains(word)) {
				AbsMeasurableWord absWord = (AbsMeasurableWord) dictionary.getEntry(word);
				double pleasantness = absWord.getPleasantness();
				
				if (pleasantness < neutralThreshold) {
					negativeWordsNum++;
					negativeWordsSum += pleasantness;
				} else if (pleasantness < positiveThreshold) {
					neutralWordsNum++;
					neutralWordsSum += pleasantness;
				} else {
					positiveWordsNum++;
					positiveWordsSum += pleasantness;
				}
			} else {
				neutralWordsNum++;
			}
		}
		
		negativeWordsAvg = negativeWordsSum / negativeWordsNum;
		positiveWordsAvg = positiveWordsSum / positiveWordsNum;
		neutralWordsAvg =  neutralWordsSum  / neutralWordsNum;
		
		// some could be NaN (division by zero in the code above)
		negativeWordsAvg = (Double.isNaN(negativeWordsAvg))? 0 : negativeWordsAvg;
		positiveWordsAvg = (Double.isNaN(positiveWordsAvg))? 0 : positiveWordsAvg;
		neutralWordsAvg =  (Double.isNaN(neutralWordsAvg))? 0 : neutralWordsAvg;;
		
		double totalAvg = (negativeWordsAvg + positiveWordsAvg + neutralWordsAvg) / 3;
		pleasantness = totalAvg;
		
		// TODO:
		// total overhead when calculating average
		// pleasantness, but leaving it for now as
		// these numbers might play a role in the future
		
		// PROBLEM:
		// example: lol -> ['lol' -> 'laughing out loud', P:0,073]
		// lol only gets a pleasantness of 0,073 which is completely
		// neutral... probably not good?
		// Take the pleasantness of the most pleasant word???
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}
	
	public String getFullText() {
		return fullText;
	}
	
	public String[] getListOfWords() {
		return listOfWords;
	}

	@Override
	public String getTag() {
		return "<ACR>";
	}

	@Override
	public double getPleasantness() {
		return pleasantness;
	}

	@Override
	public double getActivation() {
		return -2;
	}

	@Override
	public double getImagery() {
		return -2;
	}
	
	@Override
	public boolean checkIntegrity() {
		if (sourceText.length() > 1 && checkValidValue(pleasantness)) {
			return true;
		}
		return false;
	}
	
	private boolean checkValidValue(double value) {
		if (value >= -1 && value <= 1) return true;
		return false;
	}
	
	@Override
	public String getString() {
		return sourceText;
	}
	
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", " + getSentimentTag() + ", '" + sourceText + "' -> " + "'" + fullText + "'" + ", P:" + format.format(pleasantness) + "]";
	}

	@Override
	public void setPleasantness(double pleasantness) {
		this.pleasantness = pleasantness;
		if (this.pleasantness < -1) this.pleasantness = -1;
		else if (this.pleasantness > 1) this.pleasantness = 1;
	}
	
	@Override
	public void setFlipPleasantness() {
		this.pleasantness *= -1;
	}
}
