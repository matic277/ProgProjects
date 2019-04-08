package Renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import Main.Environment;
import Main.Listener;
import Main.Var;

public class Painter extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	Environment env;
	IRenderer renderer;
	JFrame f;
	
	int height = Var.height;
	int width = Var.width;
	
	// upper panel
	public JButton doneButton;
	public JButton switchButton;
	public JButton clearButton;
	public JButton resetButton;
	
	public JButton spawnStartButton;
	public JButton spawnEndButton;
	
	public JButton undoButton;
	
	public JSlider speedSlider;
	
	// right panel
	public JSlider mutationSlider;
	int mutationSliderWidth = 150;
	
	public JTextField populationSizeInput;
	int populationSizeInputWidth = 70;
	
	public JSlider angleSlider;
	int angleSliderWidth = 150;
	
	public JButton refreshButton;
	int refreshButtonWidth = 100;
	int refreshButtonHeight = 50;
	
	public JButton pauseButton;
	int pauseButtonWidth = 100;
	int pauseButtonHeight = 50;
	
	
	public Painter(Environment env_, Listener listener_) {
		env = env_;

		renderer = new EditorRenderer(env);
		
		initUpperControlPanel(listener_);
		initRightControlPanel(listener_);
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
		this.setVisible(true);
		this.addMouseListener(listener_);
		this.addMouseMotionListener(listener_);
		
		f = new JFrame();
		f.add(this);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paintComponent(Graphics gg){
		Graphics2D g = (Graphics2D) gg;

		// renderer, type MazeEditor or SimulationEditor(wraps MazeEditor)
		renderer.draw(g);
		
		try { Thread.sleep(1000/60); }
		catch (InterruptedException e) { e.printStackTrace(); }
		super.repaint();
	}
	
	public void enableResetButton() {
		// this button restarts simulation info and
		// creates a new random population
		// it is already inited, just enable it and
		// display it (takes the place of doneButton)
		this.add(resetButton);
		resetButton.setEnabled(true);
		resetButton.setVisible(true);
	}
	
	public void initRightControlPanel(Listener listener_) {
		// mutation slider
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(0, new JLabel("0%"));
		labelTable.put(100, new JLabel("100%"));
		
		Rectangle mutationSliderBounds = new Rectangle(Var.width - mutationSliderWidth - 20, Var.buttonSpaceHeight + 50, mutationSliderWidth, 50);
		
		mutationSlider = new JSlider(0, 100, 15);
		mutationSlider.setForeground(Color.black);
		mutationSlider.setBounds(mutationSliderBounds);
		
		mutationSlider.setBackground(IRenderer.upperControlPanelClr);
		mutationSlider.setToolTipText("Odds of mutating a single gene");
		mutationSlider.addChangeListener(listener_);
		
		mutationSlider.setMajorTickSpacing(20);
		mutationSlider.setMinorTickSpacing(5);
		mutationSlider.setLabelTable(labelTable);
		mutationSlider.setPaintLabels(true);
		mutationSlider.setPaintTicks(true);
		
		// description text
		JLabel mutationSliderText = new JLabel("Odds of mutating a single gene:");
		Rectangle mutationSliderTextBounds = new Rectangle((int)(mutationSliderBounds.getCenterX() - mutationSliderBounds.getWidth()*2), (int)(mutationSliderBounds.getY()), 200, 50);
		mutationSliderText.setBounds(mutationSliderTextBounds);
		
		// current value (above slider)
		JLabel mutationSliderValueText = new JLabel("125%");
		Rectangle mutationSliderValueTextBounds = new Rectangle((int)(mutationSliderBounds.getCenterX() - 20), (int)mutationSliderBounds.getY()-40, 50, 50);
		mutationSliderValueText.setBounds(mutationSliderValueTextBounds);
		
		this.add(mutationSlider);
		this.add(mutationSliderText);
		this.add(mutationSliderValueText);
		
		// angle slider
		labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(0, new JLabel("0°"));
		labelTable.put(100, new JLabel("180°"));
		
		Rectangle angleSliderBounds = new Rectangle(Var.width - angleSliderWidth - 20, Var.buttonSpaceHeight + (int)mutationSliderBounds.getY() + 30, angleSliderWidth, 50);
		
		angleSlider = new JSlider(0, 100, 15);
		angleSlider.setForeground(Color.black);
		angleSlider.setBounds(angleSliderBounds);
		
		angleSlider.setBackground(IRenderer.upperControlPanelClr);
		angleSlider.setToolTipText("The maximum angle at which subjects can turn");
		angleSlider.addChangeListener(listener_);
		
		angleSlider.setMajorTickSpacing(20);
		angleSlider.setMinorTickSpacing(5);
		angleSlider.setLabelTable(labelTable);
		angleSlider.setPaintLabels(true);
		angleSlider.setPaintTicks(true);
		
		// description text
		JLabel angleSliderText = new JLabel("Size of population (subjects):");
		Rectangle angleSliderTextBounds = new Rectangle((int)(angleSliderBounds.getX() - 200), (int)(angleSliderBounds.getY()), 200, 50);
		angleSliderText.setBounds(angleSliderTextBounds);
		
		// current value (above slider)
		JLabel angleSliderValueText = new JLabel(Var.vectorAngle + "°");
		Rectangle angleSliderValueTextBounds = new Rectangle((int)(angleSliderBounds.getCenterX() - 20), (int)angleSliderBounds.getY()-40, 200, 50);
		angleSliderValueText.setBounds(angleSliderValueTextBounds);
		
		this.add(angleSlider);
		this.add(angleSliderText);
		this.add(angleSliderValueText);
		
		// population size input
		Rectangle populationSizeInputBounds = new Rectangle(Var.width - populationSizeInputWidth - 20, Var.buttonSpaceHeight + (int)angleSliderBounds.getY() + 30, populationSizeInputWidth, 30);
		populationSizeInput = new JTextField(Var.populationSize);
		populationSizeInput.setBounds(populationSizeInputBounds);
		populationSizeInput.addActionListener(listener_);
		
		// description text
		JLabel populationSizeText = new JLabel("Size of population (subjects):");
		Rectangle populationSizeTextBounds = new Rectangle((int)(populationSizeInputBounds.getX() - 200), (int)(populationSizeInputBounds.getY() + 8), 200, 15);
		populationSizeText.setBounds(populationSizeTextBounds);

		this.add(populationSizeInput);
		this.add(populationSizeText);
		
		// refresh button
		Rectangle refreshButtonBounds = new Rectangle(Var.width - refreshButtonWidth - 10, Var.height - refreshButtonHeight - 10, refreshButtonWidth, refreshButtonHeight);
		refreshButton = new JButton("Refresh");
		refreshButton.setBounds(refreshButtonBounds);
		refreshButton.addMouseListener(listener_);
		refreshButton.setToolTipText("Apply the newly set parameters");
		
		this.add(refreshButton);
		
		// pause button
		Rectangle pauseButtonBounds = new Rectangle((int)(refreshButtonBounds.getX() - pauseButtonWidth - 100), (int)(refreshButtonBounds.getY()), pauseButtonWidth, pauseButtonHeight);
		pauseButton = new JButton("Pause");
		pauseButton.setBounds(pauseButtonBounds);
		pauseButton.addMouseListener(listener_);
		pauseButton.setToolTipText("Pause/Unpause simulation");
		
		this.add(pauseButton);
		
	}
	
	// TODO:
	// fix this lazy way of setting the x position of these buttons
	public void initUpperControlPanel(Listener listener_) {
		// finishes editing process, spawns population
		doneButton = new JButton("Done");
		doneButton.addMouseListener(listener_);
		doneButton.setBounds(new Rectangle(10, 10, 100, 30));
		
		// read enableRestartButton method
		resetButton = new JButton("reset pop");
		resetButton.addMouseListener(listener_);
		resetButton.setBounds(doneButton.getBounds());
		resetButton.setEnabled(false);
		resetButton.setVisible(false);
		
		// switches between painting a maze or free-hand drawing, switches renderer
		switchButton = new JButton("Switch");
		switchButton.addMouseListener(listener_);
		switchButton.setBounds(new Rectangle(10 + 100 + 10, 10, 100, 30));
		
		// clears the maze and the free-hand that has been drawn
		clearButton = new JButton("Clear");
		clearButton.addMouseListener(listener_);
		clearButton.setBounds(new Rectangle(10 + 100 + 10 + 100 + 10, 10, 100, 30));
		
		// controls sleeping time when moving population
		speedSlider = new JSlider();
		speedSlider.setBackground(IRenderer.upperControlPanelClr);
		speedSlider.setToolTipText("Simulation speed slider");
		speedSlider.addChangeListener(listener_);
		speedSlider.setBounds(new Rectangle(10 + 100 + 10 + 100 + 10 + 100 + 10, 10, 100, 40));
		
		// spawns a rectangle object on panel that can be dragged around, acts as a starting/end box for population
		spawnStartButton = new JButton("Spawn start");
		spawnStartButton.addMouseListener(listener_);
		spawnStartButton.setBounds(new Rectangle(10 + 100 + 10 + 100 + 10 + 100 + 10 + 100 + 10, 10, 110, 30));
		// |
		spawnEndButton = new JButton("Spawn end");
		spawnEndButton.addMouseListener(listener_);
		spawnEndButton.setBounds(new Rectangle(10 + 100 + 10 + 100 + 10 + 100 + 10 + 100 + 10 + 100 + 20, 10, 100, 30));
		
		// undoes last created obstacle, line or rectangle
		undoButton = new JButton("<--");
		undoButton.setToolTipText("Undo last obstacle placed");
		undoButton.addMouseListener(listener_);
		undoButton.setBounds(new Rectangle(10 + 100 + 10 + 100 + 10 + 100 + 10 + 100 + 10 + 100 + 20 + 100 + 10, 10, 50, 30));
		
		this.add(doneButton);
		this.add(switchButton);
		this.add(clearButton);
		this.add(speedSlider);
		this.add(spawnStartButton);
		this.add(spawnEndButton);
		this.add(undoButton);
	}

	public void hideDoneButton() {
		doneButton.setEnabled(false);
		doneButton.setVisible(false);
	}
	
	public void hideUndoButton() {
		undoButton.setEnabled(false);
		undoButton.setVisible(false);
	}

	public IRenderer getCurrentRenderer() {
		return renderer;
	}
	
	public void repaint() {
		super.repaint();
	}
	
	public void setRenderer(IRenderer renderer_) {
		renderer = renderer_;
	}
}
