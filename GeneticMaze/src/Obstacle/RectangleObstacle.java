package Obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import Main.Var;
import Subject.Subject;

public class RectangleObstacle implements IObstacle {
	
	Rectangle rect;
	Color clr;
	
	public RectangleObstacle(Point p) {
		rect = new Rectangle(p.x, p.y, Var.squareSize, Var.squareSize);
		setColor();
	}
	public RectangleObstacle(int x, int y) {
		rect = new Rectangle(x, y, Var.squareSize, Var.squareSize);
		setColor();
	}
	public RectangleObstacle(Rectangle rect_) {
		rect = rect_;
		setColor();
	}
	public void setColor() {
		clr = Color.DARK_GRAY;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(clr);
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
