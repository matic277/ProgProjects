package Main;

import java.util.ArrayList;
import Obstacle.IObstacle;
import Renderer.Painter;
import Renderer.SimulationRenderer;

public class Environment extends Thread {
	
	ArrayList<IObstacle> obstacles;
	//ArrayList<IObstacle> tmpObs;
	
	Population pop;
	Painter painter;

	public Environment(ArrayList<IObstacle> obstacles, /*ArrayList<IObstacle> tmpObs,*/ Painter painter) {
		this.obstacles = obstacles;
		//this.tmpObs = tmpObs;
		this.painter = painter;
		this.pop = new Population();
		
		Var.environment = this;
		
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

//	public void switchObstacles() {
//		ArrayList<IObstacle> tmp = obstacles;
//		obstacles = tmpObs;
//		tmpObs = tmp;
//	}

//	public void removeLastObstacle() {
//		// TODO Auto-generated method stub
//		
//	}
	
}
