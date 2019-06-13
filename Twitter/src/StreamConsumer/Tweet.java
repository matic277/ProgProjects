package StreamConsumer;

public class Tweet {
	
	public String cleanSource; // gets defined by Tokenizer
	public String sourceText;
	public String username;
	
	public Tweet(String sourceStatus, String username) {
		this.sourceText = sourceStatus;
		this.username = username;
	}
}
