package AbstractWordClasses;

public abstract class AbsWord {
	
	protected String sourceText;
	protected String processedText;
	
	protected String tag;
	
	public AbsWord(String source, String processed) {
		this.sourceText = source;
		this.processedText = processed;
	}
	
	public String getTag() {
		return "<" + tag + ">";
	}
	
	public String toString() {
		return "[" + getTag() + ", src:'" + sourceText + "', prc:'" + processedText + "']";
	}
	public void setSourceText(String newStr) { this.sourceText = newStr; }
	public void setProcessedText(String newStr) { this.sourceText = newStr; }
	public String getSourceText() { return this.sourceText; }
	public String getProcessedText() { return this.processedText; }
}
