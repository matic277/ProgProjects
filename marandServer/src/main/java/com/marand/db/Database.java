package com.marand.db;

public class Database {

	private static Database db;
	private static Data data;
	
	private Database(String s) { }

	public static synchronized Data getDatabase() {
		if (db == null) {
			DatabasePreparator dbPreparator = new DatabasePreparator();
			data = dbPreparator.getDatabase();
		}
		return data;
	}
	
}
