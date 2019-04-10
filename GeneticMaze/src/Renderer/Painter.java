package Renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	public JSlider angleSlider;
	public JSlider pathDrawingSlider;
	public JTextField populationSizeInput;
	
	public ArrayList<JComponent> componentOptions;
	public ArrayList<JComponent> componentText;
	
	public JLabel mutationSliderToolTip;
	public JLabel angleSliderToolTip;
	
	// bottom buttons
	public JButton refreshButton;
	int refreshButtonWidth = 100;
	int refreshButtonHeight = 30;
	
	public JButton pauseButton;
	int pauseButtonWidth = 100;
	int pauseButtonHeight = 30;
	
	
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
		Rectangle rightPanelBounds = new Rectangle(Var.drawingWidth + 10, Var.buttonSpaceHeight + 10, Var.rightPanelWidth - 20, Var.drawingHeight - 20);
		Rectangle rightPanelTextBounds = new Rectangle((int)(rightPanelBounds.x), (int)(rightPanelBounds.y), (int)(rightPanelBounds.width / 2 - 5), (int)(rightPanelBounds.height));
		Rectangle rightPanelOptionsBounds = new Rectangle((int)(rightPanelBounds.getCenterX() + 5), (int)(rightPanelBounds.y), (int)(rightPanelBounds.width / 2 - 5), (int)(rightPanelBounds.height));
		
		// main rectangle
		JLabel rightPanelBox = new JLabel();
		rightPanelBox.setBounds(rightPanelBounds);
		rightPanelBox.setOpaque(true);
		rightPanelBox.setBackground(new Color(0, 0, 0, 0)); // transparency
		rightPanelBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		// text rectangle
		JLabel rightPanelBoxText = new JLabel();
		rightPanelBoxText.setBounds(rightPanelTextBounds);
		rightPanelBoxText.setOpaque(true);
		rightPanelBoxText.setBackground(new Color(0, 0, 0, 0));
		rightPanelBoxText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		// options rectangle
		JLabel rightPanelBoxOptions = new JLabel();
		rightPanelBoxOptions.setBounds(rightPanelOptionsBounds);
		rightPanelBoxOptions.setOpaque(true);
		rightPanelBoxOptions.setBackground(new Color(0, 0, 0, 0));
		rightPanelBoxOptions.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		this.add(rightPanelBox);
		this.add(rightPanelBoxText);
		this.add(rightPanelBoxOptions);
		
		
		componentOptions = new ArrayList<JComponent>();
		componentText = new ArrayList<JComponent>();
		
		
		initAngleSlider(listener_);
		initMutationSlider(listener_);
		initPathDrawingSlider(listener_);
		initPopulationSizeInput(listener_);
		initPauseButton(listener_);
		initRefreshButton(listener_);
		
		
		// height of each component (right panel-right side, components)
		int componentOptionsHeight = 70;
		
		// rightPanelOptionsBounds
		for (int i=0; i<componentOptions.size(); i++) {
			Rectangle bounds = new Rectangle((int)(rightPanelOptionsBounds.x), (int)(rightPanelOptionsBounds.y + i*(componentOptionsHeight + 20)), (int)(rightPanelOptionsBounds.width), (int)(componentOptionsHeight));
			
			JLabel componentBoundsBackground = new JLabel();
			componentBoundsBackground.setBounds(bounds);
			componentBoundsBackground.setOpaque(true);
			componentBoundsBackground.setBackground(new Color(0, 0, 0, 0));
			componentBoundsBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			this.add(componentBoundsBackground);
			
			// components
			JComponent c = componentOptions.get(i);
			c.setBounds(bounds);
			this.add(c);
		}
		
		// rightPanelTextBounds
		for (int i=0; i<componentText.size(); i++) {
			Rectangle bounds = new Rectangle((int)(rightPanelTextBounds.x), (int)(rightPanelTextBounds.y + i*(componentOptionsHeight + 20)), (int)(rightPanelTextBounds.width), (int)(componentOptionsHeight));
			
			JLabel componentBoundsBackground = new JLabel();
			componentBoundsBackground.setBounds(bounds);
			componentBoundsBackground.setOpaque(true);
			componentBoundsBackground.setBackground(new Color(0, 0, 0, 0));
			componentBoundsBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			this.add(componentBoundsBackground);
			
			JComponent c = componentText.get(i);
			c.setBounds(bounds);
			this.add(c);
		}
		
		// do these after the for loops, not
		// after the initial inits of components
		initToolTips();
	}
	
	// the two JLabels that update when moving 
	// slider for mutations or angle
	private void initToolTips() {
		Rectangle parentBounds, childBounds;
		
		// mutation slider tool-tip
		mutationSliderToolTip = new JLabel(Var.mutationRate + "%");
		parentBounds = mutationSlider.getBounds();
		childBounds = new Rectangle((int)(parentBounds.getCenterX() - 17), parentBounds.y - 15, 34, 15);
		mutationSliderToolTip.setBounds(childBounds);
		mutationSliderToolTip.setVisible(true);
		mutationSliderToolTip.setOpaque(true);
		mutationSliderToolTip.setBackground(Color.red);
		this.add(mutationSliderToolTip);
		
		// angle slider tool-tip
		angleSliderToolTip = new JLabel(Var.vectorAngle +"°");
		parentBounds = angleSlider.getBounds();
		childBounds = new Rectangle((int)(parentBounds.getCenterX() - 15), parentBounds.y - 15, 30, 15);
		angleSliderToolTip.setBounds(childBounds);
		angleSliderToolTip.setVisible(true);
		angleSliderToolTip.setOpaque(true);
		angleSliderToolTip.setBackground(Color.red);
		this.add(angleSliderToolTip);
		
	}

	private void initRefreshButton(Listener listener_) {
		Rectangle refreshButtonBounds = new Rectangle(Var.width - refreshButtonWidth - 10, Var.height - refreshButtonHeight - 10, refreshButtonWidth, refreshButtonHeight);
		refreshButton = new JButton("Refresh");
		refreshButton.setBounds(refreshButtonBounds);
		refreshButton.addMouseListener(listener_);
		refreshButton.setToolTipText("Apply the newly set parameters");
		
		this.add(refreshButton);
	}

	private void initPauseButton(Listener listener_) {
		// pause button
		Rectangle pauseButtonBounds = new Rectangle((int)(Var.width - refreshButtonWidth - 10 - pauseButtonWidth - 10), (int)(Var.height - refreshButtonHeight - 10), pauseButtonWidth, pauseButtonHeight);
		pauseButton = new JButton("Pause");
		pauseButton.setBounds(pauseButtonBounds);
		pauseButton.addMouseListener(listener_);
		pauseButton.setToolTipText("Pause/Unpause simulation");
		pauseButton.setEnabled(false);
		
		this.add(pauseButton);
		
	}

	public void initPathDrawingSlider(Listener listener_) {
		// switch check-box
		Hashtable<Integer, JLabel> ht = new Hashtable<Integer, JLabel>();
		ht.put(1, new JLabel("On"));
		ht.put(0, new JLabel("Off"));
		
		pathDrawingSlider = new JSlider(0, 1, ((Var.pathDrawing)? 1 : 0));
		pathDrawingSlider.addChangeListener(listener_);
		
		pathDrawingSlider.setMajorTickSpacing(1);
		pathDrawingSlider.setLabelTable(ht);
		pathDrawingSlider.setPaintLabels(true);
		pathDrawingSlider.setPaintTicks(true);
		
		pathDrawingSlider.setBackground(Color.LIGHT_GRAY);
		
		// description text
		JLabel pathDrawingSliderText = new JLabel("Enable or disable path drawing:");

		componentText.add(pathDrawingSliderText);
		componentOptions.add(pathDrawingSlider);
	}
	
	public void initPopulationSizeInput(Listener listener_) {
		// population size input
		populationSizeInput = new JTextField();
		populationSizeInput.setText(Var.populationSize + "");
		populationSizeInput.addActionListener(listener_);
		
		// description text
		JLabel populationSizeText = new JLabel("Size of population (subjects):");
		
		componentText.add(populationSizeText);
		componentOptions.add(populationSizeInput);
	}
	
	public void initMutationSlider(Listener listener_) {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(0, new JLabel("0%"));
		labelTable.put(50, new JLabel("50%"));
		labelTable.put(100, new JLabel("100%"));
		
		mutationSlider = new JSlider(0, 100, (int)(Var.mutationRate * 100));
		mutationSlider.setForeground(Color.black);
		
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
		
		componentText.add(mutationSliderText);
		componentOptions.add(mutationSlider);
	}
	
	public void initAngleSlider(Listener listener_) {
		// angle slider
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(0, new JLabel("0°"));
		labelTable.put(45, new JLabel("45°"));
		labelTable.put(90, new JLabel("90°"));
		labelTable.put(180, new JLabel("180°"));

		angleSlider = new JSlider(0, 180, Var.vectorAngle);
		angleSlider.setForeground(Color.black);
		
		angleSlider.setBackground(IRenderer.upperControlPanelClr);
		angleSlider.setToolTipText("The maximum angle at which subjects can turn");
		angleSlider.addChangeListener(listener_);
		
		angleSlider.setMajorTickSpacing(20);
		angleSlider.setMinorTickSpacing(10);
		angleSlider.setLabelTable(labelTable);
		angleSlider.setPaintLabels(true);
		angleSlider.setPaintTicks(true);
		
		// description text
		JLabel angleSliderText = new JLabel("Maximum angle of rotation:");
		
		componentText.add(angleSliderText);
		componentOptions.add(angleSlider);
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
		speedSlider = new JSlider(0, 100, (int)Var.iterationSleep);
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
