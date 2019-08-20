package ngramStatistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import AbstractWordClasses.AbsWord;
import Tokenizer.Tweet;
import Words.Emoji;
import Words.Hashtag;
import Words.Target;
import Words.URL;
import Dictionaries.*;

public class NGramBuilder {
	
	int n;
	
	ArrayList<String> positiveTweets;
	ArrayList<String> negativeTweets;
	//ArrayList<String> neutralTweets;
	
	HashMap<String, Gram> positiveTable;
	HashMap<String, Gram> negativeTable;
//	HashMap<String, Gram> neutralTable;
	
	final int printingSize = 10;	// how many lines to print when printing frequent ngrams, -1 prints all
	
	ArrayList<Gram> sortedPositive;
//	ArrayList<Gram> sortedNeutral;
	ArrayList<Gram> sortedNegative;
	
	ArrayList<Gram> uniqueToPositive;
//	ArrayList<Gram> uniqueToNeutral;
	ArrayList<Gram> uniqueToNegative;
	
	public NGramBuilder(int n) {
		this.n = n;
		
		DatasetReader reader = new DatasetReader();
		positiveTweets = reader.positiveTweets;
		negativeTweets = reader.negativeTweets;
//		neutralTweets = reader.neutralTweets;
		
		positiveTable = buildNGramTable(positiveTweets);
		negativeTable = buildNGramTable(negativeTweets);
//		neutralTable = buildNGramTable(neutralTweets);
		
		sortedPositive = getFrequentNGrams(positiveTable);
//		sortedNeutral = getFrequentNGrams(neutralTable);
		sortedNegative = getFrequentNGrams(negativeTable);
		
		ArrayList<Gram> stopGramFreeSet_positive = excludeStopNgrams(sortedPositive);
//		ArrayList<Gram> stopGramFreeSet_neutral = excludeStopNgrams(sortedNeutral);
		ArrayList<Gram> stopGramFreeSet_negative = excludeStopNgrams(sortedNegative);
		
		print2(stopGramFreeSet_positive, "stop-gram-free positive");
//		print2(stopGramFreeSet_neutral, "stop-gram-free neutral");
		print2(stopGramFreeSet_negative, "stop-gram-free negative");
		
//		print(sortedPositive, positiveTable, "positive");
//		print(sortedNeutral, neutralTable, "neutral");
//		print(sortedNegative, negativeTable, "negative");
		
		populateDatasetUniqueNGrams(
			stopGramFreeSet_positive,
//			stopGramFreeSet_neutral,
			stopGramFreeSet_negative
		);

		print2(uniqueToPositive, "stop-gram-free unique to positive");
//		print2(uniqueToNeutral, "stop-gram-free unique to neutral");
		print2(uniqueToNegative, "stop-gram-free unique to negative");
		
		
		
		
		
		DatasetWriter writer = new DatasetWriter();
		try {
			writer.writeFile(uniqueToPositive, "datasets/ngrams2/OrderedUniquePositive_trigrams.txt");
//			writer.writeFile(uniqueToNeutral, "datasets/ngrams/OrderedUniqueNeutral_unigrams.txt");
			writer.writeFile(uniqueToNegative, "datasets/ngrams2/OrderedUniqueNegative_trigrams.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private ArrayList<Gram> excludeStopNgrams(ArrayList<Gram> set) {
		ArrayList<Gram> subSet = new ArrayList<Gram>(set.size());
		
		IDictionary negationDictionary = DictionaryCollection.getDictionaryCollection().getNegationwordDictionary();
		IDictionary stopDictionary = DictionaryCollection.getDictionaryCollection().getStopwordDictionary();
		
		double percentageThreshold = 0.5;
		
		// if an ngram contains more than *percentageThreshold*
		// stop-words or negation-words, then don't add those
		// to the subset
		set.forEach(gram -> {
			double percentage = 0;
			int occurences = 0;
			String[] tokens = gram.ngram.split(" ");
			
			for (int i=0; i<tokens.length; i++) {
				if (negationDictionary.contains(tokens[i]) || stopDictionary.contains(tokens[i])) 
					occurences++;
			}
			percentage = (double) occurences / tokens.length;
			
			if (percentage <= percentageThreshold) subSet.add(gram);
			
		});
		DecimalFormat format = new DecimalFormat("##.##");
		String percentageSmaller = format.format(100-100*((double)subSet.size() / set.size()));
		System.out.println("Removing stop-grams, subset shrank for " + percentageSmaller +
				"%, from " + set.size() + " to " + subSet.size() + ".\n");
		return subSet;
	}
	
	private void populateDatasetUniqueNGrams(ArrayList<Gram> positive, /*ArrayList<Gram> neutral,*/ ArrayList<Gram> negative) {
		uniqueToPositive = new ArrayList<Gram>(positive.size());
//		uniqueToNeutral = new ArrayList<Gram>(neutral.size());
		uniqueToNegative = new ArrayList<Gram>(negative.size());
		// check against entries in hash-tables
		positive.forEach(gram -> {
			if (/*!neutralTable.containsKey(gram.ngram) && */!negativeTable.containsKey(gram.ngram))
				uniqueToPositive.add(gram);
		});
//		neutral.forEach(gram -> {
//			if (!positiveTable.containsKey(gram.ngram) && !negativeTable.containsKey(gram.ngram))
//				uniqueToNeutral.add(gram);
//		});
		negative.forEach(gram -> {
			if (/*!neutralTable.containsKey(gram.ngram) &&*/ !positiveTable.containsKey(gram.ngram))
				uniqueToNegative.add(gram);
		});
		DecimalFormat format = new DecimalFormat("#.##");
		System.out.println("Unique-to-positive-set is " + 
				format.format(100-100*((double)uniqueToPositive.size() / positive.size())) +
				"%, smaller than positive-set, from " + positive.size() + " to " + uniqueToPositive.size() + ".\n");
//		System.out.println("Unique-to-neutral-set is " + 
//				format.format(100-100*((double)uniqueToNeutral.size() / neutral.size())) +
//				"%, smaller than neutral-set, from " + neutral.size() + " to " + uniqueToNeutral.size() + ".\n");
		System.out.println("Unique-to-negative-set is " + 
				format.format(100-100*((double)uniqueToNegative.size() / negative.size())) +
				"%, smaller than negative-set, from " + negative.size() + " to " + uniqueToNegative.size() + ".\n");
	}

	private ArrayList<Gram> getFrequentNGrams(HashMap<String, Gram> table) {
		ArrayList<Gram> listFrequent = new ArrayList<Gram>(table.size());
		listFrequent = new ArrayList<Gram>(table.values());
		listFrequent.sort((g1, g2) -> {
			return Double.compare(g2.occurrences, g1.occurrences);
		});
		return listFrequent;
	}
	
	public HashMap<String, Gram> buildNGramTable(ArrayList<String> dirtyTweets) {
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
				if (w instanceof Hashtag) return;
				NGrammableList.add((w.getProcessedText() == null || w.getProcessedText().length() == 0)? 
						w.getSourceText() : w.getProcessedText());
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
		}
		
		// recalculate probabilities
		int totalNGrams = 0;
		for (Gram g : tables[0].values()) totalNGrams += g.occurrences;
		final int totalNGrams_ = totalNGrams;
		tables[0].forEach((s, g) -> g.probability = (g.occurrences / totalNGrams_) * 100);
		
		return tables[0];
	}
	
	private void printList(ArrayList<String> list) {
		System.out.print("List of words: $");
		for (int i=0; i<list.size()-1; i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.print(list.get(list.size()-1) + "$");
	}
	
	
	private void print(ArrayList<Gram> list, HashMap<String, Gram> table, String info) {
		int listSize = (printingSize == -1)? list.size() : printingSize;
		
		int totalNGrams = 0;
		for (Gram g : table.values()) totalNGrams += g.occurrences;
		
		System.out.println("Top "+printingSize+" "+n+"-grams from "+info+" tweets:");
		
		for (int i=0; i<listSize; i++) System.out.println("\t|-> " + list.get(i).toString());
//		list.forEach(g -> System.out.println("\t|-> " + g.toString()));
		
		System.out.println("\t|");
		System.out.println("\t|-> Stats:");
		System.out.println("\t\t|-> Number of unique n-grams:" + table.size());
		System.out.println("\t\t|-> Number of n-grams:" + totalNGrams);
		System.out.println();
	}
	
	private void print2(ArrayList<Gram> list, String info) {
		int listSize = (printingSize == -1)? list.size() : printingSize;
		
		System.out.println("Top "+printingSize+" "+n+"-grams from "+info+" tweets:");
		
		for (int i=0; i<listSize; i++) System.out.println("\t|-> " + list.get(i).toString());
//		list.forEach(g -> System.out.println("\t|-> " + g.toString()));

		System.out.println();
	}

}
