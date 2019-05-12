package Words;

import Dictionaries.INode;
import Main.Main;

public class Acronym implements IWord, INode {
	
	String sourceText;
	String fullText;

	@Override
	public String getSourceWord() {
		return sourceText;
	}

	@Override
	public String getTag() {
		return "<ACR>";
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
	public boolean checkIntegrity() {
		if (sourceText.length() > 1) return true;
		return false;
	}

	@Override
	public String getString() {
		return sourceText;
	}
	
	public String toString() {
		return "[" + getTag() + ", " + "'"+sourceText+"' -> " + "'" + fullText + "'" + ", " + getPleasantness() + "]";
	}

}
