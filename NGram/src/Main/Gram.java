package Main;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Gram {
	
	int indexOfLastWord;
	
	String gram;
	
	ArrayList<String> candidates = new ArrayList<String>(30);
	
	Hashtable<String, WordProbability> ht;
	
	public Gram(String gram, int indexOfLastWord) {
		this.gram = gram;
		this.indexOfLastWord = indexOfLastWord;
	}
	
	public void addCandidate(String s) {
		candidates.add(s);
	}
	
	public String toString() {
		return "['"+gram+"' -> "+indexOfLastWord+"]";
	}
	
	public String toStringFull() {
		String s = "['"+gram+"'";
		
		Set<String> keys = ht.keySet();
        for(String key: keys){
        	s += "\n\t-> " + ht.get(key).toString();
        }
		s += "]\n";
		
		return s;
	}
	
	public boolean compare(Gram g) {
		return gram.equals(g.gram);
	}

	public void calculateStatistics() {
		ht = new Hashtable<String, WordProbability>(candidates.size() / 2);
		
		for (int i=0; i<candidates.size(); i++) {
			String candidate = candidates.get(i);
			
			addNewCandidate(candidate);
		}
		
		ht.forEach((k, word) -> {
			word.probability = (double)word.occurences / ht.size();
		});
	}
	
	private void addNewCandidate(String candidate) {
		if (ht.containsKey(candidate)) {
			WordProbability duplicate = ht.get(candidate);
			duplicate.occurences++;
		} else {
			ht.put(candidate, new WordProbability(candidate));
		}
	}

}
