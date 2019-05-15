package Main;

import java.util.ArrayList;

public class Gram {
	
	int indexOfLastWord;
	
	String gram;
	
	ArrayList<String> candidates = new ArrayList<String>(10);
	
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
	
	public boolean compare(Gram g) {
		return gram.equals(g.gram);
	}

}
