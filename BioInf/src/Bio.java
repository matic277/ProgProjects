import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.DocFlavor.STRING;

public class Bio {
	
	public static boolean isStopCodon(String codon) {
		return codon.equals("UAG")
			 || codon.equals("UAA");
			 //|| codon.equals("UGA");
	}
	
	public static boolean isStopCodon(char codon) {
		return codon == 'Z';
			 //|| codon.equals("UGA");
	}
	
	public static boolean isStartCodon(String codon) {
		return codon.equals("AUG");	
	}
	
	public static boolean isStartCodon(char codon) {
		return codon == 'M';	
	}
	
	public static String dnaToRna(String seq) {
//		HashMap<Character, Character> map = new HashMap<Character, Character>(8);
//		map.put('A', 'U'); map.put('T', 'A');
//		map.put('C', 'G'); map.put('G', 'C');
//		StringBuilder sb = new StringBuilder();
//		for (int i=0; i<seq.length(); i++) {
//			sb.append(map.get(seq.charAt(i)));
//		}
//		return sb.toString();
		return seq.replace("T", "U");
	}
	
	public static String applyCodonTable(String seq) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<seq.length()-2; ) {
			String codon = "" + seq.charAt(i++) + seq.charAt(i++) + seq.charAt(i++);
			sb.append(codonTable.get(codon));
		}
		return sb.toString();
	}
	
	public static String getReverseComplement(String seq) {
		HashMap<Character, Character> map = new HashMap<Character, Character>(8);
		map.put('A', 'T'); map.put('T', 'A');
		map.put('C', 'G'); map.put('G', 'C');
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<seq.length(); i++) {
			sb.append(map.get(seq.charAt(i)));
		}
		return sb.reverse().toString();
	}
	
	private static String[] getAllReadingFrames(String seq) {
		String c = getReverseComplement(seq);
		
		String s1 = applyCodonTable(dnaToRna(seq.substring(0, seq.length())));
		String s2 = applyCodonTable(dnaToRna(seq.substring(1, seq.length())));
		String s3 = applyCodonTable(dnaToRna(seq.substring(2, seq.length())));

		String s4 = applyCodonTable(dnaToRna(c.substring(0, c.length())));
		String s5 = applyCodonTable(dnaToRna(c.substring(1, c.length())));
		String s6 = applyCodonTable(dnaToRna(c.substring(2, c.length())));
		
//		System.out.println(s1.length());
//		System.out.println(s2.length());
//		System.out.println(s3.length());
//		System.out.println(s4.length());
//		System.out.println(s5.length());
//		System.out.println(s6.length());
		
		return new String[] {
			s1, s2, s3,
			s4, s5, s6
		};
	}
	
	public static List<String> getORFs(String seq) {
		String[] frames = getAllReadingFrames(seq);
		List<String> orfs = new LinkedList<String>();
		
		for (int i=0; i<frames.length; i++) {
//			System.out.println(frames[i]);
			orfs.addAll(getORFhelper(frames[i]));
		}

		orfs = orfs.stream().distinct().collect(Collectors.toList());
		return orfs;
	}
	
	static ArrayList<Protein> orfs2 = new ArrayList<Protein>();
	
	public static List<String> getORFhelper(String seq) {
		StringBuilder sb = new StringBuilder();
		List<String> orfs = new LinkedList<String>();
		
		loop:
		for (int i=0; i<seq.length(); i++) {
				int j = i;
				if (isStartCodon(seq.charAt(i))) {
				while (!isStopCodon(seq.charAt(j))) {
					if (j == seq.length()-1) {
						break loop;
					}
					sb.append("" + seq.charAt(j));
					j++;
				}
				orfs.add(sb.toString());
				orfs2.add(new Protein(sb.toString(), i, j));
				sb.setLength(0);
			}
		}
		
		return orfs;
	}
	
	public static BigDecimal[][] getDistanceMatrix(ArrayList<String> seqs) {
		BigDecimal[][] distances = new BigDecimal[seqs.size()][seqs.size()];
		for (int i=0; i<seqs.size(); i++)  {
			for (int j=0; j<seqs.size(); j++)
				distances[i][j] = new BigDecimal("0");
		}
		for (int i=0; i<seqs.size(); i++) {
			for (int j=i+1; j<seqs.size(); j++) {
				distances[i][j] = getPdistance(seqs.get(i), seqs.get(j));
//				System.out.println(distances[i][j]);
			}
		}
		for (int i=0; i<distances.length; i++) {
			for (int j=0; j<i; j++) {
				distances[i][j] = distances[j][i];
			}
		}
		return distances;
	}
	
	public static BigDecimal getPdistance(String s1, String s2) {
		int matches = 0;
		for (int i=0; i<s1.length(); i++) {
			if (s1.charAt(i) == s2.charAt(i))
				matches++;
		}
		return new BigDecimal(1)
				.subtract(new BigDecimal(matches)
						.divide(new BigDecimal(s1.length()), MathContext.DECIMAL32));
	}
	
    public static int hammingDistance(String seq1, String seq2) {
        int c = 0;
        for (int i = 0; i < seq1.length(); i++) {
            if (seq1.charAt(i) != seq2.charAt(i)) c++;
        }
        return c;
    }
    
    public static List<String> locateRestrictionSites_Palindromes(String seq) {
    	List<String> results = new LinkedList<String>();
    	//List<String> candidates = new LinkedList<String>();
    	// list of all possible substrings of length
    	// between 4 and 12
    	for (int e=4; e<13; e++) {
    		for (int s=0; (s+e)<=seq.length(); s++) {
    			String candidate = seq.substring(s, s+e);
    			if (candidate.equals(getReverseComplement(candidate)))
    				results.add((s+1) + " " + e);
    		}
    	}
    	//results.forEach(System.out::println);
    	return results;
    }
    
    private static boolean isTransition(char c1, char c2) {
    	return ((c1 == 'A' || c1 == 'G') && (c2 == 'A' || c2 == 'G')) ||
    			((c1 == 'C' || c1 == 'T') && (c2 == 'C' || c2 == 'T'));
    }
    public static double transitionsAndTransversions(String seq1, String seq2) {
    	double transition = 0, transversion = 0;
    	for (int i=0; i<seq1.length(); i++) {
    		if (seq1.charAt(i) != seq2.charAt(i))
	    		if (isTransition(seq1.charAt(i), seq2.charAt(i)))
	    			transition++;
	    		else transversion++;
    	}
    	return transition / transversion;
    }
	
	static HashMap<String, String> codonTable;
	static {
		// not true anymore
		// encode Stop as X
		// UGA (or TGA) is Z (non-stop codon in this case)
		
		// Z is stop codon
		codonTable = new HashMap<String, String>();
		codonTable.put("UUU", "F");   
		codonTable.put("CUU", "L");
		codonTable.put("AUU", "I");      
		codonTable.put("GUU", "V");
		codonTable.put("UUC", "F");   
		codonTable.put("CUC", "L");
		codonTable.put("AUC", "I");      
		codonTable.put("GUC", "V");
		codonTable.put("UUA", "L");   
		codonTable.put("CUA", "L");
		codonTable.put("AUA", "I");      
		codonTable.put("GUA", "V");
		codonTable.put("UUG", "L");   
		codonTable.put("CUG", "L");
		codonTable.put("AUG", "M");      
		codonTable.put("GUG", "V");
		codonTable.put("UCU", "S");   
		codonTable.put("CCU", "P");
		codonTable.put("ACU", "T");      
		codonTable.put("GCU", "A");
		codonTable.put("UCC", "S");   
		codonTable.put("CCC", "P");
		codonTable.put("ACC", "T");      
		codonTable.put("GCC", "A");
		codonTable.put("UCA", "S");   
		codonTable.put("CCA", "P");
		codonTable.put("ACA", "T");      
		codonTable.put("GCA", "A");
		codonTable.put("UCG", "S");   
		codonTable.put("CCG", "P");
		codonTable.put("ACG", "T");      
		codonTable.put("GCG", "A");
		codonTable.put("UAU", "Y");   
		codonTable.put("CAU", "H");
		codonTable.put("AAU", "N");      
		codonTable.put("GAU", "D");
		codonTable.put("UAC", "Y");   
		codonTable.put("CAC", "H");
		codonTable.put("AAC", "N");      
		codonTable.put("GAC", "D");
		codonTable.put("UAA", "Z");
		codonTable.put("CAA", "Q");
		codonTable.put("AAA", "K");      
		codonTable.put("GAA", "E");
		codonTable.put("UAG", "Z");
		codonTable.put("CAG", "Q");
		codonTable.put("AAG", "K");      
		codonTable.put("GAG", "E");
		codonTable.put("UGU", "C");   
		codonTable.put("CGU", "R");
		codonTable.put("AGU", "S");     
		codonTable.put("GGU", "G");
		codonTable.put("UGC", "C");   
		codonTable.put("CGC", "R");
		codonTable.put("AGC", "S");      
		codonTable.put("GGC", "G");
		codonTable.put("UGA", "Z");
		codonTable.put("CGA", "R");
		codonTable.put("AGA", "R");      
		codonTable.put("GGA", "G");
		codonTable.put("UGG", "W");   
		codonTable.put("CGG", "R");
		codonTable.put("AGG", "R");      
		codonTable.put("GGG", "G");
	}

}
