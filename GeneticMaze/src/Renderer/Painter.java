package Renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import Main.Listener;
import Main.MazeEditor;
import Main.Var;

public class Painter extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	MazeEditor editor;
	IRenderer renderer;
	JFrame f;
	
	int height = Var.height;
	int width = Var.width;
	
	public JButton doneButton;
	public JButton switchButton;
	public JButton clearButton;
	public JButton resetButton;
	
	public JButton spawnStartButton;
	public JButton spawnEndButton;
	
	public JButton undoButton;
	
	public JSlider speedSlider;
	
	public Painter(MazeEditor editor_, Listener listener_) {
		editor = editor_;

		renderer = new EditorRenderer(editor);
		
		initControlPanel(listener_);
		
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
	
	public void initControlPanel(Listener listener_) {
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
		speedSlider.setBackground(IRenderer.controlPanelClr);
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
		
		undoButton = new JButton("<--");
		undoButton.setToolTipText("Undo last obstacle placed");
		undoButton.addMouseListener(listener_);
		undoButton.setBounds(new Rectangle(Var.width - 50 - 10, 10, 50, 30));
		
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
