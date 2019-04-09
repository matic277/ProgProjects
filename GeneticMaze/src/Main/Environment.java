package Main;

import java.util.ArrayList;
import Obstacle.IObstacle;
import Obstacle.NoObstacle;
import Renderer.EditorRenderer;
import Renderer.GraphicsRenderer;
import Renderer.Painter;

public class Environment extends Thread {
	
	ArrayList<IObstacle> obstacles;
	IObstacle tmpObs;
	
	Population pop;
	GraphicsRenderer graphics;
	
	Object lock;
	
	boolean isSimulationRunning;

	public Environment() {
		Var.environment = this;
		
		isSimulationRunning = true;
		
		obstacles = new ArrayList<IObstacle>(500); // when drawing, objects add up pretty quick, so 500 is fine
		tmpObs = new NoObstacle();
		
		graphics = new GraphicsRenderer(this);
		graphics.start();
		
//		painter = new Painter(this, new Listener(this));
//		painter.setRenderer(new EditorRenderer(this));
	}

	@Override
	public void run() {
		while (true)
		{
			synchronized (lock)
			{	
				while (isSimulationRunning) {
					pop.move();
					sleep();
					
					// all genes have been expressed,
					// do selection and all that stuff
					if (Var.dnaIndex >= Var.DnaLength) {
						Var.dnaIndex = 0;
						
						pop.calculateFitness();
						pop.createNewGeneration();
						pop.resetPositions();
					}
				}
				
				// simulation has been paused, wait here until notified
				// the notify comes from Listener, from button click
				try { lock.wait(); } 
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
	}
	
	public void pauseSimulation() {
		
	}
	
	public void initPopulation() {
		pop = new Population();
	}

	private void sleep() {
		try { Thread.sleep(Var.iterationSleep); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

	public void resetPopulation() {
		this.pop = new Population();
	}

	public ArrayList<IObstacle> getObstacles() {
		return obstacles;
	}

	public Painter getPainter() {
		return graphics.painter;
	}

	public Population getPopulation() {
		return pop;
	}

	public void clearObstacles() {
		obstacles.clear();
		tmpObs = new NoObstacle();
	}

	public void removeLastObstacle() {
		if (obstacles.size() == 0) return;
		obstacles.remove(obstacles.size() - 1);
	}

	public IObstacle getTmpObstacle() {
		return tmpObs;
	}

	public void addTmpObstacleToObstacles() {
		if (tmpObs != null && !(tmpObs instanceof NoObstacle)) {
			obstacles.add(tmpObs);
			tmpObs = new NoObstacle();
		}
	}
	
}
