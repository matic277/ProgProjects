package com.marand.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.SystemColor;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;

public class ClientApp {

	private JFrame frame;

	DefaultTableModel tableModel;
	JTable flightsTable;
	
	JTextField fromInputField;
	JTextField toInputField;
	JTextField seatsInputField;
	
	JButton reserveBtn;
	JButton searchBtn;
	
	Client client;
	ComponentListeners listeners;

	public static void main(String[] args) {
		ClientApp window = new ClientApp();
		window.frame.setVisible(true);
	}

	public ClientApp() {
		client = ClientBuilder.newClient();
		initialize();
	}

	private String[] getFieldNames() {
		WebTarget target = client.target("http://localhost:8080/marandServer/webapi/api/databaseinfo");
		String response = target.request(MediaType.TEXT_PLAIN).get(String.class);

		System.out.println(response);

		return response.split("\\],\\[")[0].substring(1).split(", ");
	}
	
	public String[] sendSearchRequest(String from, String to) {
		WebTarget target = client
				.target("http://localhost:8080/marandServer/webapi/api/searchflights")
				.queryParam("from", from)
				.queryParam("to", to);
		
		String response = target.request(MediaType.TEXT_PLAIN).get(String.class);
		
		System.out.println("response: " + response);
		
		return response.split(",");
	}
	
