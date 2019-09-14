package single;
import common.FileReader;

public class Main {
	
	public static void main(String[] args) {
		
		// ngram size
		int n = 2;
		long t;

		
		FileReader r = new FileReader(FileReader.holmesshort);
		

		NGram ng = new NGram(n, r.getWords(), true);
		ng.printCommon(5);
	}

}
