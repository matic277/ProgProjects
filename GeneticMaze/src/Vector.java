import java.util.Random;

public class Vector {
	
	double x, y;
	
	public Vector(double x_, double y_) {
		x = x_;
		y = y_;
	}
	
	public Vector(int min, int max) {
		Random r = new Random();
		x = r.nextInt(max - min) + min;
		y = r.nextInt(max - min) + min;
	}
	
	public void set(Vector v) { x = v.x; y = v.y; }
	public void set(int x_, int y_) { x = x_; y = y_; }
	public void set(double x_, double y_) { x = x_; y = y_; }
	
	public void add(Vector v) {
		x += v.x;
		y += v.y;
	}
	
	public void sub(Vector v) {
		x -= v.x;
		y -= v.y;
	}
	
	public void multi(float n) {
		x *= n;
		y *= n;
	}
	
	public void div(double n) {
		x /= n;
		y /= n;
	}
	
	public double mag() {
		return Math.sqrt(x*x + y*y);
	}
	
	public void norm() {
		double v_mag = mag();
		if (v_mag > 0) {
			div(v_mag);
		}
	}
	
	public static boolean compare(Vector v1, Vector v2) {
		if (v1.x == v2.x && v1.y == v2.y) return true;
		return false;
	}
	
	public String toString() {
		return "(" + (int)x + ", " + (int)y + ")";
	}
}
