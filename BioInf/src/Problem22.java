

import jaligner.Alignment;
import jaligner.Sequence;
import jaligner.SmithWatermanGotoh;
import jaligner.formats.Pair;
import jaligner.matrix.Matrix;
import jaligner.matrix.MatrixLoader;

public class Problem22 {
	
	public static void main(String[] args) {
//		Sequence s1 = new Sequence("PRETTY");
//		Sequence s2 = new Sequence("PRTTEIN");
//		SmithWatermanGotoh.align(s1, s2, Matrix., arg3, arg4);
//		
//		Matrix m = new Matrix();
		
        try {
//        	logger.info("Running example...");
        	
    		Sequence s1 = new Sequence("PRTEINS");
    		Sequence s2 = new Sequence("PRTWPSEIN");
        
	        Alignment alignment = SmithWatermanGotoh.align(s1, s2, MatrixLoader.load("BLOSUM62"), 10f, 0.5f);
	        
	        System.out.println (alignment.getSummary());
	        System.out.println (new Pair().format(alignment));

        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

}
