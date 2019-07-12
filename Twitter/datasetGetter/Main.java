import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main {
	
	// constructing the emoji dictionary
	
	static ArrayList<String> extracted = new ArrayList<String>(10000);

	public static void main(String[] args) {

		String relativeFilePath = "dictionary/emojis.txt";
		try (Stream<String> lines = Files.lines(Paths.get(relativeFilePath))) {
			lines.forEachOrdered(
				line -> processLine(line)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		saveResults();

	}
	
	public static void processLine(String line) {
		String[] tokens = line.split(",");
//		System.out.println(line);
		String code = tokens[1];
		String neg = tokens[4];
		String neu = tokens[5];
		String pos = tokens[6];
		String meaning = tokens[7];
		
		double negper =  Double.parseDouble(neg) / Double.parseDouble(tokens[2]);
		double neuper =  Double.parseDouble(neu) / Double.parseDouble(tokens[2]);
		double posper =  Double.parseDouble(pos) / Double.parseDouble(tokens[2]);
		
		
		double sen = posper - negper;
		
//		System.out.println(pos +" -> " + Double.parseDouble(pos));
//		System.out.println(neg +" -> " + Double.parseDouble(neg));
		
		String procline = code +","+ meaning + "," + sen;
		System.out.println(procline);
		extracted.add(procline);
	}
	
	public static void saveResults() {
		String output = "dictionary/emojis2.txt";
		File file = new File(output );
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			for (int i=0; i<extracted.size(); i++) {
				writer.write(extracted.get(i));
				writer.newLine();
			}
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error at writing file!\n");
			e.printStackTrace();
		}
	}
	
	

}