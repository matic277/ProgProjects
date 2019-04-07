package Main;

import java.util.ArrayList;
import Obstacle.IObstacle;
import Obstacle.NoObstacle;
import Renderer.Painter;

public class MazeEditor {
	
	int width, height;
	
	Painter painter;
	Listener listener;
	
	ArrayList<IObstacle> obstacles;
	IObstacle tmpObs;
	
	public MazeEditor() {
		obstacles = new ArrayList<IObstacle>(500); // when drawing, objects add up pretty quick, so 500 is fine
		tmpObs = new NoObstacle();
		
		listener = new Listener(this);
		painter = new Painter(this, listener);
		
		// DEBUG - TESTING
//		new Thread() {
//			@Override
//			public void run() {
//				while (true) {				
////					for (int i=0; i<obstacles.size(); i++) System.out.print(obstacles.get(i).toString());
////					System.out.println();System.out.println();
//					
//					Vector v = new Vector(1, 2);
//					v.set(10, 10);
//					Vector.draw((Graphics2D)painter.getGraphics(), v, Color.black);
//					v.rotate();
//					Vector.draw((Graphics2D)painter.getGraphics(), v, Color.red);
//					
//					try { Thread.sleep(3); } 
//					catch (InterruptedException e) { e.printStackTrace(); }
//				}
//			}
//		}.start();
	}

	public void clearObstacles() {
		obstacles.clear();
		tmpObs = new NoObstacle();
	}

	public ArrayList<IObstacle> getObstacles() {
		return obstacles;
	}

//	public void switchObstacles() {
//		ArrayList<IObstacle> tmp = obstacles;
//		obstacles = tmpObs;
//		tmpObs = tmp;
//	}

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
