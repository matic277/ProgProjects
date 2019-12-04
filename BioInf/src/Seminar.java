import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Seminar {
	
	static StringBuilder sb = new StringBuilder();
	
	static String seq;
	static ArrayList<Protein> annotatedProteins = new ArrayList<Protein>();
	static ArrayList<Protein> foundProteins = new ArrayList<Protein>();
	static ArrayList<String> foundProteins2 = new ArrayList<String>();
	
	static int maxLen = 0;
	
	public static void main(String[] args) throws IOException {
		readSequence("./g37.txt");
		readProteins("./clean_source.txt");
		seq = sb.toString();
		
		System.out.println("-> Reading done");
		System.out.println("   -> Sequence length: " + seq.length());
		System.out.println("   -> Number of proteins: " + annotatedProteins.size());
		
		System.out.println("-> Finding proteins...");
		foundProteins2 = (ArrayList<String>) Bio.getORFs(seq);
		System.out.println("   -> Number of possible proteins found: " + foundProteins2.size());
		
		for (String s : foundProteins2) maxLen = Integer.max(maxLen, s.length());
		System.out.println("   -> Max length from found proteins: " + maxLen);
		
		annotatedProteins.sort(Protein::sortByLength);
		
		System.out.println("-> Annotated genes stats: ");
		System.out.println("   -> Max length from annotated proteins: "
				+ annotatedProteins.get(annotatedProteins.size()-1).seq.length());
		for (Protein p : annotatedProteins) {
			if (p.seq.length() > 1) {
				System.out.println("   -> Min length from annotated proteins: " + p.seq.length());
				break;
			}
		}
		int medianIndex1 = annotatedProteins.size() / 2;
		int medianIndex2 = 1 + (annotatedProteins.size() / 2);
		int median = (annotatedProteins.get(medianIndex1).seq.length()
				+ annotatedProteins.get(medianIndex2).seq.length()) / 2;
		System.out.println("   -> Median length from annotated proteins: " + median);

//		annotatedProteins.forEach(p -> {
//			System.out.println(p.seq.length());
//		});
		
		
		//computePercisionAndRecall();

	}
		
	public static void computePercisionAndRecall() {
		DecimalFormat format = (DecimalFormat)DecimalFormat.getNumberInstance(Locale.UK);
		
		for (int L=1; L<=maxLen; L++) {
			double TP = 0, FN = 0, FP = 0;

			for (String s : foundProteins2) {
				// the proteins i'm considering, >L
				if (s.length() > L) {
					if (proteinIsInAnnotatedProteins(s)) {
						TP++;
					} else {
						FP++;
					}
				}
				// proteins i'm not considering
				 else {
					 if (proteinIsInAnnotatedProteins(s)) {
						FN++;
					}
				}
			}
			
			double recall = TP / (TP + FN);
			double percision = TP / (TP + FP);
//			System.out.println(" -> L = " + L +
//					" -> Percision (" + format.format(percision) + "), "
//					+ "Recall: (" + format.format(recall) + ")");
			
			System.out.println(L + ", " + format.format(percision) + ", " + format.format(recall));
		}
	}
	
	private static boolean proteinIsInAnnotatedProteins(String s) {
		for (Protein p : annotatedProteins) {
			if (p.compareToProteinString(s) == 1) return true;
		}
		return false;
	}

	
	public static void readSequence(String path) throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get(path))) {
			lines.forEachOrdered(Seminar::processSequenceLine);
		}
	}
	
	public static void readProteins(String path) throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get(path))) {
			lines.forEachOrdered(Seminar::processProteinLine);
		}
	}
	
	public static void processProteinLine(String line) {
		String[] tokens = line.split(" ");
		String indexes = tokens[0].substring(1, tokens[0].length()-1);
		String[] start_end = indexes.split(":");
		String seq = (tokens[1].equals("?"))? 
				"?" : tokens[1].substring(2, tokens[1].length()-2);
		
		annotatedProteins.add(new Protein(
			seq,
			Integer.parseInt(start_end[0]),
			Integer.parseInt(start_end[1])
		));
	}
	
	public static void processSequenceLine(String line) {
		sb.append(line.toUpperCase());
	}
	
	
	
	

}

class Protein implements Comparable<Protein> {
	String seq;
	int start;
	int end;
	
	public Protein(String seq, int start, int end) {
		this.seq = seq;
		this.start = start;
		this.end = end;
	}


	@Override
	public int compareTo(Protein p) {
		if (this.seq.length() != p.seq.length()) return -1;
		
		boolean matches = true;
		for (int i=0; i<seq.length(); i++) {
			if (seq.charAt(i) == 'Z' || p.seq.charAt(i) == 'Z' ) continue;
			else if (seq.charAt(i) != p.seq.charAt(i)) {
				matches = false;
				break;
			}
		}
		if (matches) return 1;
		
		StringBuilder sb = new StringBuilder(seq);
		String rseq = sb.reverse().toString();
		
		for (int i=0; i<rseq.length(); i++) {
			if (rseq.charAt(i) == 'Z' || p.seq.charAt(i) == 'Z' ) continue;
			else if (rseq.charAt(i) != p.seq.charAt(i)) return -1;
		}
		return 1;
	}
	
	public int compareToProteinString(String s) {
		if (this.seq.length() != s.length()) return -1;
		
		boolean matches = true;
		for (int i=0; i<seq.length(); i++) {
			if (seq.charAt(i) == 'Z' || s.charAt(i) == 'Z' ) continue;
			else if (seq.charAt(i) != s.charAt(i)) {
				matches = false;
				break;
			}
		}
		if (matches) return 1;
		
		StringBuilder sb = new StringBuilder(seq);
		String rseq = sb.reverse().toString();
		
		for (int i=0; i<rseq.length(); i++) {
			if (rseq.charAt(i) == 'Z' || s.charAt(i) == 'Z' ) continue;
			else if (rseq.charAt(i) != s.charAt(i)) return -1;
		}
		return 1;
	}
	
	public String indexToString() {
		return "[" + start + ", " + end + "]";
	}
	
	@Override
	public String toString() {
		return indexToString() + ", [" + seq + "]";
	}
	
	public static int sortByLength(Protein p1, Protein p2) {
		if (p1.seq.length() < p2.seq.length()) return -1;
		if (p1.seq.length() > p2.seq.length()) return 1;
		return 0;
	}


	public static int compare(Protein p1, Protein p2) {
		if (p1.start < p2.start) return 1;
		if (p1.start > p2.start) return -11;
		return 0;
	}
	
	public int compareByLength(Protein p) {
		if (p.seq.length() < seq.length()) return 1;
		if (p.seq.length() > seq.length()) return -1;
		return 0;
	}
}
