package Obstacle;

import java.awt.Graphics2D;
import Subject.Subject;

public interface IObstacle {
	
	void draw(Graphics2D g);
	boolean checkCollision(Subject s);
	
}
