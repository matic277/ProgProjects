import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public interface IObstacle {
	
	void draw(Graphics2D g);
	boolean checkCollision(Subject s);
	
}

class RectangleObstacle implements IObstacle {
	
	Rectangle rect;
	
	public RectangleObstacle(Point p) {
		rect = new Rectangle(p.x, p.y, Var.squareSize, Var.squareSize);
	}
	public RectangleObstacle(int x, int y) {
		rect = new Rectangle(x, y, Var.squareSize, Var.squareSize);
	}
	public RectangleObstacle(Rectangle rect_) {
		rect = rect_;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fill(rect);
	}

	@Override
	public boolean checkCollision(Subject s) {
		return rect.contains(s.position.x, s.position.y);
	}
	
	@Override
	public boolean equals(Object o) {
        if (o instanceof IObstacle){
        	RectangleObstacle obs = (RectangleObstacle) o;
            return  obs.rect.x == rect.x && obs.rect.y == rect.y &&
            		obs.rect.width == rect.width && obs.rect.height == rect.height;
        }
        return false;
	}
	
}

class LineObstacle implements IObstacle {
	double x1, y1;
	double x2, y2;
	
	public LineObstacle(Point p1, Point p2) {
		x1 = p1.getX(); y1 = p1.getY();
		x2 = p2.getX(); y2 = p2.getY();
	}
	public LineObstacle(int x1_, int y1_, int x2_, int y2_) {
		x1 = x1_; x2 = x2_;
		x2 = x2_; y2 = y2_;
	}
	public LineObstacle(double x1_, double y1_, double x2_, double y2_) {
		x1 = x1_; x2 = x2_;
		x2 = x2_; y2 = y2_;
	}
	
	@Override
	public boolean checkCollision(Subject s) {
		// TODO
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	
	@Override
	public boolean equals(Object o) {
        if (o instanceof IObstacle){
        	LineObstacle obs = (LineObstacle) o;
            return  obs.x1 == x1 && obs.x2 == x2 && 
            		obs.y1 == y1 && obs.y2 == y2;
        }
        return false;
	}
}
