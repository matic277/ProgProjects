import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Painter extends JPanel {
	
	MazeEditor editor;
	IRenderer renderer;
	JFrame f;
	
	int height = Var.height,
		width = Var.width;
	
	JButton doneButton;
	JButton switchButton;
	JButton clearButton;
	
	JSlider speedSlider;
	
	public Painter(MazeEditor editor_, Listener listener_) {
		editor = editor_;

		renderer = new MazeEditorRenderer(editor);
		
		initControlPanel(listener_);
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
		this.setVisible(true);
		this.addMouseListener(listener_);
		
		f = new JFrame();
		f.add(this);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paintComponent(Graphics gg){
		Graphics2D g = (Graphics2D) gg;

		// renderer
		renderer.draw(g);
		
		try { Thread.sleep(1000/60); }
		catch (InterruptedException e) { e.printStackTrace(); }
		super.repaint();
		//System.out.println("repainting");
	}

	public void repaint() {
		super.repaint();
	}
	
	public void setNewRenderer(IRenderer renderer_) {
		renderer = renderer_;
	}
	
	public void initControlPanel(Listener listener_) {
		doneButton = new JButton("Done");
		doneButton.addMouseListener(listener_);
		doneButton.setBounds(new Rectangle(10, 10, 100, 30));
		
		// switches between painting a maze or free-hand drawing, switches renderer
		switchButton = new JButton("Switch");
		switchButton.addMouseListener(listener_);
		switchButton.setBounds(new Rectangle(10 + 100 + 10, 10, 100, 30));
		
		// clears the maze and the free-hand that has been drawn
		clearButton = new JButton("Clear");
		clearButton.addMouseListener(listener_);
		clearButton.setBounds(new Rectangle(10 + 100 + 10 + 100 + 10, 10, 100, 30));
		
		speedSlider = new JSlider();
		speedSlider.addChangeListener(listener_);
		speedSlider.setBounds(new Rectangle(10 + 100 + 10 + 100 + 10 + 100 + 10, 10, 100, 40));
		
		this.add(doneButton);
		this.add(switchButton);
		this.add(clearButton);
		this.add(speedSlider);
	}
}
