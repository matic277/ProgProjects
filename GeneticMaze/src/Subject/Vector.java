package Subject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import Main.Var;

public class Vector {
	
	public double x;
	public double y;
	
	public Vector(double x_, double y_) {
		x = x_;
		y = y_;
	}
	public Vector(int min, int max) {
		Random r = new Random();
		x = r.nextInt(max - min) + min;
		y = r.nextInt(max - min) + min;
	}
	public Vector(Vector v) {
		x = v.x; y = v.y;
	}
	
	public void set(Vector v) { x = v.x; y = v.y; }
	public void set(int x_, int y_) { x = x_; y = y_; }
	public void set(double x_, double y_) { x = x_; y = y_; }
	
	// rotates for a random value from [0, Var.vectorAngle]
	// in both directions (clockwise or counter-clockwise)
	public void rotate() {
		Random r = new Random();
		int da = 0;
		double angle = Var.vectorAngle;
		
		// rotate from [0, angle]
		if (r.nextDouble() < 0.5) {
			angle = r.nextInt(Var.vectorAngle + 1);
		}
		// rotate from [360-angle, 360]
		else {
			da = 360  - Var.vectorAngle;
			angle = da + r.nextInt(Var.vectorAngle +1 );
		}
		
		x = x * Math.cos(Math.toRadians(angle)) - y * Math.sin(Math.toRadians(angle));
		y = x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));
	}
	
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
	
	public static void draw(Graphics2D g, Vector v, Color c) {
		g.setColor(c);
		g.drawLine(300, 300, (int)v.x+300, (int)v.y+300);
	}
	
	public static boolean compare(Vector v1, Vector v2) {
		if (v1.x == v2.x && v1.y == v2.y) return true;
		return false;
	}
	
	public String toString() {
		return "(" + (int)x + ", " + (int)y + ")";
	}
}
