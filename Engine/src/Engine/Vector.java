package Engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.Random;


public class Vector {
	
	public double x;
	public double y;
	
	DecimalFormat rounder = new DecimalFormat("#.#");
	
	public Vector(double x_, double y_) {
		x = x_;
		y = y_;
	}
	public Vector(Vector v) {
		x = v.x; y = v.y;
	}
	public Vector() {
		Random r = new Random();
		x = r.nextDouble();
		y = r.nextDouble();
		x = r.nextBoolean()? -x : x;
		y = r.nextBoolean()? -y : y;
	}
	
	public void set(Vector v) { x = v.x; y = v.y; }
	public void set(Point p) { x = p.x; y = p.y; }
	public void set(int x_, int y_) { x = x_; y = y_; }
	public void set(double x_, double y_) { x = x_; y = y_; }
	
	// rotates for a random value from [0, Var.vectorAngle]
	// in both directions (clockwise or counter-clockwise)
	public void rotateRandomly(int maxRotation) {
		if (maxRotation == 0) return;
		
		double currentMag = mag();
		
		Random r = new Random();
		int da = 0;
		double angle = maxRotation;
		
		// rotate from [0, angle]
		if (r.nextDouble() < 0.5) {
			angle = r.nextInt(maxRotation + 1);
		}
		// rotate from [360-angle, 360]
		else {
			da = 360  - maxRotation;
			angle = da + r.nextInt(maxRotation +1 );
		}
		
		x = x * Math.cos(Math.toRadians(angle)) - y * Math.sin(Math.toRadians(angle));
		y = x * Math.sin(Math.toRadians(angle)) + y * Math.cos(Math.toRadians(angle));
		
		this.norm();
		this.multi(currentMag);
	}
	
	public void rotate(double ammount) {
		if (ammount == 0) return;
		
		double currentMag = mag();

		x = x * Math.cos(Math.toRadians(ammount)) - y * Math.sin(Math.toRadians(ammount));
		y = x * Math.sin(Math.toRadians(ammount)) + y * Math.cos(Math.toRadians(ammount));
		
		this.norm();
		this.multi(currentMag);
	}
	
	public void add(Vector v) {
		x += v.x;
		y += v.y;
	}
	
	public void sub(Vector v) {
		x -= v.x;
		y -= v.y;
	}
	
	public void multi(double n) {
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
		return v1.x == v2.x && v1.y == v2.y;
	}
	
	public double distance(Vector v) {
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public String toString() {
		return "(" + rounder.format(x) + ", " + rounder.format(y) + ")";
	}
}
