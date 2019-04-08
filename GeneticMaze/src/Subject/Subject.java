package Subject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import Main.Var;
import Obstacle.IObstacle;

public class Subject {
	
	double fitness;
	boolean colided;
	int id;
	
	Vector position;
	DNA dna;
	
	Color clr;
	
	// coordinates of center of this subject
	// used for drawing purposes
	int cx, cy;

	public Subject(int id_) {
		dna = new DNA(-1);
		id = id_;
		
		// set starting position - same as resetting position
		position = new Vector();
		resetPosition();
		
//		this.printDna();
		
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
		
		checkCollision();
		
		if (colided) return;
		
		position.add(dna.seq[Var.dnaIndex]);
		
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
		position.set(Var.start.getCenterX(), Var.start.getCenterY());
		colided = false;
	}
	
	public void printDna() {
		System.out.println(dna.toString());
	}

	public static Subject mateSubjects(Subject p1, Subject p2, int childId) {
		Subject child = new Subject(childId);
		child.dna = DNA.combineDNA(p1.dna, p2.dna);
		return child;
	}
	
	public void mutateSubject() {
		if (Var.mutation) dna.mutate();
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
	
	public Point2D.Double getPosition() {
		return new Point2D.Double(position.x, position.y);
	}
	
	public Point2D.Double getNextPosition() {
		return new Point2D.Double(
			position.x + dna.seq[Var.dnaIndex].x,
			position.y + dna.seq[Var.dnaIndex].y
		);
	}

	public DNA getDNA() {
		return dna;
	}

}
