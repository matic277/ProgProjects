package ngramStatistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DatasetWriter {

	
	public void writeFile(ArrayList<Gram> list, String fileName) throws Exception {
		System.out.print("Writing file: '"+fileName+"'... ");
		
		File file = new File(fileName);
		if (file.exists()) 
			throw new Exception("File '" + fileName + "' already exists");
		
		BufferedWriter writer = null;
		
		// this is only appending text to the file
		// no need to worry about overwriting
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			
			for (int i=0; i<list.size(); i++) {
				writer.write(list.get(i).ngram);
				writer.newLine();
			}
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			System.out.println("Error at writing file!\n");
			e.printStackTrace();
		}

		System.out.println("File written.");
	}

}
