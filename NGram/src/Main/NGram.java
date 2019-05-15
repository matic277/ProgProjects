package Main;

import java.util.ArrayList;
import java.util.Hashtable;

public class NGram {
	
	int n;
	
	String sourceText;
	String processedText;
	
	String[] wordSequence;
	
	Hashtable<String, Gram> ht;
	
	public NGram(String sourceText, int ngramSize) {
		this.sourceText = sourceText;
		n = ngramSize;
		
		preprocessText();
		//processngrams();
		populateTable();
		processTable();
		
		System.out.println("HT SIZE: " + ht.size());
		
		System.out.println();
		System.out.println("STATISTICS: ");
		ht.forEach((k, gram) -> {
			if (gram.ht.size() > 2)
				System.out.println(gram.toStringFull());
		});
	}
	
	public void populateTable() {
		ht = new Hashtable<String, Gram>(wordSequence.length);
		
		for (int i=0; i<wordSequence.length-n; i++) {
			String gram = "";
			for (int j=0; j<n-1; j++) {
				gram += wordSequence[i+j] + " ";
			}
			gram += wordSequence[i+n-1];
			
			Gram g = new Gram(gram, i+n-1);
			g.addCandidate(wordSequence[i+n]);
			
			addNewGram(g);
		}
	}
	
	private void processTable() {
		ht.forEach((k, gram) -> {
			gram.calculateStatistics();
		});
	}
	
	private void addNewGram(Gram g) {
		if (ht.containsKey(g.gram)) {
			Gram duplicate = ht.get(g.gram);
			duplicate.addCandidate(g.candidates.get(0));
		} else {
			ht.put(g.gram, g);
		}
	}
	
	private void preprocessText() {
		processedText = sourceText.toLowerCase();
		
		String[] replacementChars = {
			".", ",", ";", ":",	"'", "!",
			"?", "\r", "\"", "/", "*",
			"(", ")", "[", "]", "{", "}"
		};
		
		for (String s : replacementChars) processedText = processedText.replace(s, "");
		
		processedText = processedText.replace("\n", " ");
		wordSequence = processedText.split(" ");
		
		//System.out.println(processedText);
	}

	public void printSequence() {
		System.out.println("Sequence:");
		for (int i=0; i<wordSequence.length; i++) {
			System.out.print("("+i+")" + wordSequence[i] + " ");
		}
	}
}
