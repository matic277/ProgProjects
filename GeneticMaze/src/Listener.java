import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.sound.sampled.Line;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Listener implements MouseListener, ChangeListener {

	MazeEditor editor;
	Environment env; // not cool
	
	Point p1;
	Point p2;
	
	//ArrayList<Linee> lines = new ArrayList<Linee>(50);
	
	public Listener(MazeEditor editor_) {
		editor = editor_;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked @ ("+e.getX()+", "+e.getY()+")");
		
		// Switch button
		if (e.getSource().equals(editor.painter.switchButton)) {
			onClickSwitchButton();
		}
		
		// Clear button
		if (e.getSource().equals(editor.painter.clearButton)) {
			onClickClearButton();
		}
		
		// Done button
		else if (e.getSource().equals(editor.painter.doneButton)) {
			onClickDoneButton();
		}
		
		// drawing lines or maze
		else {
			// drawing lines
			if (editor.painter.renderer.getClass().getName().equals("FreeEditorRenderer")) { 
				drawingLineAction(e);
			}
			
			// drawing maze
			else {
				editingMazeAction(e);
			}
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		System.out.println("Slider changed");
		Var.iterationSleep = editor.painter.speedSlider.getValue();
	}
	
	private void editingMazeAction(MouseEvent e) {
		for (int i=0; i<editor.maze.length; i++) {
		for (int j=0; j<editor.maze.length; j++) {
			Rectangle r = new Rectangle(j*Var.squareSize, i*Var.squareSize + Var.buttonSpaceHeight, Var.squareSize, Var.squareSize);
			if (r.contains(e.getX(), e.getY())) {
				setObstacleRectangle(i, j, r, e);
				return;
			}
		}}
	}
	
	private void drawingLineAction(MouseEvent e) {
		if (p1 == null) {
			p1 = new Point(e.getX(), e.getY());
		} else {
			p2 = new Point(e.getX(), e.getY());
			editor.obstacles.add(new LineObstacle(p1, p2));
			
			p1 = p2;
			p2 = null;
		}
	}
	
	private void onClickSwitchButton() {
		System.out.println("Switch");
		editor.recoverTempObstacles();
		if (editor.painter.renderer.getClass().getName().equals("FreeEditorRenderer")) {
			editor.painter.setNewRenderer(new MazeEditorRenderer(editor));
		} else {
			editor.painter.setNewRenderer(new FreeEditorRenderer(editor));
		}
		p1 = null;
		p2 = null;
	}
	
	private void onClickClearButton() {
		System.out.println("Clear");
		editor.clearObstacles();
		p1 = null;
		p2 = null;
		Var.end = null;
		Var.start = null;
	}
	
	private void onClickDoneButton() {
		System.out.println("Done");
		checkStartAndEndCoords();
		new Environment(editor.obstacles, editor.painter).start();
	}
	
	private void checkStartAndEndCoords() throws Error {
		// maybe check these when clicking, but its not nice to implement?
		for (int i=0; i<editor.maze.length; i++) {
		for (int j=0; j<editor.maze.length; j++) {
			if (editor.maze[i][j] == 2) {
				Var.start = new Rectangle(j*Var.squareSize, i*Var.squareSize + Var.buttonSpaceHeight, Var.squareSize, Var.squareSize);
			} else if (editor.maze[i][j] == 3) {
				Var.end = new Rectangle(j*Var.squareSize, i*Var.squareSize + Var.buttonSpaceHeight, Var.squareSize, Var.squareSize);
			}
		}}
		if (Var.start == null || Var.end == null) throw new Error(" --> Start or end not set! <-- ");
	}

	private void setObstacleRectangle(int i, int j, Rectangle r, MouseEvent e) {
		if (leftClick(e)) {
			if (editor.maze[i][j] == 0) {
				editor.maze[i][j] = 1;
				editor.obstacles.add(new RectangleObstacle(r));
			}
			else if (editor.maze[i][j] == 1) {
				editor.maze[i][j] = 0;
				editor.obstacles.remove(new RectangleObstacle(r));
				editor.obstacles.contains(new RectangleObstacle(r));
			} else if (editor.maze[i][j] == 3) {
				editor.maze[i][j] = 0;
				Var.end = null;
			} else if (editor.maze[i][j] == 2) {
				editor.maze[i][j] = 0;
				Var.start = null;
			}
		} else if (rightClick(e)) {
			if (editor.maze[i][j] == 2 || editor.maze[i][j] == 0) {
				if (editor.maze[i][j] == 2) Var.start = null;
				removeAllOtherEnds();
				editor.maze[i][j] = 3;
				Var.end = new Rectangle(j*Var.squareSize, i*Var.squareSize + Var.buttonSpaceHeight, Var.squareSize, Var.squareSize);
			}
			else {
				if (editor.maze[i][j] == 3) Var.end = null;
				removeAllOtherStarts();
				editor.maze[i][j] = 2;
				Var.start = new Rectangle(j*Var.squareSize, i*Var.squareSize + Var.buttonSpaceHeight, Var.squareSize, Var.squareSize);
			}
		}
		editor.printMaze();
		if (Var.start == null) System.out.println("Start je null");
		else System.out.println("Start ni null");
		if (Var.end == null) System.out.println("end je null");
		else System.out.println("end ni null");
	}
	
	private void removeAllOtherEnds() {
		for (int i=0; i<editor.maze.length; i++) {
		for (int j=0; j<editor.maze.length; j++) {
			if (editor.maze[i][j] == 3) editor.maze[i][j] = 0;
		}}
	}

	private void removeAllOtherStarts() {
		for (int i=0; i<editor.maze.length; i++) {
		for (int j=0; j<editor.maze.length; j++) {
			if (editor.maze[i][j] == 2) editor.maze[i][j] = 0;
		}}
	}

	private boolean leftClick(MouseEvent e) { return e.getButton() == MouseEvent.BUTTON1; }
	private boolean rightClick(MouseEvent e) { return e.getButton() == MouseEvent.BUTTON3; }
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
