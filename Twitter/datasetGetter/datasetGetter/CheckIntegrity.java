package datasetGetter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CheckIntegrity {
	
	public static void main(String[] args) {
		String filename = "combined.txt";
		try (Stream<String> lines = Files.lines(Paths.get(filename), Charset.defaultCharset())) {
			lines.forEachOrdered(line -> processLine(line));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	static int linen = 0;
	private static void processLine(String line) {
		linen++;
		String[] tokens = line.split(",");
		if (!(tokens[0].equals("0") || tokens[0].equals("-1") || tokens[0].equals("1"))) {
			System.out.println("not good at line: " + linen + " -> " + line);
		}
	}

}
