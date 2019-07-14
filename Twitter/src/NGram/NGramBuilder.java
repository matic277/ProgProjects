package NGram;

import java.util.ArrayList;
import java.util.HashMap;

import AbstractWordClasses.AbsWord;
import Tokenizer.Tweet;
import Words.Emoji;
import Words.Target;
import Words.URL;

public class NGramBuilder {
	
	int n;
	
	ArrayList<String> positiveTweets;
	ArrayList<String> negativeTweets;
	ArrayList<String> neutralTweets;
	
	HashMap<String, Gram> positiveTable;
	HashMap<String, Gram> negativeTable;
	HashMap<String, Gram> neutralTable;
	
	final int mostFrequentListSize = 10;
	
	ArrayList<Gram> frequentPositive;
	ArrayList<Gram> frequentNeutral;
	ArrayList<Gram> frequentNegative;
	
	public NGramBuilder(int n) {
		this.n = n;
		DatasetReader reader = new DatasetReader();
		positiveTweets = reader.positiveTweets;
		negativeTweets = reader.negativeTweets;
		neutralTweets = reader.neutralTweets;
		
		positiveTable = buildNGrammableTweetString(positiveTweets);
		negativeTable = buildNGrammableTweetString(negativeTweets);
		neutralTable = buildNGrammableTweetString(neutralTweets);
		
		frequentPositive = getFrequentNGrams(positiveTable);
		frequentNeutral = getFrequentNGrams(negativeTable);
		frequentNegative = getFrequentNGrams(neutralTable);
		
		print(frequentPositive, positiveTable, "positive");
		print(frequentNeutral, negativeTable, "neutral");
		print(frequentNegative, neutralTable, "negative");
	}

	private ArrayList<Gram> getFrequentNGrams(HashMap<String, Gram> table) {
		ArrayList<Gram> listAll;// = new ArrayList<Gram>(table.size());
		ArrayList<Gram> listFrequent = new ArrayList<Gram>(mostFrequentListSize);
		
		listAll = new ArrayList<Gram>(table.values());
		listAll.sort((g1, g2) -> {
			return Double.compare(g2.occurrences, g1.occurrences);
		});
		for (int i=0; i<listAll.size() && i<mostFrequentListSize ; i++) {
			listFrequent.add(listAll.get(i));
		}
		return listFrequent;
	}
	
	public HashMap<String, Gram> buildNGrammableTweetString(ArrayList<String> dirtyTweets) {
		HashMap<String, Gram>[] tables = new HashMap[dirtyTweets.size()];
		
		for (int i=0; i<dirtyTweets.size(); i++) {
			String dirtyTweet = dirtyTweets.get(i);
			Tweet tweet = new Tweet(dirtyTweet, null);
			tweet.processTweet();
			ArrayList<AbsWord> words = tweet.getTokens();
			ArrayList<String> NGrammableList = new ArrayList<String>(words.size());
			
			words.forEach(w -> {
				if (w instanceof Emoji) return;
				if (w instanceof URL) return;
				if (w instanceof Target) return;
				NGrammableList.add((w.getProcessedText() == null)? w.getSourceText() : w.getProcessedText());
			});
			
			NGram currentNGram = new NGram(n, NGrammableList);
			tables[i] = currentNGram.table;
		}
		
		// combine all tables into table[0]
		// if two grams need to be combined
		// sum up their *occurrences* property
		for (int i=1; i<tables.length; i++) {
			tables[i].forEach((k, v) -> {
				tables[0].merge(k, v, (g1, g2) -> { g1.occurrences += g2.occurrences; return g1; });
			});
			// release some memory by setting all other hashmaps to null?
			tables[i] = null;
		}
		return tables[0];
	}
	
	private void print(ArrayList<Gram> list, HashMap<String, Gram> table, String info) {
		System.out.println("Top "+mostFrequentListSize+" "+n+"-grams from "+info+" tweets:");
		list.forEach(g -> System.out.println("\t|-> " + g.toString()));
		System.out.println("\t|");
		System.out.println("\t|-> Stats:");
		System.out.println("\t|\t|-> Number of n-grams:" + table.size());
		System.out.println();
	}

}
