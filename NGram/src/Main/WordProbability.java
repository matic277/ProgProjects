package Main;

public class WordProbability {
	
	String word;
	double probability;
	int occurences = 1;
	
	public WordProbability(String word) {
		this.word = word;
	}
	
	public String toString() {
		return "['" + word + "' -> " + probability + "]";
	}

}
