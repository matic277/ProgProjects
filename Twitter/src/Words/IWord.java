package Words;

public interface IWord {
	
	String getSourceText();
	String getTag();
	double getActivation();
	double getImagery();
	double getPleasantness();
	
	void setPleasantness(double pleasantness);
	void setFlipPleasantness();
	
}
