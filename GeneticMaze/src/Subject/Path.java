package Subject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import Main.Population;
import Main.Var;

public class Path {
	
	Subject subject;
	Vector trail[];
	static Color colors[] = Population.pathColors;
	
	public Path(Subject s) {
		subject = s;
		trail = new Vector[Var.DnaLength];
		colors = new Color[trail.length];
		
		Vector start = new Vector(Var.start.getCenterX(), Var.start.getCenterY());
		for (int i=0; i<trail.length; i++) {
			trail[i] = new Vector(start);
			start.add(subject.getDNA().getSeq()[i]);
		}
	}

	
	public void draw(Graphics2D g) {
		Vector start, end;
		
		g.setStroke(new BasicStroke(5));
		
		// draw all paths leading to dnaIndex
		for (int i=0; i<Var.dnaIndex - 2; i++) {
			start = trail[i];
			end = trail[i+1];
			
			g.setColor(Population.pathColors[i]);
			g.drawLine(
				(int)start.x,
				(int)start.y,
				(int)end.x,
				(int)end.y
			);
			
			//System.out.println(colors[i].getRed() + " " + colors[i].get);
		}
		
		g.setStroke(new BasicStroke(1));
	}
}
