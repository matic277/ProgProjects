package common;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileReader {
	
	String path;
	
	ArrayList<String> lines;
	ArrayList<String> words;
	
	long t;
	
	// 5Mb text:
	// lines: 77911
	// spaces: 772905
	// words: spaces + 1  + lines + 1
	
	public FileReader(String path) {
		this.path = path;
		lines = new ArrayList<String>(78000);
		words = new ArrayList<String>(78000 + 773000);
		readFile();
		processLines();
	}
	
	private void processLines() {
		System.out.println("Creating word list...");
		t = System.currentTimeMillis();
		lines.forEach(line -> {
			for (String s : line.split(" ")) words.add(s);
		});
		t = System.currentTimeMillis() - t;
		System.out.println("\t|-> Time spent: " + t + "ms");
		System.out.println("\t|-> Number of words: " + words.size() + "\n");
	}
	
	private void readFile() {
		System.out.println("Reading and processing file '"+path+"'...");
		
		t = System.currentTimeMillis();
		
		// Stream<String> lines = Files.lines(Paths.get(path), Charset.forName("Cp1251"))
		// when reading the 5mb file, no charset defined when reading the small file
		
		// lazy way
		if (path.contains("5mb")) {
			try ( Stream<String> lines = Files.lines(Paths.get(path), Charset.forName("Cp1251")) ) {
				lines.forEachOrdered(line -> readLine(line));
				lines.close();
			} catch (Exception e) {
				System.out.println("Error reading file!");
				e.printStackTrace();
			}
		}
		else {
			try ( Stream<String> lines = Files.lines(Paths.get(path)) ) {
				lines.forEachOrdered(line -> readLine(line));
				lines.close();
			} catch (Exception e) {
				System.out.println("Error reading file!");
				e.printStackTrace();
			}
		}
		
		t = System.currentTimeMillis() - t;
		
		System.out.println("\t|-> Time spent reading and processing: " + t + "ms");
		System.out.println("\t|-> Number of lines: " + lines.size() + "\n");
	}
	
	private void readLine(String line) {
		String[] charsToRemove = {
			".", ",", ";", ":",	"'", "!",
			"?", "\r", "\"", "/", "*",
			"(", ")", "[", "]", "{", "}"				
		};
		for (String s : charsToRemove) line = line.replace(s, "");
		line = line.trim().toLowerCase().replaceAll(" +", " ");
		lines.add(line);
	}
	
	public ArrayList<String> getWords() {
		return words;
	}

}
