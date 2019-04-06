package Main;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Obstacle.*;

public class Listener implements MouseListener, ChangeListener, MouseMotionListener {

	MazeEditor editor;
	Environment env;
	
	// points for drawing lines
	Point lp1;
	Point lp2;
	
	// points for drawing rectangles
	Point rp1, rp2;
	
	// state, indicating is start/end have been selected
	boolean startSelected = false;
	boolean endSelected = false;
	
	public Listener(MazeEditor editor_) {
		editor = editor_;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked -> ("+e.getX()+", "+e.getY()+")");
		
		// Switch button
		if (e.getSource().equals(editor.painter.switchButton)) {
			onClickSwitchButton();
		}
		
		// Undo button
		else if (e.getSource().equals(editor.painter.undoButton)) {
			onClickUndoButton();
		}
		
		// Clear button
		else if (e.getSource().equals(editor.painter.clearButton)) {
			onClickClearButton();
		}
		
		// Spawn start button
		else if (e.getSource().equals(editor.painter.spawnStartButton)) {
			onClickSpawnStartButton();
		}

		// Spawn end button
		else if (e.getSource().equals(editor.painter.spawnEndButton)) {
			onClickSpawnEndButton();
		}
		
		// Done button
		else if (e.getSource().equals(editor.painter.doneButton)) {
			onClickDoneButton();
		}
		
		// Reset population button
		else if (e.getSource().equals(editor.painter.resetButton)) {
			onClickResetButton();
		}
		
		// remove rectangle if it was clicked
		else {
			if (Var.editType == EditingType.MAZE) { 
				editingRectangleObstacleAction(e);
			}
		}
		
		System.out.println(editor.obstacles.size());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// moving start or end
		if (startSelected) {
			int x = (int) e.getPoint().getX()-Var.start.width/2;
			int y = (int) e.getPoint().getY()-Var.start.height/2;
			Var.start.setLocation(x, y);
		}
		else if (endSelected) {
			int x = (int) e.getPoint().getX() - Var.end.width / 2;
			int y = (int) e.getPoint().getY() - Var.end.height / 2;
			Var.end.setLocation(x, y);
		}
		// drawing lines or rectangles
		else if (Var.editType == EditingType.MAZE) {
			rp2 = e.getPoint();
			editor.tmpObs = new RectangleObstacle(rp1, rp2);

		} else {
			// when dragging with right click,
			// draw a straight line, then add
			// it when mouse is released.
			// if its left click, just add it immediately
			lp2 = e.getPoint();

			if (SwingUtilities.isRightMouseButton(e)) {
				lp2 = e.getPoint();
				editor.tmpObs = new LineObstacle(lp1, lp2);
			} else {
				editor.obstacles.add(new LineObstacle(lp1, lp2));
				
				lp1 = lp2;
				lp2 = null;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (Var.start != null && Var.start.contains(e.getPoint())) {
			startSelected = true;
		} else if (Var.end != null && Var.end.contains(e.getPoint())) {
			endSelected = true;
		}
		else if (Var.editType == EditingType.MAZE) {
			rp1 = e.getPoint();
		} else {
			lp1 = e.getPoint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (Var.editType == EditingType.MAZE) {
			// don't add rectangles that have a diagonal
			// smaller that what's defined in Var.minRectSize
			rp2 = e.getPoint();
			if (rp1 != null && rp1.distance(rp2) > Var.minRectSize) {
				editor.addTmpObstacleToObstacles();
			}
			editor.tmpObs = new NoObstacle();
		} else {
			// if right click was released, then there should
			// be a straight line waiting to be added to
			// obstacles. The add method worries if there
			// is actually anything to add, so no checking here
			if (SwingUtilities.isRightMouseButton(e)) {
				editor.addTmpObstacleToObstacles();
			}
		}
		
		rp1 = null; rp2 = null;
		lp1 = null; lp2 = null;
		endSelected = false;
		startSelected = false;
	}
	
	private void editingRectangleObstacleAction(MouseEvent e) {
		// if an obstacle of type RectangleObstacle
		// has been clicked, remove it from obstacles
		for (int i=0; i<editor.obstacles.size(); i++) {
			IObstacle obs = editor.obstacles.get(i);
			if (obs instanceof RectangleObstacle) {
				RectangleObstacle rectObs = (RectangleObstacle) obs;
				if (rectObs.getRect().contains(e.getPoint())) {
					editor.obstacles.remove(editor.obstacles.get(i));
					return;
				}
			}
		}
	}

	
	
	private void onClickUndoButton() {
		editor.removeLastObstacle();
	}

	private void onClickResetButton() {
		env.resetPopulation();
	}

	private void onClickSpawnEndButton() {
		Rectangle end = new Rectangle(Var.width/2, Var.height/2, Var.endWidth, Var.endHeight);
		Var.end = end;
	}

	private void onClickSpawnStartButton() {
		Rectangle start = new Rectangle(Var.width/2, Var.height/2, Var.startWidth, Var.startHeight);
		Var.start = start;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Var.iterationSleep = editor.painter.speedSlider.getValue();
	}
	
	private void onClickSwitchButton() {
		System.out.println("Switch");

		if (Var.editType == EditingType.FREEHAND) {
			Var.editType = EditingType.MAZE;
		}
		else {
			Var.editType = EditingType.FREEHAND;
		}
		
		lp1 = null; lp2 = null;
		rp1 = null; rp2 = null;
		endSelected = false;
		startSelected = false;
	}
	
	private void onClickClearButton() {
		System.out.println("Clear");
		
		editor.clearObstacles();
		
		lp1 = null; lp2 = null;
		rp1 = null; rp2 = null;
	}
	
	private void onClickDoneButton() {
		System.out.println("Done");
		
		checkStartAndEndCoords(); // TODO: re-implement this method ??
		
		env = new Environment(editor.obstacles, editor.painter);
		env.start();
		
		env.getPainter().hideDoneButton();
		editor.painter.enableResetButton();
	}

	private void checkStartAndEndCoords() throws Error {
		if (Var.start == null || Var.end == null) throw new Error(" --> Start or end not set! <-- ");
	}

	// not needed
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
}
