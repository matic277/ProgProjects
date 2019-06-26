
public class Main {
	
	public static void main(String[] args) {
		int ngramSize = 3;
		
		long t1 = System.currentTimeMillis();
		Text t = new Text();
		
		System.out.println("\nTime spent pre-processing text: " + ((System.currentTimeMillis() - t1)/1000.0) + " s");
		
		t1 = System.currentTimeMillis();
		NGram ng = new NGram(ngramSize, t.words);
		
		System.out.println("\nTime spent processing "+ngramSize+"-grams: " + ((System.currentTimeMillis() - t1)/1000.0) + " s");
	}

}
