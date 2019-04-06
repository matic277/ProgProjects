package Renderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Main.MazeEditor;
import Main.Var;
import Obstacle.IObstacle;

public class EditorRenderer implements IRenderer {
	
	MazeEditor editor;
	
	public EditorRenderer(MazeEditor editor_) {
		editor = editor_;
	}
	
	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawControlPanel(g);
		drawEnd(g);
		drawStart(g);
		drawObstacles(g);
		drawTmpObstacle(g);
		drawInfo(g);
	}
	
	private void drawTmpObstacle(Graphics2D g) {
		editor.getTmpObstacle().draw(g);
	}

	private void drawObstacles(Graphics2D g) {
		for (IObstacle lo : editor.getObstacles()) lo.draw(g);
	}
	
	private void drawControlPanel(Graphics2D g) {
		g.setColor(controlPanelClr);
		g.fillRect(0, 0, Var.width, Var.buttonSpaceHeight);
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
		
		String obstClassName = (editor.getObstacles().size() != 0)? editor.getObstacles().get(0).getClass().getName() : "Empty";
		g.drawString(obstClassName, 15 , Var.height-30);
		g.setFont(current);
	}
	
	private void drawStart(Graphics2D g) {
		if (Var.start == null) return;
		g.setColor(startClr);
		g.fillRect(Var.start.x, Var.start.y, Var.start.width, Var.start.height);
	}

	private void drawEnd(Graphics2D g) {
		if (Var.end == null) return;
		g.setColor(endClr);
		g.fillRect(Var.end.x, Var.end.y, Var.end.width, Var.end.height);
	}
}
