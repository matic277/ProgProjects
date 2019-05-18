package Words;

import java.math.BigDecimal;
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
		
		// process the full version
		// with the use of Whissell
		processAcronym();
	}
	
	private void processAcronym() {
		fullText = fullText.toLowerCase();
		listOfWords = fullText.split(" ");
		
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		
		BigDecimal value;
		BigDecimal _175 = new BigDecimal(1.76);
		BigDecimal _2 = new BigDecimal(2);
		
		// class ranges (according to pleasantness)
		// <AFT>:
		//		[1, 1.75]	-> negative
		//		(1.75, 2.25)-> neutral
		//		[2, 3]		-> positive
		
		int positiveWordsNum = 0;
		int negativeWordsNum = 0;
		int neutralWordsNum = 0;
		
		double negativeWordsSum = 0;
		double positiveWordsSum = 0;
		double neutralWordsSum = 0;
		
		double negativeWordsAvg = 0;
		double positiveWordsAvg = 0;
		double neutralWordsAvg = 0;
		
		for (String word : listOfWords) {
			if (dictionary.contains(word)) {
				double pleasantness = dictionary.getEntry(word).getPleasantness();
				value = new BigDecimal(pleasantness);
				
				if (value.compareTo(_175) == -1) {
					negativeWordsNum++;
					negativeWordsSum += pleasantness;
				} else if (value.compareTo(_2) == -1) {
					neutralWordsNum++;
					neutralWordsSum += pleasantness;
				} else {
					positiveWordsNum++;
					positiveWordsSum += pleasantness;
				}
			} else {
				neutralWordsNum++;
				neutralWordsSum += 2.0;
			}
		}
		
		negativeWordsAvg = negativeWordsSum / negativeWordsNum;
		positiveWordsAvg = positiveWordsSum / positiveWordsNum;
		neutralWordsAvg =  neutralWordsSum  / neutralWordsNum;
		
		double totalAvg = (negativeWordsAvg + positiveWordsAvg + neutralWordsAvg) / 3;
		pleasantness = totalAvg;
		// TODO:
		// total overhead when calculating average
		// pleasantness, maybe change this ?
	}

	@Override
	public String getSourceText() {
		return sourceText;
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
		return -1;
	}

	@Override
	public double getImagery() {
		return -1;
	}

	@Override
	public boolean checkIntegrity() {
		if (sourceText.length() > 1) return true;
		return false;
	}

	@Override
	public String getString() {
		return sourceText;
	}
	
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", " + "'"+sourceText+"' -> " + "'" + fullText + "'" + ", P:" + format.format(pleasantness) + "]";
	}
}
