import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;


public class ClassifierTester {

	static String datasetPath = "resources/incompleteDataset2.txt";
	
	static HashMap<String, String> annotatedTweets = new HashMap<String, String>(3500);
	static HashMap<String, String> classifiedTweets = new HashMap<String, String>(3500);
	
	public static void main(String[] args) {
		readDataset();

	}
	
	public static void readDataset() {
		try (Stream<String> lines = Files.lines(Paths.get(datasetPath), Charset.defaultCharset())) {
			lines.forEachOrdered(
				line -> processLine(line)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void processLine(String line) {
		
	}

}
