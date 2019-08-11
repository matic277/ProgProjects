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
		
//		ArrayList<AbsWord> words = tweet.getTokens();
//		double otherWords = 0;
//		for (int i=0; i<words.size(); i++) {
//			if (words.get(i) instanceof Other) {
//				otherWords++;
//			}
//		}
//		if (otherWords / words.size() >= 0.5) {
//			System.out.println("returning -2; out of "+words.size()+", there are "+otherWords+" Other words, thats: " + (otherWords/words.size()));
//			return -2;
//		}
		
		return tweet.getSentiment();
	}

}
