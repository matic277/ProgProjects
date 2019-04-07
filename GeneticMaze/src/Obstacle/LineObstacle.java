package Obstacle;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import Subject.Subject;

public class LineObstacle implements IObstacle {
	
	// the two points that define this line
	Point p1, p2;

	public LineObstacle(Point p1_, Point p2_) {
		p1 = p1_; p2 = p2_;
	}
//	public LineObstacle(int x1, int y1, int x2, int y2) {
//		p1 = new Point2D.Double((double)x1, (double)y1);
//		p1 = new Point2D.Double((double)x2, (double)y2);
//	}
//	public LineObstacle(double x1, double y1, double x2, double y2) {
//		p1 = new Point2D.Double(x1, y1);
//		p1 = new Point2D.Double(x2, y2);
//	}
	
	@Override
	public boolean checkCollision(Subject s) {
		Point2D.Double sp1 = s.getPosition();
		Point2D.Double sp2 = s.getNextPosition();
		
		Line2D.Double subjectLine = new Line2D.Double(sp1, sp2);
		Line2D.Double thisLine  = new Line2D.Double(p1, p2);
		
		if (subjectLine.intersectsLine(thisLine)) return true;
		return false;		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(obstacleClr);
		g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
	}
	
	@Override
	public boolean equals(Object o) {
        if (o instanceof IObstacle){
        	LineObstacle obs = (LineObstacle) o;
            return  obs.p1.x == p1.x && obs.p1.y == p1.y && 
            		obs.p2.x == p2.x && obs.p2.y == p2.y;
        }
        return false;
	}
}