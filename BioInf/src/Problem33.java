import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Problem33 {
	
	public static void main(String[] args) throws IOException {
		GenomeAssemblyPerfectCoverage ga = new GenomeAssemblyPerfectCoverage();
		System.out.println(ga.reconstructGenome("./input.txt"));
		
		System.out.println("Done.");
	}
	
	

}

class GenomeAssemblyPerfectCoverage {
	
	private ArrayList<String> path = new ArrayList<>();
	public String reconstructGenome(String filename) 
			throws IOException {

		List<String> reads = new ArrayList<String>();
		FileInputStream fstream = new FileInputStream(filename);
		BufferedReader br = 
				new BufferedReader(new InputStreamReader(fstream));

		String read;
		while ((read = br.readLine()) != null) {
			reads.add(read);
		}
		br.close();
		Map<String, ArrayList<String>> graph = 
				constructDeBruin(reads);
		String firstRead = reads.get(0);
		String target = firstRead.substring(0, firstRead.length() - 1);
		constructHamiltonianPath(target, target, new HashSet<String>(), graph);
		Collections.reverse(path);
		String dna = coalesceStrings(path);
		int trim = calculateKmpPrefixTable(dna);
		String shortenedDna = dna.substring(0, dna.length() - trim);
		return shortenedDna;

	}
	
	private Map<String, ArrayList<String>> constructDeBruin(List<String> reads) {
		Map<String, ArrayList<String>> graph = new HashMap<>();
		for (String read : reads) {
			String a = read.substring(0, read.length() - 1);
			String b = read.substring(1, read.length());
			if (!graph.containsKey(a)) {
				ArrayList<String> arr = new ArrayList<String>();
				graph.put(a, arr);
			}
			graph.get(a).add(b);
		}
		return graph;
	}
	
	private boolean constructHamiltonianPath(String target, String current, Set<String> visited,
			Map<String, ArrayList<String>> graph) {
		visited.add(current);
		List<String> neighbours = graph.get(current);
		for (String neighbour : neighbours) {
			if (neighbour.equals(target)) {
				path.add(current);
				return true;
			} else if (!visited.contains(neighbour)) {
				boolean res = constructHamiltonianPath(target, neighbour, visited, graph);
				if (res) {
					path.add(current);
					return true;
				}
			} else
				continue;
		}
		return false;
	}
	
	private String coalesceStrings(ArrayList<String> path) {
		StringBuilder sb = new StringBuilder();
		sb.append(path.get(0));
		for (int i = 1; i < path.size(); i++) {
			String read = path.get(i);
			sb.append(read.charAt(read.length() - 1));
		}
		return sb.toString();
	}
	
	private int calculateKmpPrefixTable(String dna) {
		int[] table = new int[dna.length()];
		int j = 0;
		for (int i = 1; i < dna.length(); i++) {
			if (dna.charAt(i) == dna.charAt(j)) {
				table[i] = j + 1;
				j++;
			} else {
				while (j > 0 && dna.charAt(i) != dna.charAt(j)) {
					j = table[j - 1];
				}
				if (dna.charAt(j) == dna.charAt(i)) {
					table[i] = j + 1;
					j++;
				}
			}
		}
		return table[dna.length() - 1];
	}
}