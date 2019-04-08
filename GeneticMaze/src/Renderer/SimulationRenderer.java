package Renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import Main.Environment;
import Main.Var;

public class SimulationRenderer implements IRenderer {
	
	Environment env;
	IRenderer editorRenderer;	// this is always instanceOf EditorRenderer, should be atleast...
	
	DecimalFormat rounder;
	
	// this class wraps EditorRenderer and adds
	// functionality of also drawing population, 
	// and some simulation information in the
	// drawStatus method
	public SimulationRenderer(Environment env_, IRenderer editorRenderer_) {
		env = env_;
		editorRenderer = editorRenderer_;
		rounder = new DecimalFormat("#.##");
	}
	
	@Override
	public void draw(Graphics2D g) {
		editorRenderer.draw(g);
		drawPopulation(g);
		drawStatus(g);
	}

	private void drawPopulation(Graphics2D g) {
		env.getPopulation().draw(g);
	}

	private void drawStatus(Graphics2D g) {
		Font current = g.getFont();
		g.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		int drawXcoord = Var.drawingWidth - 220;
		
		g.setColor(Color.BLACK);
		g.drawString("Population size:   " + Var.populationSize, drawXcoord, Var.height-50);
		g.drawString("Generation number: " + Var.generationNumber, drawXcoord, Var.height-35);
		g.drawString("Avgerage fitness:  " + rounder.format(Var.averageFitness), drawXcoord, Var.height-20);
		
		g.setFont(current);
	}
}
