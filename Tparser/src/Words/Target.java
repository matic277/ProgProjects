package Words;

import java.util.Hashtable;
import Main.Main;

public class Target implements IWord {
	
	String sourceWord;
	String word;
	
	public Target(String source) {
		sourceWord = source;
		
		word = sourceWord.substring(1);
	}
	
	public static boolean isType(String s) {
		if (s.length() < 2) return false;
		if (s.charAt(0) == '@') return true;
		return false;
	}

	@Override
	public String getSourceWord() {
		return sourceWord;
	}

	@Override
	public String getTag() {
		return "<TRG>";
	}

	@Override
	public double getPleasantness() {
		if (Main.whissell.contains(word)) {
			return Main.whissell.getEntry(word).getPleasantness();
		}
		return -1;
	}

	@Override
	public double getActivation() {
		if (Main.whissell.contains(word)) {
			return Main.whissell.getEntry(word).getActivation();
		}
		return -1;
	}

	@Override
	public double getImagery() {
		if (Main.whissell.contains(word)) {
			return Main.whissell.getEntry(word).getImagery();
		}
		return -1;
	}
	
	public String toString() {
		return "[" + getTag() + ", '" + sourceWord + "', " + "P:" + getPleasantness() + "]";
	}

}
