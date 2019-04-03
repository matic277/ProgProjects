import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JButton;

public class Environment extends Thread {
	
	ArrayList<IObstacle> obstacles;
	
	Population pop;
	Painter painter;

	public Environment(ArrayList<IObstacle> obstacles, Painter painter) {
		this.obstacles = obstacles;
		this.painter = painter;
		this.pop = new Population();
		
		//initObstacles();
		
		painter.doneButton.setEnabled(false);
		painter.doneButton.setVisible(false);
		//painter.setPreferredSize(new Dimension(Var.width, Var.height));
		//painter.f.pack();
		//painter.removeMouseListener(painter.getMouseListeners()[0]);
		painter.renderer = new SimulationRenderer(this);
	}
	
	public void initObstacles() {
		/*
		// count them
		// TODO: make this an arraylist and delete this garbo code
		int num = 0;
		for (int i=0; i<maze.length; i++)
		for (int j=0; j<maze[0].length; j++) if (maze[i][j] == 1) num++;
		
		obstacles = new Rectangle[num];
		for (int i=0, ind=0; i<maze.length; i++)
		for (int j=0; j<maze[0].length; j++) if (maze[i][j] == 1) {
			obstacles[ind] = new Rectangle(j*Var.squareSize, i*Var.squareSize, Var.squareSize, Var.squareSize);
			ind++;
		}
		Var.obs = obstacles;
		*/
	}

	private void sleep() {
		try { Thread.sleep((long)Var.iterationSleep); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

	@Override
	public void run() {
		while (true) {
			pop.move();
			sleep();
			
			if (Var.dnaIndex == Var.DnaLength) {
				pop.calculateFitness();
				pop.createNewGeneration();
				pop.resetPositions();
			}
		}
	}
	
}
