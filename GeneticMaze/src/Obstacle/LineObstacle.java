package Obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import Main.Var;
import Subject.Subject;

public class LineObstacle implements IObstacle {
	
	// the two points that define this line
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
		double	x1 = s.getPositionX(),
				y1 = s.getPositionY(),
				x2 = s.getDNA().getSeq()[Var.dnaIndex].x + s.getPositionX(),
				y2 = s.getDNA().getSeq()[Var.dnaIndex].y + s.getPositionY();
		
		Line2D.Double line1 = new Line2D.Double(x1, y1, x2, y2);
		Line2D.Double line2 = new Line2D.Double(this.x1, this.y1, this.x2, this.y2);
		
		if (line1.intersectsLine(line2)) return true;
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