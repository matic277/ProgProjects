import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<String> seqs = new ArrayList<String>();
		seqs.add("TTTCCATTTA");
		seqs.add("GATTCATTTC");
		seqs.add("TTTCCATTTT");
		seqs.add("GTTCCATTTA");
		
		BigDecimal[][] r = Bio.getDistanceMatrix(seqs);
		
		for (int i=0; i<seqs.size(); i++) {
			for (int j=0; j<seqs.size(); j++) {
				System.out.print(r[i][j].toPlainString() + " ");
			}
			System.out.println();
		}
		
		
//		String seq = "AGCCATGTAGCTAACTCAGGTTACATGGGGATGACCCCGCGACTTGGATTAGAGTCTCTTTTGGAATAAGCCTGAATGATCCGAGTAGCATCTCAG";
//		
//		List<String> res = Bio.getORFs(seq);
//		res.forEach(System.out::println);
//		
//		System.out.println("done, size: " + res.size());
	}

}
