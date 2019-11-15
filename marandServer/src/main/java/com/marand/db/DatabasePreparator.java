package com.marand.db;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Creates a Data object, which is the database.
 * Reads all data from given path, and creates
 * a database at given location.
 * 
 * @param: String databasePath - Path to database
 * @param: String dataPath - path to data that will be inserted to database
 */
public class DatabasePreparator {
	
	private final String databasePath = "C:\\Users\\V2\\eclipse-workspace-EE\\marandServer\\data\\db.txt";
	private final String dataPath = "C:\\Users\\V2\\eclipse-workspace-EE\\marandServer\\data\\data.txt";
	
	private Data database;
	private FieldInfo[] description;
	
    private String headerPrefix = "#";
    private String separator = "\\^";
    
    private List<String[]> dataLines = new LinkedList<String[]>();
    private Deque<FieldInfo> headerLines = new LinkedList<FieldInfo>();
	
	public DatabasePreparator() {
		// check to see if database already exists
		// assuming we can read/write
		File dbFile = new File(databasePath);
		
		if (dbFile.exists()) {
			try {
				database = new Data(databasePath);
			} catch (IOException e) {
				System.out.println("Error creating database");
				System.out.println(" -> databasePath: '" + databasePath + "'.");
				e.printStackTrace();
			}
		} else {
			readData();
			createDatabase();
			addAllRecords();
		}
	}
	
	private void readData() {
		try {
	    	Path path = Paths.get(dataPath);
			Files.lines(path).forEach(this::processLine);
		}
		catch(Exception e) {
			System.out.println("Error at reading data file: '" + dataPath + "'.");
			e.printStackTrace();
		}
//		headerLines.forEach(s -> System.out.print(s.getLength() + " "));

		description = new FieldInfo[headerLines.size()];
		for (int i=0; i<description.length; i++)
			description[i] = headerLines.pollFirst();
	}
	
    private void processLine(String line) {
    	String[] tokens;
    	
    	// header lines
    	if (line.startsWith(headerPrefix)) {
    		line = line.substring(1, line.length());
    		tokens = line.split(separator);
    		
    		// should always be two tokens
    		// [0] -> max field len
    		// [1] -> field name
    		int fieldLength = Integer.parseInt(tokens[0]);
    		String fieldName = tokens[1];
    		
    		headerLines.add(new FieldInfo(fieldName, fieldLength));
    	}
    	// data lines
    	else {
    		tokens = line.split(separator);
    		dataLines.add(tokens);
    	}
    }
    
	private void createDatabase() {
		try {
			database = new Data(databasePath, description);
		} catch (IOException e) {
			System.out.println("Error initing database:");
			System.out.println(" -> database path: '" + databasePath + "'.");
			System.out.println(" -> description: " + Arrays.deepToString(description));
			e.printStackTrace();
		}
	}
	
	private void addAllRecords() {
		for (String[] data : dataLines) {
			try {
				System.out.println("Writing to database: " + Arrays.toString(data));
				database.add(data);
			}
			catch (DatabaseException e) {
				System.out.println("Error at recording data:");
				System.out.println(" -> data: " + Arrays.toString(data));
				e.printStackTrace();
			}
		}
	}
	
	public Data getDatabase() {
		return database;
	}
	
	public FieldInfo[] getDescriptionFied() {
		return description;
	}

}
