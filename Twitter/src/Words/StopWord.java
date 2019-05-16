package Words;

import Dictionaries.INode;

public class StopWord implements INode, IWord {
	
	String word = "";
	
	public StopWord(String source) {
		word = source;
	}

	@Override
	public boolean checkIntegrity() {
		return true;
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
		return "<STP>";
	}

	@Override
	public double getPleasantness() {
		return -1;
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
	public String toString() {
		return "[" + getTag() + ", '" + word + "']";
	}
}
