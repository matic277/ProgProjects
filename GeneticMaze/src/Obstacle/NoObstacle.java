package Obstacle;

import java.awt.Graphics2D;

import Subject.Subject;

public class NoObstacle implements IObstacle {

	@Override
	public void draw(Graphics2D g) {
		// do nothing
	}

	@Override
	public boolean checkCollision(Subject s) {
		return false;
	}

}
