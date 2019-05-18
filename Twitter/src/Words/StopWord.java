package Words;

import Dictionaries.INode;

public class StopWord implements INode, IWord {
	
	String sourceText = "";
	
	public StopWord(String sourceText) {
		this.sourceText = sourceText;
	}

	@Override
	public boolean checkIntegrity() {
		return true;
	}

	@Override
	public String getString() {
		return sourceText;
	}

	@Override
	public String getSourceText() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<STP>";
	}
	
	@Override
	public void setPleasantness(double pleasantness) { }
	
	@Override
	public void setFlipPleasantness() { }

	@Override
	public double getPleasantness() {
		return -2;
	}

	@Override
	public double getActivation() {
		return -2;
	}

	@Override
	public double getImagery() {
		return -2;
	}
	
	@Override
	public String toString() {
		return "[" + getTag() + ", '" + sourceText + "']";
	}
}
