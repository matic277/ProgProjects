import common.FileReader;

public class Main {
	
	public static void main(String[] args) {
		
		// ngram size
		int n = 2;
		long t;
		
		// https://sherlock-holm.es/stories/html/advs.html#Chapter-1
		String holeslong = "resources/holmes_5mb.txt";
		String holmesshort = "resources/holmes.txt";
		
		FileReader r = new FileReader(holeslong);
		

		NGram ng = new NGram(n, r.getWords(), true);
		ng.printCommon(5);
	}

}
