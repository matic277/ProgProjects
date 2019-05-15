package Main;

import java.util.ArrayList;

public class NGram {
	
	int n;
	
	String sourceText;
	String processedText;
	
	String[] wordSequence;
	
	public NGram(String sourceText, int ngramSize) {
		this.sourceText = sourceText;
		n = ngramSize;
		
		preprocessText();
		processngrams();
	}
	
	private void preprocessText() {
		processedText = sourceText.toLowerCase();
		
		String[] replacementChars = {
			".", ",", ";", ":",	"'", "!", "?"
		};
		
		for (String s : replacementChars) processedText = processedText.replace(s, "");
		
		wordSequence = processedText.split(" ");
		
		System.out.println(processedText);
	}

	private void processngrams() {
		
		ArrayList<Gram> grams = new ArrayList<Gram>(1000);
		
		for (int i=0; i<wordSequence.length-n; i++) {
			String gram = "";
			for (int j=0; j<n-1; j++) {
				gram += wordSequence[i+j] + " ";
			}
			gram += wordSequence[i+n-1];
			
			grams.add(new Gram(gram, i+n-1));
		}
		
		System.out.println(grams.get(grams.size()-1).toString());
		
		System.out.println("BEFORE COMBINING: " + grams.size());
		
		for (int i=0; i<grams.size(); i++) {
			for (int j=i; j<grams.size(); j++) {
				if (grams.get(i).compare(grams.get(j))) {
					grams.get(i).addCandidate(wordSequence[grams.get(j).indexOfLastWord+1]);
					grams.remove(j);
				}
			}
		}
		
		System.out.println("AFTER COMBINING: " + grams.size());
		
	}
	
	
	
	
	public void printSequence() {
		System.out.println("Sequence:");
		for (int i=0; i<wordSequence.length; i++) {
			System.out.print("("+i+")" + wordSequence[i] + " ");
		}
	}
}
