package com.marand.client;

import java.awt.event.ActionEvent;


import javax.swing.event.ListSelectionEvent;

public class ComponentListeners {
	
	ClientApp client;
	String selectedFlightNumber;
	
	public ComponentListeners(ClientApp client) {
		this.client = client;
	}
	
	public void flightTableEvent(ListSelectionEvent event) {
		if (!event.getValueIsAdjusting()) {
			if (client.flightsTable.getSelectedRow() == -1) return;
			selectedFlightNumber = client.flightsTable
					.getValueAt(client.flightsTable.getSelectedRow(), 0)
					.toString();
		}
	}
	
	public void searchButtonEvent(ActionEvent event) {
		// fields must be 3 letters or chars
		String from = client.fromInputField.getText().toUpperCase();
		String to = client.toInputField.getText().toUpperCase();
		
		boolean isValidFieldFrom = from.length() == 3 && from.chars().allMatch(Character::isLetter);
		boolean isValidFieldTo = to.length() == 3 && to.chars().allMatch(Character::isLetter);
		
		if (isValidFieldFrom && isValidFieldTo) {
			String[] flights = client.sendSearchRequest(from, to);
			if (flights.length > 1) client.updateTable(flights);
			else client.clearTable();
		} else {
			System.out.println("Bad inputs: " + from + ", " + to);
		}
	}
	
	public void reserveButtonEvent(ActionEvent event) {
		String seats = client.seatsInputField.getText();
		// seats must be a number
		if (seats.matches("[1-9]+") && selectedFlightNumber != null) {
			client.bookSeats(selectedFlightNumber, seats);
			client.flightsTable.clearSelection();
		} else {
			client.clearTable();
			System.out.println("Error in inputs.");
			System.out.println(" -> seats: " + seats);
			System.out.println(" -> selected row: " + client.flightsTable.getSelectedRow());
		}
	}

}
