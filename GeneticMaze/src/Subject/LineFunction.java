package Subject;

import java.awt.Point;

public class LineFunction {
	
	double k, n;
	
	public LineFunction() {
		
	}
	
	public void refresh(Point p1, Point p2) {
		double	dx;
		double	x1 = p1.getX(),
				y1 = p1.getY(),
				x2 = p2.getX(),
				y2 = p2.getY();
		
		// y = kx + n
		// k = (y2 - y1) / (x2 - x1) ; where dx = (x2 - x1) AND dx != 0
		// n = y - kx ; -> plugging in x1,y1 or x2,y2
		dx = x2 - x1;
		
		if (dx == 0) return; // how to handle this?
		
		k = (y2 - y1) / dx;
		n = y1 - k*x1;
	}
	
	public static double[] intersects(LineFunction f1, LineFunction f2) {
		double ix, iy; // intersection coords
		double dk;
		
		// x = (n2 - n1) / (k1 - k2) ; where dk = (k1 - k2) AND dk != 0
		dk = (f1.k - f2.k);
		
		if (dk == 0) return new double[] {-1, -1}; // this means lines don't intersect, same slopes, they are parallel

		ix = (f2.n - f1.n) / dk;
		iy = f1.k * ix + f1.n;
		
		return new double[] {ix, iy};
	}
}
