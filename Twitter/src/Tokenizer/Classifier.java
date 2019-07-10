package Tokenizer;

public class Classifier {
	
	Tweet tweet;
	String sourceText;
	
	public Classifier(String sourceText) {
		this.sourceText = sourceText;
	}
	
	public int getSentiment() {
		tweet = new Tweet(sourceText, "{unknown}");
		tweet.processTweet();
		return tweet.getSentiment();
	}

}
