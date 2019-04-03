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
	
	double y, k, x, n;
	
	public LineObstacle(Point p1, Point p2) {
		x1 = p1.getX(); y1 = p1.getY();
		x2 = p2.getX(); y2 = p2.getY();
		
		defineLinearFunction();
	}
	public LineObstacle(int x1_, int y1_, int x2_, int y2_) {
		x1 = x1_; x2 = x2_;
		x2 = x2_; y2 = y2_;
	}
	public LineObstacle(double x1_, double y1_, double x2_, double y2_) {
		x1 = x1_; x2 = x2_;
		x2 = x2_; y2 = y2_;
	}
	
	public void defineLinearFunction() {
		// y = kx + n  ->  n = y - kx
		// k = (y2 - y1) / (x2 - x1)
		k = (y2 - y1) / (x2 - x1);
		n = y1 - k * x1;
		
		// TODO: if user inputs a straight vertical line, k and n are Infinity -> not good
	}
	
	@Override
	public boolean checkCollision(Subject s) {
		// see if line from (position)----(vector) and this line intersect
		// then see if the intersection coords fall into both lines bounds
		// if they do, collision has occured
		// also, freeze the subject at the place of intersection
		
		//first, define subjects line of path / movement
		double	sy1 = s.position.y, // y1 coord of subject |__ point A(x1, y1)
				sx1 = s.position.x, // x1 coord of subject |
				
				sy2 = s.dna.seq[Var.dnaIndex].y, // y2 coord of subject |__ point B(x1, y2)
				sx2 = s.dna.seq[Var.dnaIndex].y; // x2 coord of subject |
		
		double sk, sn;	// k and n for linear function for subject
						// y = kx + n  ->  n = y - kx
						// k = (y2 - y1) / (x2 - x1)
		sk = (sy2 - sy1) / (sx2 - sx1);
		sn = sy1 - sk * sx1;
		
		// so the subjects line is defined as:
		// sy = sk * sx + sn  ->  ( sy1 = sk * sx1 + sn )
		
		// see if subjects line intersects this line
		// example equation:
		// line one: y1 = k1 * x1 + n1
		// line two: y2 = k2 * x2 + n2
		// intersection equation:
		// k1 * x1 + n1 = k2 * x2 + n2	-> ( (sk * x + sn) = (k * x + n) )
		// then find x
		// k1*x1 - k2*x2 = n2 - n1		-> ( (sk - k)*x =  n - sn)
		// x = (n2 - n1) / (k1 - k2)	-> ( x = (n - sn) / (sk - k) )
		double ix, iy; // intersection coords
		
		ix = (n - sn) / (sk - k);
		
		// now calcucalte iy by plugging coord ix subjects line
		// (or this lines)
		// y = kx + n
		iy = k * ix + n;
		
		// we have coords of intersection as (ix, iy)
		// TODO: see if they fit on both lines
		
		
						
				
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
	
	public String toString() {
		return ("y = "+k+" * x + " + n);
	}
}
