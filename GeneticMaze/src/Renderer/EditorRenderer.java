package Renderer;

import java.awt.Font;
import java.awt.Graphics2D;

import Main.Environment;
import Main.Var;
import Obstacle.IObstacle;

public class EditorRenderer implements IRenderer {
	
	Environment env;
	
	public EditorRenderer(Environment env_) {
		env = env_;
	}
	
	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawUpperControlPanel(g);
		drawRightControlPanel(g);
		drawEnd(g);
		drawStart(g);
		drawObstacles(g);
		drawTmpObstacle(g);
		drawInfo(g);
	}

	private void drawTmpObstacle(Graphics2D g) {
		env.getTmpObstacle().draw(g);
	}

	private void drawObstacles(Graphics2D g) {
		for (IObstacle lo : env.getObstacles()) lo.draw(g);
	}
	
	private void drawUpperControlPanel(Graphics2D g) {
		g.setColor(upperControlPanelClr);
		g.fillRect(0, 0, Var.width, Var.buttonSpaceHeight);
	}
	
	private void drawRightControlPanel(Graphics2D g) {
		g.setColor(rightControlPanelClr);
		g.fillRect(Var.drawingWidth, 0, Var.rightPanelWidth, Var.height);
	}

	private void drawBackground(Graphics2D g) {
		g.setColor(bgClr);
		g.fillRect(0, 0, Var.width, Var.height);
	}
	
	public void drawInfo(Graphics2D g) {
		Font current = g.getFont();
		
		g.setColor(infoClr);
		g.setFont(new Font("Consolas", Font.BOLD, 14)); 
		
		g.drawString(this.getClass().getName(), 15 , Var.height-15);
		
		String obstClassName = (env.getObstacles().size() != 0)? env.getObstacles().get(0).getClass().getName() : "Empty";
		g.drawString(obstClassName, 15 , Var.height-30);
		
		g.drawString("Status: " + env.getState(), 15 , Var.height-45);
		g.setFont(current);
	}
	
	private void drawStart(Graphics2D g) {
		if (Var.start == null) return;
		g.setColor(startClr);
		g.fillRect(Var.start.x, Var.start.y, Var.start.width, Var.start.height);
		g.setColor(startClr.darker());
		g.drawString("start", Var.start.x, Var.start.y + Var.startHeight + 9);
	}

	private void drawEnd(Graphics2D g) {
		if (Var.end == null) return;
		g.setColor(endClr);
		g.fillRect(Var.end.x, Var.end.y, Var.end.width, Var.end.height);
		g.setColor(endClr.darker());
		g.drawString("end", Var.end.x, Var.end.y + Var.startHeight + 9);
	}
}
