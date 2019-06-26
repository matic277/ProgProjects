package multi;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Text {
	
	
	public String text = "";
//	public String words[] = //new String[772095 + 1 + 77911*2];
//			new String[77911 + 772905 + 2];
//	
//	// lines: 77911
//	// spaces: 772905
//	// words: spaces + 1  + lines + 1
	public ArrayList<String> words = new ArrayList<String>(100000);
	
	// https://sherlock-holm.es/stories/html/advs.html#Chapter-1
	String relativeFilePath = "resources/holmes_5mb.txt";
	
	public Text() {
		readFile();
	}
	
	public void readFile() {
		// Stream<String> lines = Files.lines(Paths.get(relativeFilePath), Charset.forName("Cp1251"))
		// when reading the 5mb file, no charset defined when reading the small file
		try ( Stream<String> lines = Files.lines(Paths.get(relativeFilePath), Charset.forName("Cp1251")) ) {
			lines.forEachOrdered(
				line -> processLine(line)
			);
			lines.close();
		} catch (Exception e) {
			System.out.println("Error reading file!");
			e.printStackTrace();
		}
	}

	int ln = 0;
	int i = 0;
	private void processLine(String line) {
//		ln++;
//		if (ln % 1000 == 0)
//			System.out.println((ln / 77911.0)*100 + " %");
		
//		if (line.contains("\n"))
//			line = line.replace("\n", " ");
		
//		text += line + " ";
//		text = text.concat(line + " ");
		
		
		line = line.replace("\n", " ");
		String[] tokens = line.split(" ");
		
		for (int j=0; j<tokens.length; j++) {
			words.add(tokens[j]);
		}
	}
	
	public ArrayList<String> getWords() {
		return words;
	}
	
	private void preprocessText() {
		text = text.toLowerCase();
		
		String[] replacementChars = {
			".", ",", ";", ":",	"'", "!",
			"?", "\r", "\"", "/", "*",
			"(", ")", "[", "]", "{", "}"
		};
		
		for (String s : replacementChars) text = text.replace(s, "");
	}
	
	private void preprocessText2() {
//		
//		
//		int len = 0;
//		for (int i=0; i<words.length; i++) {
//			if (words[i] == null) {
//				len = i-1;
//				break;
//			}
//		}
//		
//		String[] w = new String[len];
//		for (int i=0; i<w.length; i++) w[i] = words[i];
//		words = w;
//		
		String[] replacementChars = {
			".", ",", ";", ":",	"'", "!",
			"?", "\r", "\"", "/", "*",
			"(", ")", "[", "]", "{", "}"
		};
		
		for (int i=0; i<words.size(); i++) {
			words.set(i, words.get(i).toLowerCase());
			for (String s : replacementChars) words.set(i, words.get(i).replace(s, ""));
		}
		
//		for (String s : replacementChars) text = text.replace(s, "");
	}
}
