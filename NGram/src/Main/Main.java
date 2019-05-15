package Main;

public class Main {

	public static void main(String[] args) {
		String text = Text.text2;
		int n = 3;
		
		Long t1 = System.currentTimeMillis();
		
		NGram ng = new NGram(text, n); 
		
		Long t2 = System.currentTimeMillis();
		
		System.out.println("Time taken to process text (ngram = "+n+"):" + ((t2-t1)/1000) + "ms");
	}

}
