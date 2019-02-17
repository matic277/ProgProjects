package Pieces;
import java.awt.Color;
import java.awt.Graphics2D;

public interface SquarePainter {
	
	Color defaultColor1 = new Color(79,91,102);
	Color defaultColor2 = new Color(167,173,186);
	Color highLightedColor1 = new Color(
			255 - defaultColor1.getRed(),
			255 - defaultColor1.getGreen(),
			255 - defaultColor1.getBlue()
	);
	Color highLightedColor2 = new Color(
			255 - defaultColor2.getRed(),
			255 - defaultColor2.getGreen(),
			255 - defaultColor2.getBlue()
	);
	Color attackedColor = new Color(255, 68, 68);
	Color hoveredColor = new Color(255, 255, 255, 50);
	Color hoveredEdgesColor = new Color(255, 255, 255, 200);

	void paintSquare(Graphics2D g);
}


class Hovered1 implements SquarePainter {
	Square square;
	
	public Hovered1(Square _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor1);
		g.fill(square.square);
		g.setColor(hoveredColor);
		g.fillRect(square.square.x, square.square.y, square.size, square.size);
		g.setColor(hoveredEdgesColor);
		g.drawRect(square.square.x, square.square.y, square.size-1, square.size-1);
	}
}

class Hovered2 implements SquarePainter {
	Square square;
	
	public Hovered2(Square _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor2);
		g.fill(square.square);
		g.setColor(hoveredColor);
		g.fillRect(square.square.x, square.square.y, square.size, square.size);
		g.setColor(hoveredEdgesColor);
		g.drawRect(square.square.x, square.square.y, square.size-1, square.size-1);
	}
}



class HighLighted1 implements SquarePainter {
	Square square;
	
	public HighLighted1(Square _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor1);
		g.fill(square.square);
		g.setColor(highLightedColor1);
		g.fillOval(square.square.x, square.square.y, 30, 30);
	}
}

class HighLighted2 implements SquarePainter {
	Square square;
	
	public HighLighted2(Square _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor2);
		g.fill(square.square);
		g.setColor(highLightedColor2);
		g.fillOval(square.square.x, square.square.y, 30, 30);
	}
}

class Attacked1 implements SquarePainter {
	Square square;
	
	public Attacked1(Square _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor1);
		g.fill(square.square);
		g.setColor(attackedColor);
		g.fillOval(square.square.x, square.square.y, 30, 30);
	}
}

class Attacked2 implements SquarePainter {
	Square square;
	
	public Attacked2(Square _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor2);
		g.fill(square.square);
		g.setColor(attackedColor);
		g.fillOval(square.square.x, square.square.y, 30, 30);
	}
}

class Default1 implements SquarePainter {
	Square square;
	
	public Default1(Square _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor1);
		g.fill(square.square);
	}
}

class Default2 implements SquarePainter {
	Square square;
	
	public Default2(Square _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor2);
		g.fill(square.square);
	}
}
