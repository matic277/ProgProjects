package Tokenizer;

import java.math.BigDecimal;
import java.util.ArrayList;

import Words.AffectionWord;
import Words.IWord;
import Words.Other;
import Words.Smiley;

public class ProcessedTweet {
	
	String sourceText;

	ArrayList<IWord> words;
	
	int numOfNegativeWords = 0;
	int numOfNeutralWords = 0;
	int numOfPositiveWords = 0;
	
	int sumOfNegativeWords = 0;
	int sumOfNeutralWords = 0;
	int sumOfPositiveWords = 0;
	
	public ProcessedTweet(String sourceText, ArrayList<IWord> words) {
		this.sourceText = sourceText;
		this.words = words;
		
		doSomeStatistics();
	}
	
	private void doSomeStatistics() {
		BigDecimal value;
		
		BigDecimal _175 = new BigDecimal(1.76);
		BigDecimal _2 = new BigDecimal(2);
		
		// class ranges (according to pleasantness)
		// <AFT>:
		//		[1, 1.75]	-> negative
		//		(1.75, 2.25)-> neutral
		//		[2, 3]		-> positive
		
		// <SML>:	
		//		[-1, 0)		-> negative
		//		0			-> neutral
		//		(0, 1]		-> positive
	
		for (IWord w : words) {
			double pleasantness = w.getPleasantness();
			value = new BigDecimal(pleasantness);
			
			if (w instanceof AffectionWord) {
				if (value.compareTo(_175) == -1) {
//					System.out.println("-> neg: " + w.getSourceWord());
					numOfNegativeWords++;
					sumOfNegativeWords += pleasantness;
				} else if (value.compareTo(_2) == -1) {
//					System.out.println("-> neu: " + w.getSourceWord());
					numOfNeutralWords++;
					sumOfNeutralWords += pleasantness;
				} else {
//					System.out.println("-> pos: " + w.getSourceWord());
					numOfPositiveWords++;
					sumOfPositiveWords += pleasantness;
				}
			}
			
			else if (w instanceof Smiley) {
				 if (pleasantness == 0){
//					System.out.println("-> neu: " + w.getSourceWord());
					numOfNeutralWords++;
					sumOfNeutralWords += pleasantness;
				}
				 else if (pleasantness < 0) {
//					System.out.println("-> neg: " + w.getSourceWord());
					numOfNegativeWords++;
					sumOfNegativeWords += pleasantness;
				} else if (pleasantness > 0) {
//					System.out.println("-> pos: " + w.getSourceWord());
					numOfPositiveWords++;
					sumOfPositiveWords += pleasantness;
				}
			}
			
			else if (w instanceof Other){
				numOfNeutralWords++;
			}
 		}
	}
	
	public String toString() {
		String s = "";
		s += "---------------------------\n";
		s += "Source tweet:\n" + sourceText + "\n\n";
		s += "Tree of tweet:\n";
		
		for (int i=0; i<words.size()-1; i++) {
			s += "\t|-> " + words.get(i).toString() + "\n";
		}
		s += "\t\\-> " + words.get(words.size()-1).toString() + "\n\n";
		
		s += "Some statistics:\n";
		s += "\t|-> Num of neg words: " + numOfNegativeWords + "\n";
		s += "\t|-> Num of neu words: " + numOfNeutralWords + "\n";
		s += "\t|-> Num of pos words: " + numOfPositiveWords + "\n";
		s += "\t|-> Sum of neg words: " + sumOfNegativeWords + "\n";
		s += "\t|-> Sum of neu words: " + sumOfNeutralWords + "\n";
		s += "\t\\-> Sum of pos words: " + sumOfPositiveWords + "\n";
			
		s += "---------------------------\n";
		
		return s;
	}
	
	// not needed anymore?
	public void print() {
		System.out.println("Tree of tweet:");
		System.out.println("\t|-> Source text: '" + sourceText + "'");
		System.out.println("\t|-> Tokens:");
		
		for (IWord w : words) {
			System.out.println("\t|\t|-> " + w.toString());
		}
		
		System.out.println("\t|-> Stats:");
		System.out.println("\t\t|-> Num of neg words: " + numOfNegativeWords);
		System.out.println("\t\t|-> Num of neu words: " + numOfNeutralWords);
		System.out.println("\t\t|-> Num of pos words: " + numOfPositiveWords);
		System.out.println("\t\t|-> Sum of neg words: " + sumOfNegativeWords);
		System.out.println("\t\t|-> Sum of neu words: " + sumOfNeutralWords);
		System.out.println("\t\t|-> Sum of pos words: " + sumOfPositiveWords);
	}
}
