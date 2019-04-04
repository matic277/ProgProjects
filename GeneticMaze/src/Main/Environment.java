package Main;

import java.util.ArrayList;
import Obstacle.IObstacle;
import Renderer.Painter;
import Renderer.SimulationRenderer;

public class Environment extends Thread {
	
	ArrayList<IObstacle> obstacles;
	
	Population pop;
	Painter painter;

	public Environment(ArrayList<IObstacle> obstacles, Painter painter) {
		this.obstacles = obstacles;
		this.painter = painter;
		this.pop = new Population();
		
		Var.environment = this; // making this class statically available is not that great? make it a singleton?
		
		painter.setRenderer(new SimulationRenderer(this, painter.getCurrentRenderer()));
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

	private void sleep() {
		try { Thread.sleep((long)Var.iterationSleep); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

	public void resetPopulation() {
		this.pop = new Population();
	}

	public ArrayList<IObstacle> getObstacles() {
		return obstacles;
	}

	public Painter getPainter() {
		return painter;
	}

	public Population getPopulation() {
		return pop;
	}
	
}
