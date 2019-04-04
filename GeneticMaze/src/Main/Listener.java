package Main;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Obstacle.*;

public class Listener implements MouseListener, ChangeListener, MouseMotionListener {

	MazeEditor editor;
	Environment env; // handle this differently, singleton maybe?
	
	Point p1;
	Point p2;
	
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
		
		// drawing maze
		else {
			if (Var.editType == EditingType.MAZE) { 
				editingMazeAction(e, true);
			}
		}
		
		System.out.println(editor.obstacles.size());
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
	
	private void editingMazeAction(MouseEvent e, boolean wasClicked) {
		// if mouse dragged, then add rectangles if there
		// is no  intersection with current obstacles
		// if mouse was clicked, see if it was on any
		// current obstacle and remove it if true
		if (wasClicked) {
			for (int i=0; i<editor.obstacles.size(); i++) {
				RectangleObstacle ro = (RectangleObstacle) editor.obstacles.get(i);
				if (ro.getRect().contains(e.getPoint())) {
					editor.obstacles.remove(ro);
					return;
				}
			}
		}
		
		RectangleObstacle newObs = new RectangleObstacle(new Rectangle(e.getX(), e.getY(), Var.squareSize, Var.squareSize));
		
		// exit function if any intersection happens
		for (int i=0; i<editor.obstacles.size(); i++) {
			RectangleObstacle ro = (RectangleObstacle) editor.obstacles.get(i);
			if (ro.intersects(newObs)) return;
		}
		
		// no intersection, add this obstacle
		editor.obstacles.add(newObs);
	}
	
	private void onClickSwitchButton() {
		System.out.println("Switch");
		
		editor.switchObstacles();
		
		if (Var.editType == EditingType.FREEHAND) {
			Var.editType = EditingType.MAZE;
		}
		else {
			Var.editType = EditingType.FREEHAND;
		}
		
		p1 = null;
		p2 = null;
	}
	
	private void onClickClearButton() {
		System.out.println("Clear");
		
		editor.clearObstacles();
		p1 = null;
		p2 = null;
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

	@Override
	public void mouseDragged(MouseEvent e) {
		if (Var.editType == EditingType.FREEHAND) {
			drawLineOrMove_StartOrEnd_Action(e);
		} else {
			addRectangleObstacleOrMoveRectangleAction(e);
		}
	}
	
	private void drawLineOrMove_StartOrEnd_Action(MouseEvent e) {
		if (checkAndMove_StartOrEnd(e)) return;
		
		if (p1 == null) {
			p1 = e.getPoint();
		} else {
			p2 = e.getPoint();
			editor.obstacles.add(new LineObstacle(p1, p2));
			
			p1 = p2;
		}
	}
	
	private void addRectangleObstacleOrMoveRectangleAction(MouseEvent e) {
		// TODO:
		// if dragging too fast, the cursor will "outrun"
		// the Rect, due of this functions "sample rate"
		// being too low.
		
		// check if start or end have been selected first
		if (checkAndMove_StartOrEnd(e)) return;
		
		// check if empty square has been dragged across
		editingMazeAction(e, false);
	}
	
	// returns if start or end have been clicked and moved
	public boolean checkAndMove_StartOrEnd(MouseEvent e) {
		// moving Rect to a Point isn't good, because
		// the center of Rect won't match the point, but 
		// rather the top-left corner of the Rect
		if (Var.end != null) {
			Point cursorLocation = e.getPoint();
			if (Var.end.contains(cursorLocation)) {
				cursorLocation.x -= Var.endWidth / 2;
				cursorLocation.y -= Var.endHeight / 2;
				Var.end.setLocation(cursorLocation);
				return true;
			}
		}
		if (Var.start != null) {
			Point cursorLocation = e.getPoint();
			if (Var.start.contains(cursorLocation)) {
				cursorLocation.x -= Var.startWidth / 2;
				cursorLocation.y -= Var.startHeight / 2;
				Var.start.setLocation(cursorLocation);
				return true;
			}
		}
		return false;
	}

	public void mouseReleased(MouseEvent e) {
		p1 = null;
		p2 = null;
	}

	// not needed for now
	//private boolean leftClick(MouseEvent e) { return e.getButton() == MouseEvent.BUTTON1; }
	//private boolean rightClick(MouseEvent e) { return e.getButton() == MouseEvent.BUTTON3; }
	
	// not needed
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
}
