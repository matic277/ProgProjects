package Squares;
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

class Hovered implements SquarePainter {
	
	ISquare square;
	
	public Hovered(ISquare _square) {
		square = _square;
	}

	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(hoveredColor);
		g.fillRect(square.getRect().x, square.getRect().y, square.getSize(), square.getSize());
		g.setColor(hoveredEdgesColor);
		g.drawRect(square.getRect().x, square.getRect().y, square.getSize()-1, square.getSize()-1);
	}
	
}

class Selected implements SquarePainter {
	Square1 square;
	
	public Selected(Square1 _square) {
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


class Hovered1 implements SquarePainter {
	Square1 square;
	
	public Hovered1(Square1 _square) {
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
	Square2 square;
	
	public Hovered2(Square2 _square) {
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
	Square1 square;
	
	public HighLighted1(Square1 _square) {
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
	Square2 square;
	
	public HighLighted2(Square2 _square) {
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
	Square1 square;
	
	public Attacked1(Square1 _square) {
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
	Square2 square;
	
	public Attacked2(Square2 _square) {
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
	Square1 square;
	
	public Default1(Square1 square1) {
		square = square1;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor1);
		g.fill(square.square);
	}
}

class Default2 implements SquarePainter {
	Square2 square;
	
	public Default2(Square2 _square) {
		square = _square;
	}
	
	@Override
	public void paintSquare(Graphics2D g) {
		g.setColor(defaultColor2);
		g.fill(square.square);
	}
}
