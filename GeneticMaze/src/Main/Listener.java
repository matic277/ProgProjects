package Main;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Obstacle.*;
import Renderer.Painter;
import Renderer.SimulationRenderer;

public class Listener implements MouseListener, ChangeListener, MouseMotionListener, ActionListener {

	Environment env;
	public Painter painter;
	
	Object lock = new Object();
	
	// points for drawing lines
	Point lp1;
	Point lp2;
	
	// points for drawing rectangles
	Point rp1, rp2;
	
	// state, indicating is start/end have been selected
	boolean startSelected = false;
	boolean endSelected = false;
	
	public Listener(Environment env_) {
		env = env_;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(painter.populationSizeInput)) {
			System.out.println("change in pop size: " + painter.populationSizeInput.getText());
			Var.populationSize = Integer.parseInt(painter.populationSizeInput.getText());
			
			onClickResetButton();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked -> ("+e.getX()+", "+e.getY()+")");
		
		// Pause button
		if (e.getSource().equals(painter.pauseButton)) {
			onClickPauseButton();
		}
		
		// Refresh button
		if (e.getSource().equals(painter.refreshButton)) {
			onClickRefreshButton();
			// TODO: this button isn't needed anymore,
			// since all inputs are immediately applied
			// change it or not??
		}
		
		// Switch button
		if (e.getSource().equals(painter.switchButton)) {
			onClickSwitchButton();
		}
		
		// Undo button
		else if (e.getSource().equals(painter.undoButton)) {
			onClickUndoButton();
		}
		
		// Clear button
		else if (e.getSource().equals(painter.clearButton)) {
			onClickClearButton();
		}
		
		// Spawn start button
		else if (e.getSource().equals(painter.spawnStartButton)) {
			onClickSpawnStartButton();
		}

		// Spawn end button
		else if (e.getSource().equals(painter.spawnEndButton)) {
			onClickSpawnEndButton();
		}
		
		// Done button
		else if (e.getSource().equals(painter.doneButton)) {
			onClickDoneButton();
		}
		
		// Reset population button
		else if (e.getSource().equals(painter.resetButton)) {
			onClickResetButton();
		}
		
		// remove rectangle if it was clicked
		else {
			if (Var.editType == EditingType.MAZE) { 
				editingRectangleObstacleAction(e);
			}
		}
		
		System.out.println(env.obstacles.size());
	}
	
	private void onClickRefreshButton() {
		System.out.println("Refresh");
		
		env.pop = new Population();
	}

	private void onClickPauseButton() {
		System.out.println("Pause/Unpause");
		
		// if running, just set to false and
		// he logic thread (Environment) will wait
		if (env.isSimulationRunning) {
			env.isSimulationRunning = false;
		} 
		// notify when un-paused
		else {
			synchronized (lock) {
				lock.notify();
				env.isSimulationRunning = true;
			}
		}
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
			env.tmpObs = new RectangleObstacle(rp1, rp2);

		} else {
			// when dragging with right click,
			// draw a straight line, then add
			// it when mouse is released.
			// if its left click, just add it immediately
			lp2 = e.getPoint();

			if (SwingUtilities.isRightMouseButton(e)) {
				lp2 = e.getPoint();
				env.tmpObs = new LineObstacle(lp1, lp2);
			} else {
				env.obstacles.add(new LineObstacle(lp1, lp2));
				
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
				env.addTmpObstacleToObstacles();
			}
			env.tmpObs = new NoObstacle();
		} else {
			// if right click was released, then there should
			// be a straight line waiting to be added to
			// obstacles. The add method worries if there
			// is actually anything to add, so no checking here
			if (SwingUtilities.isRightMouseButton(e)) {
				env.addTmpObstacleToObstacles();
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
		for (int i=0; i<env.obstacles.size(); i++) {
			IObstacle obs = env.obstacles.get(i);
			if (obs instanceof RectangleObstacle) {
				RectangleObstacle rectObs = (RectangleObstacle) obs;
				if (rectObs.getRect().contains(e.getPoint())) {
					env.obstacles.remove(env.obstacles.get(i));
					return;
				}
			}
		}
	}

	
	
	private void onClickUndoButton() {
		env.removeLastObstacle();
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
		System.out.println("Slider changed");
		
		// Angle slider
		if (e.getSource().equals(painter.angleSlider)) {
			onAngleSliderChange(e);
		}
		// Mutation slider
		else if (e.getSource().equals(painter.mutationSlider)) {
			onMutationSliderChange(e);
		}
		// Speed slider
		else if (e.getSource().equals(painter.speedSlider)) {
			onSpeedSliderChange(e);
		}
		
	}
	
	private void onSpeedSliderChange(ChangeEvent e) {
		Var.iterationSleep = painter.speedSlider.getValue();
		
	}

	private void onMutationSliderChange(ChangeEvent e) {
//		JSlider s = (JSlider) e.getSource();
		
		Var.mutationRate = (double)(painter.mutationSlider.getValue()) / 100;
		painter.mutationSliderValueText.setText((int)(Var.mutationRate * 100) + "%");
	}

	private void onAngleSliderChange(ChangeEvent e) {
		Var.vectorAngle = painter.angleSlider.getValue();
		painter.angleSliderValueText.setText(Var.vectorAngle + "°");
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
		
		env.clearObstacles();
		
		lp1 = null; lp2 = null;
		rp1 = null; rp2 = null;
	}
	
	private void onClickDoneButton() {
		System.out.println("Done");
		
		checkStartAndEndCoords(); // TODO: re-implement this method ??
		
		env.getPainter().setRenderer(new SimulationRenderer(env, env.getPainter().getCurrentRenderer()));
		env.getPainter().hideDoneButton();
		painter.enableResetButton();
		
		env.lock = lock;
		env.initPopulation();
		env.start();
		
		env.getPainter().pauseButton.setEnabled(true);
	}

	private void checkStartAndEndCoords() throws Error {
		if (Var.start == null || Var.end == null) throw new Error(" --> Start or end not set! <-- ");
	}

	// not needed
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
}
