package Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QueryWindow extends JFrame implements ComponentListener, ActionListener {

	private static final long serialVersionUID = 7831001409134634647L;

	JPanel panel;
	
	MainWindow mainWindow;
	
	int width, height;
	
	int buttonSpacing;
	
	int presetLeftPanelHeight = 200;

	ArrayList<TextField> inputs;
	ArrayList<Button> buttons;
	ArrayList<JLabel> labels;
	Button addNewFieldButton;
	
	Dimension topPanelSize;
	Dimension bottomPanelSize;
	
	Dimension buttonSize;
	
	Dimension textFieldSize;
	Dimension addTextFieldSize;
	
	Rectangle leftPanel;
	Rectangle topPanel;
	Rectangle bottomPanel;
	Rectangle rightPanel;
	
	ArrayList<String> presetQueries;
	
	Color labelColor = Color.white;
	Color bgColor = Color.decode("#f2f2f2");
	Color borderColor = Color.decode("#b7b7b7");
	
	public QueryWindow(MainWindow mainWindow, ArrayList<String> presetQueries) {
		width = 500;
		this.mainWindow = mainWindow;
		this.presetQueries = presetQueries;
		
		buttons = new ArrayList<Button>(10);
		labels = new ArrayList<JLabel>(10);
		inputs = new ArrayList<TextField>(10);
		
		addNewFieldButton = new Button("+");
		
		buttonSpacing = 15;
		
		buttonSize = new Dimension(110, 30);
		textFieldSize = new Dimension(150, 30);
		addTextFieldSize = new Dimension(35, 30);
		
		initPanels();
		initButtons();
		initPanel();
	}
	
	private void initPanels() {
		topPanelSize = new Dimension(width - 2 * buttonSpacing, 100);
		topPanel = new Rectangle(
			buttonSpacing,
			buttonSpacing,
			topPanelSize.width,
			topPanelSize.height
		);
		
		int leftPanelHeight = 100;
		if (presetQueries != null) {
			leftPanelHeight = presetQueries.size()*buttonSize.height + ((presetQueries.size()+1) * buttonSpacing);
		}	
		leftPanel = new Rectangle(
			buttonSpacing,
			topPanel.y + topPanel.height + buttonSpacing,
			(int)((width / 2) - (buttonSpacing * 1.5)),
			leftPanelHeight
		);

		
		rightPanel = new Rectangle(
			leftPanel.x + leftPanel.width + (buttonSpacing / 2),
			leftPanel.y,
			(int)((width / 2) - (buttonSpacing * 1.5)),
			leftPanelHeight
		);
		
		int bottomY = buttonSpacing + topPanel.height +
					  buttonSpacing + leftPanel.height +
					  buttonSpacing + buttonSpacing + buttonSize.height + buttonSpacing + buttonSpacing;
		bottomPanelSize = new Dimension(topPanelSize.width, buttonSize.height + (buttonSpacing * 2));
		bottomPanel = new Rectangle(
			buttonSpacing,
			bottomY,
			bottomPanelSize.width,
			bottomPanelSize.height
		);
		
		height = (4 * buttonSpacing) + topPanel.height + leftPanel.height + bottomPanel.height;	
		
		System.out.println("calc: " + height);
	}
	
	private void positionPanels() {
		// top
		topPanelSize.width = width - (2 * buttonSpacing);
		topPanel.setBounds(
			buttonSpacing,
			buttonSpacing,
			topPanelSize.width,
			topPanelSize.height
		);
		
		int leftPanelHeight = 100;
		if (presetQueries != null) {
			leftPanelHeight = presetQueries.size()*buttonSize.height + ((presetQueries.size()+1) * buttonSpacing);
		}	
		leftPanel = new Rectangle(
			buttonSpacing,
			topPanel.y + topPanel.height + buttonSpacing,
			width/2 - buttonSpacing - buttonSpacing/2,
			leftPanelHeight
		);
		
		// right
		rightPanel = new Rectangle(
			leftPanel.x + leftPanel.width + (buttonSpacing / 2),
			leftPanel.y,
			width/2 - buttonSpacing - buttonSpacing/2,
			leftPanelHeight
		);
		
		// bottom
		bottomPanelSize.setSize(topPanelSize.width, buttonSize.height + (buttonSpacing * 2));
		int bottomY = height - buttonSpacing - bottomPanel.height;
		bottomPanel = new Rectangle(
			buttonSpacing,
			bottomY,
			bottomPanelSize.width,
			bottomPanelSize.height
		);
	}
	
	private void positionButtons() {
		// cancel button
		buttons.get(1).setBounds(
			bottomPanel.x + bottomPanel.width - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		
		// ok button
		buttons.get(2).setBounds(
			buttons.get(1).getBounds().x - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		
		// add field button
		TextField last = inputs.get(inputs.size()-1);
		addNewFieldButton.setBounds(
			last.getBounds().x + last.getBounds().width + buttonSpacing,
			last.getBounds().y,
			addTextFieldSize.width,
			addTextFieldSize.height
		);
	}
	
	private void initButtons() {
		// add preset queries if they exist
		if (presetQueries != null) {
			TextField field = new TextField(presetQueries.get(0));
			Rectangle bounds = new Rectangle(
				leftPanel.x + buttonSpacing,
				leftPanel.y + buttonSpacing,
				textFieldSize.width,
				textFieldSize.height
			);
			field.setBounds(bounds);
			inputs.add(field);
			for (int i=1; i<presetQueries.size(); i++) {
				TextField newField = new TextField(presetQueries.get(i));
				newField.setBounds(
					bounds.x,
					bounds.y + bounds.height + buttonSpacing,
					textFieldSize.width,
					textFieldSize.height
				);
				bounds = newField.getBounds();
				inputs.add(newField);
			}
		} else {
			// add an empty one otherwise
			TextField first = new TextField("");
			first.setBounds(
				leftPanel.x + buttonSpacing,
				leftPanel.y + buttonSpacing,
				textFieldSize.width,
				textFieldSize.height
			);
			first.addActionListener(this);
			first.setAction(() -> {});
			inputs.add(first);
		}
		
		// add new field button
		Rectangle lastBounds = new Rectangle(inputs.get(inputs.size()-1).getBounds());
		addNewFieldButton.setBounds(
			lastBounds.getBounds().x + lastBounds.getBounds().width + buttonSpacing,
			lastBounds.getBounds().y,
			addTextFieldSize.width,
			addTextFieldSize.height
		);
		addNewFieldButton.addActionListener(this);
		addNewFieldButton.setAction(() -> {
			TextField lastField = inputs.get(inputs.size()-1);
			Rectangle bounds = lastField.getBounds();
			TextField newField = new TextField("");
			newField.setBounds(
				bounds.x,
				bounds.y + bounds.height + buttonSpacing,
				textFieldSize.width,
				textFieldSize.height
			);
			inputs.add(newField);
			addNewFieldButton.setBounds(
				newField.getBounds().x + newField.getBounds().width + buttonSpacing,
				newField.getBounds().y,
				addTextFieldSize.width,
				addTextFieldSize.height
			);
			panel.add(newField);
			
			// resize panel if this button falls out of leftPanels bounds
			int bottomLineY_leftPanel = leftPanel.y + leftPanel.height;
			int topLineY_addFieldButton = addNewFieldButton.getBounds().y;
			int dist = bottomLineY_leftPanel - buttonSpacing - addNewFieldButton.getBounds().height;
			if (topLineY_addFieldButton > dist) {
				int extraHeight = buttonSpacing + textFieldSize.height;
				resizePanel(extraHeight);
			}
		});
		buttons.add(addNewFieldButton);
		
		// cancel button
		Button cancel = new Button("Cancel");
		cancel.setBounds(
			bottomPanel.x + bottomPanel.width - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		cancel.addActionListener(this);
		cancel.setAction(() -> this.dispose());
		buttons.add(cancel);
		
		// ok button
		Button ok = new Button("OK");
		ok.setBounds(
			cancel.getBounds().x - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		ok.addActionListener(this);
		ok.setAction(() -> {
			ArrayList<String> list = new ArrayList<String>(inputs.size());
			for (TextField tf : inputs) if (!tf.getText().trim().isEmpty()) list.add(tf.getText());
			mainWindow.setQueryList(list);
			this.dispose();
		});
		buttons.add(ok);
	}
	
	private void resizePanel(int extraHeight) {
		panel.setSize(panel.getWidth(), extraHeight + panel.getHeight());
		positionPanels();
		positionButtons();
	}

	private void initPanel() {
		panel = new JPanel() {
			private static final long serialVersionUID = -7047375427827166299L;
			protected void paintComponent(Graphics g){
				g.setColor(bgColor);
				g.fillRect(0, 0, width, height);
				
				g.setColor(labelColor);
				g.fillRect(leftPanel.x, leftPanel.y, leftPanel.width, leftPanel.height);
				g.fillRect(rightPanel.x, rightPanel.y, rightPanel.width, rightPanel.height);
				g.fillRect(topPanel.x, topPanel.y, topPanel.width, topPanel.height);
				g.fillRect(bottomPanel.x, bottomPanel.y, bottomPanel.width, bottomPanel.height);
				
				g.setColor(borderColor);
				g.drawRect(leftPanel.x, leftPanel.y, leftPanel.width, leftPanel.height);
				g.drawRect(rightPanel.x, rightPanel.y, rightPanel.width, rightPanel.height);
				g.drawRect(topPanel.x, topPanel.y, topPanel.width, topPanel.height);
				g.drawRect(bottomPanel.x, bottomPanel.y, bottomPanel.width, bottomPanel.height);
								
				super.repaint(1000/3);
			}
		};
		this.add(panel);
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(width, height));
		panel.addComponentListener(this);
		
		labels.forEach(l -> panel.add(l));
		buttons.forEach(b -> panel.add(b));
		inputs.forEach(i -> panel.add(i));
		
		this.setTitle("Query");
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}

	
	@Override
	public void componentResized(ComponentEvent event) {
		Dimension newSize = event.getComponent().getSize();
		this.width = newSize.width;
		this.height = newSize.height;
		positionPanels();
		positionButtons();
	}
	
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent event) {
		((IComponentFunction) event.getSource()).performAction();
	}

	private void repositionFieldAdderButton() {
		
	}
}
