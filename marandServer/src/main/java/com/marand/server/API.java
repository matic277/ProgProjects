package com.marand.server;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.marand.db.Data;
import com.marand.db.DataInfo;
import com.marand.db.Database;
import com.marand.db.DatabaseException;
import com.marand.db.FieldInfo;

@Path("/api")
public class API {
	
	Data db = Database.getDatabase();
	
	public API() {
		//System.out.println(" -> ClientIniter constructor called.");
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("databaseinfo")
	public String getFieldNames() {
		//System.out.println("field names");
		
		FieldInfo[] fields = db.getFieldInfo();
		String[] names = new String[fields.length];
		int[] lengths = new int[fields.length];
		
		for (int i=0; i<fields.length; i++) {
			names[i] = fields[i].getName();
			lengths[i] = fields[i].getLength();
		}
		return Arrays.toString(names) + "," +Arrays.toString(lengths);
	}
	
	@GET
	@Path("searchflights")
	@Produces(MediaType.TEXT_PLAIN)
	//@Consumes(MediaType.TEXT_PLAIN)
	public String getFlightsByCriteria(@QueryParam("from") String from, @QueryParam("to") String to) {
//		System.out.println("rearch results for query: " + from + ", " + to);

		// TODO: do this client-side and get only one param?
		String criteria = "'Origin airport'='" + from + "','Destination airport'='" + to + "'";
		
		List<String> matchingFlights = null;
		
		try {
			matchingFlights = db.criteriaFind(criteria);
		} catch (DatabaseException e) {
			System.out.println("Error generating flights matching criteria.");
			System.out.println("  -> Criteria: " + criteria);
			e.printStackTrace();
			return "";
		}
		if (matchingFlights == null || matchingFlights.isEmpty()) return "";
		
		StringBuilder sb = new StringBuilder();
		matchingFlights.forEach(s -> {
			sb.append(s);
			sb.append(",");
		});
		
		// get rid of trailing ','
		return sb.toString().substring(0, sb.length() - 1);
	}
	
	@GET
	@Path("bookseats")
	@Produces(MediaType.TEXT_PLAIN)
	public String bookSeats(@QueryParam("forFlight") String forFlight, @QueryParam("numOfSeats")String numOfSeats) {
		// TODO enumerate or map to class fields(columns) of database?
		//System.out.println("Booking for: " + forFlight + ", " + numOfSeats);
		DataInfo flightInfo = null;
		
		try {
			flightInfo = db.find(forFlight);
			if (flightInfo != null) {
				int numOfAvaliableSeats = Integer.parseInt(flightInfo.getValueOfField("Available seats"));
				int numberOfSeats = Integer.parseInt(numOfSeats);

				if (numberOfSeats <= numOfAvaliableSeats) {
					bookSeats(numberOfSeats, numOfAvaliableSeats, flightInfo);
					return "true";
				}
				
			}
		} catch (DatabaseException e) {
			System.out.println("Error at searching database/booking seats.");
			System.out.println("  -> Searched for flight: " + forFlight);
			e.printStackTrace();
		}
		return "false";
	}
	
	private void bookSeats(int numOfSeats,int numOfAvaliableSeats, DataInfo flightInfo) throws DatabaseException {
		//System.out.println("BOOKING");
		flightInfo.updateFieldValue("Available seats", numOfAvaliableSeats - numOfSeats + "");
		db.modify(flightInfo);
	}

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String testConnection() throws IOException {
        return "Connection working.";
    }
}
