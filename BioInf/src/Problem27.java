import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Problem27 {
	
	public static void main(String[] args) throws IOException {
		new HMM();
	}
	
}

class HMM {
	
	String path;
	String[] states;
	double[][] matrix;

	HashMap<String, Double> transitions;
	
	public HMM() {
		path = "AABBBAABABAAAABBBBAABBABABBBAABBAAAABABAABBABABBAB";
//		states = new String[]{"A", "B"};
//		matrix = new double[][]{
//			{0.194, 0.806},
//			{0.273, 0.727}
//		};
		transitions = new HashMap<String, Double>();
		transitions.put("AA", 0.194); transitions.put("AB", 0.896);
		transitions.put("BA", 0.273); transitions.put("BB", 0.727);
		
		process();
	}
	
	public void process() {
		BigDecimal prob = new BigDecimal("0.5");
		double p = Math.log10(0.5);
		for (int i=0; i<path.length()-1; i++) {
			String transition = "" + path.charAt(i) + path.charAt(i+1);
			System.out.println(transition + " -> " + transitions.get(transition));
//			prob = prob.multiply(new BigDecimal("" + transitions.get(transition)));
			double t = transitions.get(transition);
			p *= Math.log10(t);
		}
		System.out.println(prob.toString());
		
		System.out.println(p);
	}
	
}

class Node {
	
}
