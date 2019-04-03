import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class MazeEditorRenderer implements IRenderer {
	
	MazeEditor editor;

	Color border;
	Color path;
	Color start;
	Color finish;
	
	int squareSize;
	
	public MazeEditorRenderer(MazeEditor editor_) {
		editor = editor_;
		squareSize = Var.squareSize;
		
		border = Color.BLACK;
		path = Color.gray;
		start = Color.green;
		finish = Color.red;
	}

	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawControlPanel(g);
		drawMaze(g);
	}

	private void drawMaze(Graphics2D g) {
		/*
		for (int i=0; i<env.maze.length; i++) {
			for (int j=0; j<env.maze.length; j++) {
				if (env.maze[i][j] == 0) drawPath(g, i, j);
				else if (env.maze[i][j] == 1) drawBorder(g, i, j);
				else if (env.maze[i][j] == 2) drawStart(g, i, j);
				else if (env.maze[i][j] == 3) drawFinish(g, i, j);
			}
		}
		*/
		for (IObstacle o : editor.obstacles) o.draw(g);
		
		drawStart(g);
		drawFinish(g);
	}

	private void drawControlPanel(Graphics2D g) {
		g.setColor(Color.PINK);
		g.fillRect(0, 0, Var.width, Var.buttonSpaceHeight);
	}

	private void drawBackground(Graphics2D g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, Var.width, Var.height);
	}

	private void drawStart(Graphics2D g) {
		/*
		g.setColor(start);
		g.fillRect(j*squareSize, i*squareSize + Var.buttonSpaceHeight, squareSize, squareSize);
		*/
		if (Var.start == null) return;
		g.setColor(start);
		g.fillRect(Var.start.x, Var.start.y, Var.start.width, Var.start.height);
	}

	private void drawBorder(Graphics2D g, int i, int j) {
		g.setColor(border);
		g.fillRect(j*squareSize, i*squareSize + Var.buttonSpaceHeight, squareSize, squareSize);
	}

	private void drawPath(Graphics2D g, int i, int j) {
		g.setColor(path);
		g.fillRect(j*squareSize, i*squareSize + Var.buttonSpaceHeight, squareSize, squareSize);
	}
	
	private void drawFinish(Graphics2D g) {
		/*
		g.setColor(finish);
		g.fillRect(j*squareSize, i*squareSize + Var.buttonSpaceHeight, squareSize, squareSize);
		*/
		if (Var.end == null) return;
		g.setColor(finish);
		g.fillRect(Var.end.x, Var.end.y, Var.end.width, Var.end.height);
	}
	

}
