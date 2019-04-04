package Subject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import Main.Var;
import Obstacle.IObstacle;

public class Subject {
	
	double fitness;
	boolean colided;
	int id;
	
	Vector position;
	DNA dna;
	
	LineFunction lf;
	
	Color clr;
	
	// coordinates of center of this subject
	// used for drawing purposes
	int cx, cy;

	public Subject(int id_) {
		dna = new DNA(-1);
		id = id_;
		
		// set starting position - same as resetting position
		position = new Vector(0, 1);
		resetPosition();
		
		lf = new LineFunction();
		
//		Random rn = new Random();
//		int r = rn.nextInt(256);
//		int g = rn.nextInt(256);
//		int b = rn.nextInt(256);
//		clr = new Color(r, g, b);
		
		clr = Color.black;
		
		fitness = 0;
		colided = false;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(clr);
		g.fillOval(cx, cy, Var.subjectSize, Var.subjectSize);
	}
	
	public void move() {
		if (colided) return;
		
		lf.refresh(
			new Point(
				(int)position.x,
				(int)position.y
			),
			new Point(
				(int)dna.seq[Var.dnaIndex].x,
				(int)dna.seq[Var.dnaIndex].y
			)
		);
		position.add(dna.seq[Var.dnaIndex]);
		
		checkCollision();
		updateDrawingCoordinates();
	}
	
	
	private void updateDrawingCoordinates() {
		cx = (int)(position.x - (Var.subjectSize / 2));
		cy = (int)(position.y - (Var.subjectSize / 2));
	}

	public void checkCollision() {
		ArrayList<IObstacle> obstacles = Var.environment.getObstacles();
		for (int i=0; i<obstacles.size(); i++) {
			if (obstacles.get(i).checkCollision(this)) {
				colided = true;
				position.sub(dna.seq[Var.dnaIndex]);
				return;
			}
		}
	}
	
	public void setFitness() {
		// TODO: too basic for some problems
		double endx = Var.end.getCenterX();
		double endy = Var.end.getCenterY();
		double dx = endx - position.x;
		double dy = endy - position.y;
		
		fitness = Math.sqrt(dx*dx + dy*dy);
		
		if (colided) fitness *= 2;
	}

	public void resetPosition() {
		position.set(Var.start.x, Var.start.y);
		colided = false;
	}
	
	public void printDna() {
		System.out.println(dna.toString());
	}

	public static Subject mateSubjects(Subject p1, Subject p2, int childId) {
		Subject child = new Subject(childId);
		child.dna = DNA.combineDNA(p1.dna.seq, p2.dna.seq);
		return child;
	}

	public double getFitness() {
		return fitness;
	}

	public double getPositionX() {
		return position.x;
	}

	public double getPositionY() {
		return position.y;
	}

	public DNA getDNA() {
		return dna;
	}

}
