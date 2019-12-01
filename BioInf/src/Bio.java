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
			 || codon.equals("UAA")
			 || codon.equals("UGA");
	}
	
	public static boolean isStartCodon(String codon) {
		return codon.equals("AUG");		
	}
	
	public static String dnaToRna(String seq) {
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
	
	public static String[] getAllReadingFrames(String seq) {
		String[] frames = new String[6];
		
		String c = getReverseComplement(seq);
		
		String s1 = dnaToRna(seq.substring(0, seq.length()));
		String s2 = dnaToRna(seq.substring(1, seq.length()));
		String s3 = dnaToRna(seq.substring(2, seq.length()));
		
		String s4 = dnaToRna(c.substring(0, c.length()));
		String s5 = dnaToRna(c.substring(1, c.length()));
		String s6 = dnaToRna(c.substring(2, c.length()));
		
		frames[0] = s1;
		frames[1] = s2;
		frames[2] = s3;
		frames[3] = s4;
		frames[4] = s5;
		frames[5] = s6;
		
		return frames;
	}
	
	public static List<String> getORFs(String seq) {
		String[] frames = getAllReadingFrames(seq);
		List<String> orfs = new LinkedList<String>();
		
		for (int i=0; i<frames.length; i++) {
			System.out.println(frames[i]);
			orfs.addAll(getORFhelper(frames[i]));
		}
		orfs = orfs.stream().distinct().collect(Collectors.toList());
		return orfs;
	}
	
	public static List<String> getORFhelper(String seq) {
		StringBuilder sb = new StringBuilder();
		List<String> orfs = new LinkedList<String>();
		boolean appending = false;
		for (int i=0; i<seq.length()-2; ) {
			String codon = "" + seq.charAt(i++) + seq.charAt(i++) + seq.charAt(i++);
			System.out.print(codon + " ");
			if (appending) {
				if (isStopCodon(codon)) {
					appending = false;
					orfs.add(sb.toString());
					sb.setLength(0);
				} else {
					sb.append(codon);
				}
			} else {
				if (isStartCodon(codon)) {
					appending = true;
					sb.append(codon);
				}
			}
		}
		System.out.println();
		if (appending) orfs.add(sb.toString());
		return orfs.stream().map(Bio::applyCodonTable).collect(Collectors.toList());
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
				System.out.println(distances[i][j]);
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
	
	static HashMap<String, String> codonTable;
	static {
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
		codonTable.put("UAA", "Stop");
		codonTable.put("CAA", "Q");
		codonTable.put("AAA", "K");      
		codonTable.put("GAA", "E");
		codonTable.put("UAG", "Stop");
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
		codonTable.put("UGA", "Stop");
		codonTable.put("CGA", "R");
		codonTable.put("AGA", "R");      
		codonTable.put("GGA", "G");
		codonTable.put("UGG", "W");   
		codonTable.put("CGG", "R");
		codonTable.put("AGG", "R");      
		codonTable.put("GGG", "G");
	}

}
