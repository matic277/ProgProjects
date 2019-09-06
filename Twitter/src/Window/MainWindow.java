package Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import StreamConsumer.StreamHandler;

public class MainWindow extends JFrame implements ComponentListener, ActionListener, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = -7576066237767094230L;

	JPanel panel;
	
	int width, height;
	
	public static StreamHandler stream;
	
	ArrayList<Button> buttons;
	ArrayList<JLabel> labels;	
	TextField input;
	
	JLabel movableBound;
	Rectangle movableBoundBounds;

	Dimension buttonSize;
	Dimension topPanelSize;
	int buttonSpacing;
	
	Rectangle topPanel;
	Rectangle leftPanel;
	Rectangle rightPanel;
	Rectangle bottomPanel;
	
	QueryWindow queryWindow;
	DictionaryWindow dictionaryWindow;
	
	ArrayList<String> queryFilters;
	ArrayList<String> queryFileFilters;
	
	Color labelColor = Color.white;
	Color bgColor = Color.decode("#f2f2f2");
	
	public MainWindow() {
		width = 720;
		height = 500;
		
		try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); }
		catch (Exception e) { e.printStackTrace(); }
		
		buttons = new ArrayList<Button>(10);
		labels = new ArrayList<JLabel>(10);
		for (int i=0; i<2; i++) labels.add(new JLabel());
		for (int i=0; i<10; i++) buttons.add(new Button(""));
		
		buttonSpacing = 15;
		
		buttonSize = new Dimension(110, 30);
		topPanelSize = new Dimension(0, 150);
		
		topPanel = new Rectangle();
		leftPanel = new Rectangle();
		rightPanel = new Rectangle();
		bottomPanel = new Rectangle();

		positionPanels();
		initButtons();
		positionButtons();
		initPanel();
		
		new Thread(() -> {
			while (true) {
				try { Thread.sleep(3000); }
				catch (InterruptedException e) { e.printStackTrace(); }
				if (stream != null && !stream.processedTweets.isEmpty()) {
					// "latest" processed tweet
					String tweet = stream.processedTweets.getLast().getSourceText();
					// get rid of '...' when printing label, split text into new lines
					String[] tokens = tweet.split(" ");
					tweet = "<html>";
					tweet += tokens[0] + " ";
					for (int i=1; i<tokens.length; i++) {
						if (i%10 == 0) tweet += tokens[i] + "<br>";
						else tweet += tokens[i] + " ";
					}
					tweet += "</html>";
					labels.get(labels.size() - 1).setText(tweet);
					
					// tweets list size
					
					labels.get(labels.size() - 3).setText(" Tweet pool size: " + stream.processedTweets.size());
				} 
				else continue;
			}
		}).start();
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
	
	private void positionButtons() {
		// query button
		buttons.get(0).setBounds(
			leftPanel.x + buttonSpacing,
			leftPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		
		// dictionaries
		buttons.get(1).setBounds(
			leftPanel.x + buttonSpacing,
			leftPanel.y + buttonSize.height + (2 * buttonSpacing),
			buttonSize.width,
			buttonSize.height
		);
		
		// api keys label and field
		labels.get(0).setBounds(
			leftPanel.x + buttonSpacing,
			leftPanel.y + (buttonSize.height * 2) + (3 * buttonSpacing),
			buttonSize.width,
			buttonSize.height
		);
		labels.get(0).setOpaque(true);
		labels.get(0).setBackground(Color.cyan);

		input.setBounds(
			labels.get(0).getBounds().x + labels.get(0).getBounds().width + buttonSpacing,
			labels.get(0).getBounds().y,
			(int)(buttonSize.width * 1.5),
			buttonSize.height
		);
		
		// stop button
		buttons.get(2).setBounds(
			bottomPanel.x + bottomPanel.width - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		
		// start button
		buttons.get(3).setBounds(
			buttons.get(2).getBounds().x - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		
		// movable bound
		movableBoundBounds = new Rectangle(
			leftPanel.x + leftPanel.width + 2,
			leftPanel.y,
			buttonSpacing - 4,
			leftPanel.height
		);
		labels.get(1).setBounds(movableBoundBounds);
	}
	
	private void initButtons() {		
		// query button
		Button b1 = new Button("Query");
		b1.addActionListener(this);
		b1.setAction(() -> queryWindow = new QueryWindow(this, queryFilters));
		buttons.set(0, b1);
		
		// dictionaries
		Button b2 = new Button("Dictionaries");
		b2.setBounds(
			leftPanel.x + buttonSpacing,
			leftPanel.y + buttonSize.height + (2 * buttonSpacing),
			buttonSize.width,
			buttonSize.height
		);
		b2.addActionListener(this);
		b2.setAction(() -> dictionaryWindow = new DictionaryWindow(this));
		buttons.set(1, b2);
		
		// api keys label and field
		JLabel l1 = new JLabel(" Path to API keys:");
		l1.setBounds(
			leftPanel.x + buttonSpacing,
			leftPanel.y + (buttonSize.height * 2) + (3 * buttonSpacing),
			buttonSize.width,
			buttonSize.height
		);
		l1.setOpaque(true);
		l1.setBackground(Color.cyan);
		labels.set(0, l1);
		
		input = new TextField("./apikeys/keys.txt");
		input.setBounds(
			l1.getBounds().x + l1.getBounds().width + buttonSpacing,
			l1.getBounds().y,
			(int)(buttonSize.width * 1.5),
			buttonSize.height
		);
		input.addActionListener(this);
		input.setAction(() -> input.getText());
		
		// stop button
		Button stop = new Button("Stop");
		stop.setBounds(
			bottomPanel.x + bottomPanel.width - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		stop.setEnabled(false);
		stop.addActionListener(this);
		stop.setAction(() -> {
			stream.stopStream();
			buttons.forEach(b -> {
				if (b.getText().equals("Start")) b.setEnabled(true);
				if (b.getText().equals("Query")) b.setEnabled(true);
			});
			stop.setEnabled(false);
		});
		buttons.set(2, stop);
		
		// start button
		Button start = new Button("Start");
		start.setBounds(
			stop.getBounds().x - buttonSpacing - buttonSize.width,
			bottomPanel.y + buttonSpacing,
			buttonSize.width,
			buttonSize.height
		);
		start.addActionListener(this);
		start.setAction(() -> {
			ArrayList<String> q;
			q = queryFileFilters;
			q = (queryFilters != null)? queryFilters : queryFileFilters;
			stream = new StreamHandler(q, null);
			start.setEnabled(false);
			buttons.forEach(b -> {
				if (b.getText().equals("Start")) b.setEnabled(false);
				if (b.getText().equals("Query")) b.setEnabled(false);
				if (b.getText().equals("Stop")) b.setEnabled(!b.isEnabled());
			});
		});
		buttons.set(3, start);
		
		// movable bound
		movableBoundBounds = new Rectangle(
			leftPanel.x + leftPanel.width + 2,
			leftPanel.y,
			buttonSpacing - 4,
			leftPanel.height
		);
		movableBound = new JLabel();
		movableBound.setBounds(movableBoundBounds);
		movableBound.addMouseMotionListener(this);

		labels.set(1, movableBound);
		
		JLabel label = new JLabel("Twitter tool");
		label.setBounds(		
			topPanel.x + buttonSpacing,
			topPanel.y + buttonSpacing,
			topPanel.width - 2*buttonSpacing,
			(topPanel.height - 2*buttonSpacing)/2
		);
		labels.add(label);
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 40));
		
		// labels in right panel

		// current tweet stream labels
		JLabel stat = new JLabel(" Tweet pool size: 0");
		JLabel title = new JLabel(" Latest processed tweet:");
		JLabel tweet = new JLabel();
		
		stat.setBounds(
			rightPanel.x + buttonSpacing,
			rightPanel.y + buttonSpacing,
			rightPanel.width - (2 * buttonSpacing),
			buttonSize.height
		);
		title.setBounds(
			rightPanel.x + buttonSpacing,
			stat.getBounds().y + stat.getBounds().height + buttonSpacing,
			rightPanel.width - (2 * buttonSpacing),
			buttonSize.height
		);
		tweet.setBounds(
			rightPanel.x + buttonSpacing,
			title.getBounds().y + title.getBounds().height + buttonSpacing,
			rightPanel.width - (2 * buttonSpacing),
			(rightPanel.y + rightPanel.height - buttonSpacing) - (title.getBounds().y + title.getBounds().height + buttonSpacing)
		);
		//title.getBounds().height += tweet.getBounds().height;
		stat.setOpaque(true);
		title.setOpaque(true);
		tweet.setOpaque(true);
		stat.setBackground(Color.white);
		title.setBackground(Color.white);
		tweet.setBackground(Color.white);
//		stat.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
//		title.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		tweet.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		tweet.setFont(new Font(label.getFont().getName(), Font.PLAIN, 14));
		labels.add(stat);
		labels.add(title);
		labels.add(tweet);
	}
	
	private void initPanel() {
		panel = new JPanel() {

			private static final long serialVersionUID = -3391041934845525717L;

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
				
				// bound
				//g.fillRect(movableBoundBounds.x, movableBoundBounds.y, movableBoundBounds.width, movableBoundBounds.height);
				
				super.repaint(1000/3);
			}
		};
		this.add(panel);
		panel.setPreferredSize(new Dimension(width, height));
		panel.addComponentListener(this);
		panel.setLayout(null);
		
		labels.forEach(l -> panel.add(l));
		buttons.forEach(b -> panel.add(b));
		panel.add(input);
		
		this.setTitle("Main");
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		System.out.println(this.getSize().toString());
	}

	@Override
	public void componentResized(ComponentEvent event) {
//		Dimension newSize = event.getComponent().getSize();
//		this.width = newSize.width;
//		this.height = newSize.height;
////		System.out.println(newSize.toString());
//		positionPanels();
//		positionButtons();
	}
	
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent event) {
		((IComponentFunction) event.getSource()).performAction();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point location = e.getPoint();
		
		movableBoundBounds.x = location.x;
		movableBound.setBounds(movableBoundBounds);
		
		//panel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setQueryList(ArrayList<String> list) {
		queryFilters = (list != null)? list : null;
	}

	public void setQueryFile(ArrayList<String> queries) {
		queryFileFilters = queries;
		//queries.forEach(s -> System.out.println(s));
	}
}






