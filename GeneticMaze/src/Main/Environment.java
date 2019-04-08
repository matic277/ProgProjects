package Main;

import java.util.ArrayList;
import Obstacle.IObstacle;
import Obstacle.NoObstacle;
import Renderer.EditorRenderer;
import Renderer.Painter;

public class Environment extends Thread {
	
	ArrayList<IObstacle> obstacles;
	IObstacle tmpObs;
	
	Population pop;
	Painter painter;
	
	Object lock;
	
	boolean isSimulationRunning;

	public Environment() {
		Var.environment = this;
		
		isSimulationRunning = true;
		
		obstacles = new ArrayList<IObstacle>(500); // when drawing, objects add up pretty quick, so 500 is fine
		tmpObs = new NoObstacle();
		
		painter = new Painter(this, new Listener(this));
		painter.setRenderer(new EditorRenderer(this));
	}

	@Override
	public void run() {
		while (true) {
			synchronized (lock)
			{
				System.out.println("im here");
	//			try {
	//				lock.wait();
	//			} catch (InterruptedException e) {
	//				e.printStackTrace();
	//			}
				
				while (isSimulationRunning) {
					pop.move();
					sleep();
					
					// all genes have been expressed,
					// do selection and all that stuff
					if (Var.dnaIndex == Var.DnaLength) {
						pop.calculateFitness();
						pop.createNewGeneration();
						pop.resetPositions();
						
						Var.dnaIndex = 0;
					}
				}
				
				
			}
			System.out.println("loop");
		}
	}
	
	public void pauseSimulation() {
		
	}
	
	public void initPopulation() {
		pop = new Population();
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
