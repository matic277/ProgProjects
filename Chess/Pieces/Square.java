import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

public class Square {
	
	// position
	int i, j;
	
	// drawing position
	int dx, dy;
	
	// size
	int size;
	
	// color
	Color defaultColor;
	Color color;
	Color highlight;
	
	Rectangle rect;
	
	public Square(int i_, int j_) {
		i = i_; j = j_;
		size = Painter.squareSize;
		
		if ((i+j) % 2 == 0) color = defaultColor = highlight = new Color(79,91,102);
		else color = defaultColor = highlight = new Color(167,173,186);
		
		rect = new Rectangle();
		
		updateSize();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillRect(dx, dy, size, size);
		g.setColor(highlight);
		
		// TODO: fix these sizes for possible moves
		g.setStroke(new BasicStroke(3));
		g.drawOval(dx+10, dy+10, size-20, size-20);
	}
	
	public void updateSize() {
		dx = Painter.BOARD_EDGE + Painter.EDGE + j * Painter.squareSize;
		dy = Painter.BOARD_EDGE + Painter.EDGE + i * Painter.squareSize;
		size = Painter.squareSize;
		
		rect.setBounds(dx, dy, size, size);
	}

	public void changeColor() {
		if ((i+j) % 2 == 0) color = new Color(255-167, 255-173, 255-102);
		else color = new Color(255-79, 255-91, 255-102);
	}
	
	public void resetColor() {
		color = defaultColor;
	}

	public void highlight() {
		highlight = new Color(100, 0, 110);
	}
	
	public void resetHighlight() {
		highlight = defaultColor;
	}

}
