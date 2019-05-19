package Words;

import java.text.DecimalFormat;

import Dictionaries.DictionaryCollection;
import Dictionaries.IDictionary;
import Dictionaries.INode;

public class AffectionWord extends AbsMeasurableWord implements INode, IWord {
	
	String sourceText;
	
	public AffectionWord(String sourceText, double pleasantness, double activation, double imagery) {
		this.sourceText = sourceText;
		this.pleasantness = pleasantness;
		this.activation = activation;
		this.imagery = imagery;
	}
	
	public AffectionWord(String sourceText, String pleasantness, String activation, String imagery) {
		this.sourceText = sourceText;
		this.pleasantness = Double.parseDouble(pleasantness) - 2;
		this.activation = Double.parseDouble(activation) - 2;
		this.imagery = Double.parseDouble(imagery) - 2;
	}
	
	public AffectionWord(String sourceText) {
		this.sourceText = sourceText;
		
		IDictionary dictionary = DictionaryCollection.getDictionaryCollection().getWhissellDictionary();
		IWord word = dictionary.getEntry(sourceText);
		
		this.pleasantness = word.getPleasantness();
		this.activation = word.getActivation();
		this.imagery = word.getImagery();
	}

	public static boolean isType(String s) {
		return DictionaryCollection.getDictionaryCollection().getWhissellDictionary().contains(s);
	}
	
	public boolean checkIntegrity() {
		if (sourceText.length() > 1 && checkValidValue(pleasantness) && checkValidValue(activation) && checkValidValue(imagery)) {
			return true;
		}
		return false;
	}
	
	private boolean checkValidValue(double value) {
		if (value >= -1 && value <= 1) return true;
		return false;
	}

	@Override
	public String getString() {
		return sourceText;
	}
	
	@Override
	public void setPleasantness(double pleasantness) {
		this.pleasantness = pleasantness;
		if (this.pleasantness < -1) this.pleasantness = -1;
		else if (this.pleasantness > 1) this.pleasantness = 1;
	}
	
	@Override
	public void setFlipPleasantness() {
		this.pleasantness *= -1;
	}

	@Override
	public String getSourceText() {
		return sourceText;
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
	
	@Override
	public String toString() {
		DecimalFormat format = new DecimalFormat("#.###");
		return "[" + getTag() + ", '" + sourceText + "', P:" + format.format(pleasantness) + ", A:" + format.format(activation) + ", I:" + format.format(imagery) + "]";
	}
}
