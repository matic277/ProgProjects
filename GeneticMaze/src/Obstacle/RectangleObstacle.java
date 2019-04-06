package Obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import Main.Var;
import Subject.Subject;

public class RectangleObstacle implements IObstacle {
	
	Rectangle rect;
	
	public RectangleObstacle(Point p) {
		rect = new Rectangle(p.x, p.y, Var.squareSize, Var.squareSize);
	}
	public RectangleObstacle(Point p1, Point p2) {
		// which has the smaller Y coordinate?
		// that one is the upper one, similar for x
		int x = 0, y = 0;
		
		if (p1.y < p2.y) y = p1.y;
		else y = p2.y;
		if (p1.x < p2.x) x = p1.x;
		else x = p2.x;

		rect = new Rectangle(x, y, Math.abs(p1.x-p2.x), Math.abs(p1.y-p2.y));
	}
	public RectangleObstacle(int x, int y) {
		rect = new Rectangle(x, y, Var.squareSize, Var.squareSize);
	}
	public RectangleObstacle(Rectangle rect_) {
		rect = rect_;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(obstacleClr);
		g.fill(rect);
	}

	@Override
	public boolean checkCollision(Subject s) {
		return rect.contains(s.getPositionX(), s.getPositionY());
	}
	
	public boolean intersects(RectangleObstacle r) {
		double	cx1 = rect.getCenterX(),
				cy1 = rect.getCenterY(),
				cx2 = r.rect.getCenterX(),
				cy2 = r.rect.getCenterY(),
				dist = getDistance(cx1, cy1, cx2, cy2);
		
		if (dist < (Var.squareSize / 2)) return true;
		return false;
	}
	
//	public boolean intersects(RectangleObstacle r) {
//		return rect.intersects(r.rect);
//	}
	
	private double getDistance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2, dy = y1 - y2;
		return Math.sqrt(dx*dx + dy*dy);
	}

	@ Override
	public String toString() {
		return "(" + rect.x + ", " + rect.y + "," + rect.width + ", " + rect.height + ")";
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
}
