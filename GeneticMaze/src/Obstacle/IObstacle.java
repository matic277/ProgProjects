package Obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import Subject.Subject;

public interface IObstacle {
	
	Color obstacleClr = Color.DARK_GRAY;
	Color tmpObstacleClr = Color.GRAY;
	
	void draw(Graphics2D g);
	boolean checkCollision(Subject s);
	
}
