import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class FreeEditorRenderer implements IRenderer {
	
	ArrayList<IObstacle> obstacles;
	MazeEditor editor;
	
	public FreeEditorRenderer(MazeEditor editor_) {
		editor = editor_;
		obstacles = editor_.obstacles;
	}

	@Override
	public void draw(Graphics2D g) {
		drawBackground(g);
		drawPanel(g);
		drawLines(g);
	}

	private void drawLines(Graphics2D g) {
		for (IObstacle o : obstacles) o.draw(g);
	}

	private void drawPanel(Graphics2D g) {
		g.setColor(Color.pink);
		g.fillRect(0, 0, Var.width, Var.buttonSpaceHeight);
	}

	private void drawBackground(Graphics2D g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, Var.width, Var.height);
	}
}
