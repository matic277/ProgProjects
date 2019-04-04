package Renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import Main.Environment;
import Main.Var;

public class SimulationRenderer implements IRenderer {
	
	Environment env;
	IRenderer editorRenderer;	// this is always instanceOf EditorRenderer, should be atleast...
	
	DecimalFormat rounder;
	
	
	// this class wraps EditorRenderer
	// and adds functionality of also
	// drawing population, and some
	// simulation information in the
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

//	private void drawEnvironment(Graphics2D g) {
//		drawBackground(g);
//		drawControlPanel(g);
//		drawMaze(g);
//		
//		// status about simulation
//		drawStatus(g);
//	}
//	
//	private void drawMaze(Graphics2D g) {
//		for (IObstacle o : env.getObstacles()) o.draw(g);
//		
//		drawStart(g);
//		drawFinish(g);
//	}
//	
//	private void drawControlPanel(Graphics2D g) {
//		g.setColor(Color.PINK);
//		g.fillRect(0, 0, Var.width, Var.buttonSpaceHeight);
//	}
//
//	private void drawBackground(Graphics2D g) {
//		g.setColor(Color.LIGHT_GRAY);
//		g.fillRect(0, 0, Var.width, Var.height);
//	}
//	
	private void drawStatus(Graphics2D g) {
		// number of subjects aka population number
		// info about generation number
		// average fitness
		g.setColor(Color.BLACK);
		g.drawString("Population size:   " + Var.populationSize, Var.width-225, Var.height-50);
		g.drawString("Generation number: " + Var.generationNumber, Var.width-225, Var.height-35);
		g.drawString("Avgerage fitness:  " + rounder.format(Var.averageFitness), Var.width-225, Var.height-20);
	}
//
//	private void drawStart(Graphics2D g) {
//		/*
//		g.setColor(start);
//		g.fillRect(j*squareSize, i*squareSize + Var.buttonSpaceHeight, squareSize, squareSize);
//		*/
//		if (Var.start == null) return;
//		g.setColor(start);
//		g.fillRect(Var.start.x, Var.start.y, Var.start.width, Var.start.height);
//	}
//	
//	private void drawFinish(Graphics2D g) {
//		/*
//		g.setColor(finish);
//		g.fillRect(j*squareSize, i*squareSize + Var.buttonSpaceHeight, squareSize, squareSize);
//		*/
//		if (Var.end == null) return;
//		g.setColor(finish);
//		g.fillRect(Var.end.x, Var.end.y, Var.end.width, Var.end.height);
//	}
}
