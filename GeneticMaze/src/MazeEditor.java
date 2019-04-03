import java.util.ArrayList;

public class MazeEditor {
	
	int width, height;
	int maze[][];
	
	Painter painter;
	Listener listener;
	
	ArrayList<IObstacle> obstacles;
	ArrayList<IObstacle> tmpObs;
	
	public MazeEditor() {
		maze = new int[Var.mazeHeight][Var.mazeHeight];
		obstacles = new ArrayList<IObstacle>(50);
		tmpObs = new ArrayList<IObstacle>(50);
		
		listener = new Listener(this);
		painter = new Painter(this, listener);
		
		// DEBUG
		new Thread() {
			@Override
			public void run() {
				while (true) {
					
					for (int i=0; i<obstacles.size(); i++) System.out.print(obstacles.get(i).toString());
					System.out.println();System.out.println();
					
					try { Thread.sleep(2000); } 
					catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		}.start();
	}

	public void printMaze() {
		for (int i=0; i<maze.length; i++) {
			for (int j=0; j<maze.length; j++)
				System.out.print(maze[i][j]+" ");
			System.out.println();
		}
	}

	public void clearObstacles() {
		obstacles.clear();
		tmpObs.clear();
		maze = new int[maze.length][maze[0].length];
	}

	public void recoverTempObstacles() {
		ArrayList<IObstacle> tmp = obstacles;
		obstacles = tmpObs;
		tmpObs = tmp;
	}
}