	public void bookSeats(String forFlightNumber, String numOfSeats) {
		WebTarget target = client
				.target("http://localhost:8080/marandServer/webapi/api/bookseats")
				.queryParam("forFlight", forFlightNumber )
				.queryParam("numOfSeats", numOfSeats);

		String response = target.request(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println("response for booking seats: " + response);
		
		String infoText;
		if (response.equals("true")) {
			infoText = "Flight number " + forFlightNumber + " successfully reserved.\n" +
					"Number of seats reserved: " + numOfSeats + ".";
		} else {
			infoText = "Error at booking flight.";
		}
		createNewWindowWithContentText(infoText);
	}

	public void createNewWindowWithContentText(String text) {
		Dimension panelSize = new Dimension(250, 100);
		Dimension buttonSize = new Dimension(60, 30);
		Dimension messageSize = new Dimension(panelSize.width - 20, panelSize.height - buttonSize.height);
		
		JFrame frame = new JFrame("Confirmation frame");
		JPanel panel = new JPanel();
		panel.setPreferredSize(panelSize);
		panel.setLayout(null);
		
		JLabel info = new JLabel("<html>" + text + "<html>");
		info.setBounds(
			10,
			0,
			messageSize.width,
			messageSize.height
		);
		
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(event -> {
			frame.dispose();
		});
		okBtn.setBounds(
			(panelSize.width / 2) - (buttonSize.width / 2),
			panelSize.height - buttonSize.height - 15,
			buttonSize.width,
			buttonSize.height
		);
		panel.add(info);
		panel.add(okBtn);

		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void updateTable(String[] newContentRows) {
		clearTable();
		String separator = "\\^";
		for (String row : newContentRows) {
			String[] values = row.split(separator);
			tableModel.addRow(values);
		}
		System.out.println("added: " + Arrays.toString(newContentRows));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		listeners = new ComponentListeners(this);
		
		initJTable();
		initJTextFields();
		initReserveButton();
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel titleText = new JLabel("Marand Client App");
		titleText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(titleText);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		frame.getContentPane().add(new JScrollPane(flightsTable), BorderLayout.CENTER);
		
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{31, 86, 19, 86, 65, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel fromTextLbl = new JLabel("From: ");
		GridBagConstraints gbc_fromTextLbl = new GridBagConstraints();
		gbc_fromTextLbl.anchor = GridBagConstraints.WEST;
		gbc_fromTextLbl.insets = new Insets(0, 0, 5, 5);
		gbc_fromTextLbl.gridx = 1;
		gbc_fromTextLbl.gridy = 1;
		panel_1.add(fromTextLbl, gbc_fromTextLbl);
		fromTextLbl.setLabelFor(fromInputField);
		
		GridBagConstraints gbc_fromInputField = new GridBagConstraints();
		gbc_fromInputField.anchor = GridBagConstraints.WEST;
		gbc_fromInputField.insets = new Insets(0, 0, 5, 5);
		gbc_fromInputField.gridx = 2;
		gbc_fromInputField.gridy = 1;
		panel_1.add(fromInputField, gbc_fromInputField);
		
		JLabel toTextLbl = new JLabel("To: ");
		GridBagConstraints gbc_toTextLbl = new GridBagConstraints();
		gbc_toTextLbl.anchor = GridBagConstraints.WEST;
		gbc_toTextLbl.insets = new Insets(0, 0, 5, 5);
		gbc_toTextLbl.gridx = 1;
		gbc_toTextLbl.gridy = 2;
		panel_1.add(toTextLbl, gbc_toTextLbl);
		

		GridBagConstraints gbc_toInputField = new GridBagConstraints();
		gbc_toInputField.anchor = GridBagConstraints.WEST;
		gbc_toInputField.insets = new Insets(0, 0, 5, 5);
		gbc_toInputField.gridx = 2;
		gbc_toInputField.gridy = 2;
		panel_1.add(toInputField, gbc_toInputField);
		
		initSearchButton();
		GridBagConstraints gbc_searchBtn = new GridBagConstraints();
		gbc_searchBtn.anchor = GridBagConstraints.WEST;
		gbc_searchBtn.insets = new Insets(0, 0, 5, 5);
		gbc_searchBtn.gridx = 2;
		gbc_searchBtn.gridy = 3;
		panel_1.add(searchBtn, gbc_searchBtn);
		
		JLabel numberOfSeatsLbl = new JLabel("Number of seats: ");
		GridBagConstraints gbc_numberOfSeatsLbl = new GridBagConstraints();
		gbc_numberOfSeatsLbl.insets = new Insets(0, 0, 5, 5);
		gbc_numberOfSeatsLbl.anchor = GridBagConstraints.WEST;
		gbc_numberOfSeatsLbl.gridx = 1;
		gbc_numberOfSeatsLbl.gridy = 6;
		panel_1.add(numberOfSeatsLbl, gbc_numberOfSeatsLbl);
		
		
		GridBagConstraints gbc_seatsInputField = new GridBagConstraints();
		gbc_seatsInputField.insets = new Insets(0, 0, 5, 5);
		gbc_seatsInputField.fill = GridBagConstraints.HORIZONTAL;
		gbc_seatsInputField.gridx = 2;
		gbc_seatsInputField.gridy = 6;
		panel_1.add(seatsInputField, gbc_seatsInputField);
		seatsInputField.setColumns(10);		

		GridBagConstraints gbc_btnReserve = new GridBagConstraints();
		gbc_btnReserve.anchor = GridBagConstraints.WEST;
		gbc_btnReserve.insets = new Insets(0, 0, 0, 5);
		gbc_btnReserve.gridx = 2;
		gbc_btnReserve.gridy = 7;
		panel_1.add(reserveBtn, gbc_btnReserve);
	}
	
	private void initJTable() {
		// JTable and columns
		tableModel = new DefaultTableModel();
		String[] fieldNames = getFieldNames();
		
		for (int i=0; i<fieldNames.length; i++) tableModel.addColumn(fieldNames[i]);
		
		flightsTable = new JTable(tableModel);
		flightsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		flightsTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		flightsTable.getSelectionModel().addListSelectionListener(listeners::flightTableEvent);
	}
	
	private void initReserveButton() {
		reserveBtn = new JButton("Reserve");
		reserveBtn.addActionListener(listeners::reserveButtonEvent);
	}
	
	private void initSearchButton() {
		searchBtn = new JButton("Search");
		searchBtn.addActionListener(listeners::searchButtonEvent);
	}
	
	private void initJTextFields() {
		fromInputField = new JTextField();
		fromInputField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		fromInputField.setColumns(10);
		
		toInputField = new JTextField();
		toInputField.setColumns(10);
		
		seatsInputField = new JTextField();
	}

	public void clearTable() {
		tableModel.setRowCount(0);
	}

}
