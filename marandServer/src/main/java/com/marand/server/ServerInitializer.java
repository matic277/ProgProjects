package com.marand.server;

import javax.servlet.http.HttpServlet;
import com.marand.db.Database;

//@WebServlet("init")
public class ServerInitializer extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerInitializer() {
		System.out.println("--- Server initing... ---");
		initDatabase();
		System.out.println("--- Server inited -------");
	}
	
	private void initDatabase() {
		// build/read database on startup
		Database.getDatabase();
	}

}
