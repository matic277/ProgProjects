package Words;

import Dictionaries.INode;

public class AffectionWord implements INode, IWord {
	
	String word = "";
	
	// for all: [1, 3]
	double pleasantness = 0;	// 1 (unpleasant) to 3 (pleasant)
	double activation = 0;		// 1 (passive) to 3 (active)
	double imagery = 0;			// 1 (difficult to form a mental picture of this word) to 3 (easy to form a mental picture)
	
	public AffectionWord(String word, double pleasantness, double activation, double imagery) {
		this.word = word;
		this.pleasantness = pleasantness;
		this.activation = activation;
		this.imagery = imagery;
	}
	
	public AffectionWord(String word, String pleasantness, String activation, String imagery) {
		this.word = word;
		this.pleasantness = Double.parseDouble(pleasantness);
		this.activation = Double.parseDouble(activation);
		this.imagery = Double.parseDouble(imagery);
	}
	
	public boolean checkIntegrity() {
		if (word.length() > 1 && checkValidValue(pleasantness) && checkValidValue(activation) && checkValidValue(imagery)) {
			return true;
		}
		return false;
	}
	
	private boolean checkValidValue(double value) {
		if (value >= 1 && value <= 3) return true;
		return false;
	}

	@Override
	public String getString() {
		return word;
	}

	@Override
	public String getSourceWord() {
		return word;
	}

	@Override
	public String getTag() {
		return "<AFT>";
	}

	@Override
	public double getPleasantness() {
		return pleasantness;
	}

	@Override
	public double getActivation() {
		return activation;
	}

	@Override
	public double getImagery() {
		return imagery;
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + word + "', P:" + pleasantness + ", A:" + activation + ", I:" + imagery + "]";
	}
}
