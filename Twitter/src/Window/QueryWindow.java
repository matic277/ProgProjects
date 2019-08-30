package Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
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


	/**
	 * 
	 */
	private static final long serialVersionUID = 7831001409134634647L;

	JPanel panel;
	
	MainWindow mainWindow;
	
	int width, height;
	
	int buttonSpacing;
	
	ArrayList<JTextField> inputs;
	ArrayList<Button> buttons;
	ArrayList<JLabel> labels;
	Button addNewFieldButton;
	
	Dimension topPanelSize;
	Dimension buttonSize;
	
	Dimension textFieldSize;
	Dimension addTextFieldSize;
	
	Rectangle leftPanel;
	Rectangle topPanel;
	Rectangle bottomPanel;
	Rectangle rightPanel;
	
	Color labelColor = Color.white;
	Color bgColor = new Color(214,217,223);
	
	public QueryWindow(MainWindow mainWindow) {
		width = 500;
		height = 400;
		this.mainWindow = mainWindow;
		
		labels = new ArrayList<JLabel>(5);
		buttons = new ArrayList<Button>(5);
		inputs = new ArrayList<JTextField>(10);
		
		buttonSpacing = 15;
		
		buttonSize = new Dimension(110, 30);
		textFieldSize = new Dimension(150, 30);
		addTextFieldSize = new Dimension(35, 30);

		topPanelSize = new Dimension(0, 200);
		
		topPanel = new Rectangle();
		leftPanel = new Rectangle();
		rightPanel = new Rectangle();
		bottomPanel = new Rectangle();
		
		addNewFieldButton = new Button("+");
		
		
		positionPanels();
		initButtons();
		initPanel();
	}
	
	private void positionPanels() {
		//labels.clear();
		// top
		topPanelSize.width = width - (2 * buttonSpacing);
		topPanel.setBounds(
			buttonSpacing,
			buttonSpacing,
			topPanelSize.width,
			topPanelSize.height
		);

		// bottom
		bottomPanel.setBounds(
			buttonSpacing,
			height - (buttonSpacing * 3) - buttonSize.height,
			width - 2*buttonSpacing,
			(buttonSpacing * 2) + buttonSize.height
		);
		
		// left
		leftPanel.setBounds(
			buttonSpacing,
			topPanel.height + 2*buttonSpacing,
			width/2 - buttonSpacing - buttonSpacing/2,
			bottomPanel.y - (topPanel.height + 2*buttonSpacing) - buttonSpacing
		);
		
		// right
		rightPanel.setBounds(
			leftPanel.x + leftPanel.width + buttonSpacing,
			topPanel.height + 2*buttonSpacing,
			width/2 - buttonSpacing - buttonSpacing/2,
			bottomPanel.y - (topPanel.height + 2*buttonSpacing) - buttonSpacing
		);	
	}
	
	private void initButtons() {
		// first input field
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
		
		// add new field button
		addNewFieldButton.setBounds(
			first.getBounds().x + first.getBounds().width + buttonSpacing,
			first.getBounds().y,
			addTextFieldSize.width,
			addTextFieldSize.height
		);
		addNewFieldButton.addActionListener(this);
		addNewFieldButton.setAction(() -> {});
		
		// cancel button
		Button cancel = new Button("Cancel");
		cancel.setBounds(
			bottomPanel.x + bottomPanel.width - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		cancel.addActionListener(this);
		cancel.setAction(() -> {});
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
		ok.setAction(() -> {});
		buttons.add(ok);
	}
	
	private void initPanel() {
		panel = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7047375427827166299L;

			protected void paintComponent(Graphics g){
				g.setColor(bgColor);
				g.fillRect(0, 0, width, height);
				
				g.setColor(labelColor);
				g.fillRect(leftPanel.x, leftPanel.y, leftPanel.width, leftPanel.height);
				g.fillRect(rightPanel.x, rightPanel.y, rightPanel.width, rightPanel.height);
				g.fillRect(topPanel.x, topPanel.y, topPanel.width, topPanel.height);
				g.fillRect(bottomPanel.x, bottomPanel.y, bottomPanel.width, bottomPanel.height);
				
				g.setColor(Color.lightGray);
				g.drawRect(leftPanel.x, leftPanel.y, leftPanel.width, leftPanel.height);
				g.drawRect(rightPanel.x, rightPanel.y, rightPanel.width, rightPanel.height);
				g.drawRect(topPanel.x, topPanel.y, topPanel.width, topPanel.height);
				g.drawRect(bottomPanel.x, bottomPanel.y, bottomPanel.width, bottomPanel.height);
								
				super.repaint(1000/30);
			}
		};
		this.add(panel);
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(width, height));
		panel.addComponentListener(this);
		
		labels.forEach(l -> panel.add(l));
		buttons.forEach(b -> panel.add(b));
		
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
	}
	
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent a) {

	}

	private void repositionFieldAdderButton() {

	}
}
