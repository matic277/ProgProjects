import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Text {
	
	
	public String text = "";
	public String words[];
	
	// https://sherlock-holm.es/stories/html/advs.html#Chapter-1
	String relativeFilePath = "resources/holmes.txt";
	
	public Text() {
		readFile();
	}
	
	public void readFile() {
		try (Stream<String> lines = Files.lines(Paths.get(relativeFilePath))) {
			lines.forEachOrdered(
				line -> processLine(line)
			);
		} catch (IOException e) {
			System.out.println("Error reading file!");
			e.printStackTrace();
		}
		
		preprocessText();
		
		words = text.split(" ");
		System.out.println("Words: " + words.length);
		System.out.println("Lines: " + ln);
	}

	int ln = 0;
	private void processLine(String line) {
		ln++;
		if (line.length() < 2) {
			System.out.println("Bad line?: " + line);
		}
		
		line = line.replace("\n", " ");
		text += line + " ";
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
}
