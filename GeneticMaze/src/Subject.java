import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Subject {
	
	double fitness;
	boolean colided;
	int id;
	
	Vector position;
	DNA dna;
	
	Color clr;

	public Subject(int id_) {
		dna = new DNA(-1);
		id = id_;
		
		// set starting position - same as resetting position
		position = new Vector(0, 1);
		resetPosition();
		
		Random rn = new Random();
		int r = rn.nextInt(256);
		int g = rn.nextInt(256);
		int b = rn.nextInt(256);
		clr = new Color(r, g, b);
		
		fitness = 0;
		colided = false;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(clr);
		g.fillRect((int)position.x, (int)position.y, 15, 15);
		//g.drawString(position.toString(), (int)position.x, (int)position.y);
		
		//System.out.println("drawing at : "+position.toString());
	}
	
	public void move() {
		if (colided) return;
		checkCollision();
		
		
		
		position.add(dna.seq[Var.dnaIndex]);
	}
	
	public void checkCollision() {
		/*
		for (int i=0; i<Var.obs.length; i++) {
			if (Var.obs[i].contains(position.x, position.y)) {
				colided = true;
				return;
			}
		}
		*/
	}
	
	public void setFitness() {
		// TODO
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
	
	public void status() {
	}

	public void printDna() {
		dna.printDna();
	}

	public static Subject mateSubjects(Subject p1, Subject p2, int childId) {
		Subject child = new Subject(childId);
		child.dna = DNA.combineDNA(p1.dna.seq, p2.dna.seq);
		return child;
	}

}
