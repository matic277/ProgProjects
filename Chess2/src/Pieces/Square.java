package Pieces;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.Painter;

public class Square {

	public boolean isHighlightedAsMovable;
	public Coordinates coords;
	public Rectangle square;
	
	int size;
	
	SquarePainter painter;
	SquarePainter defaultPainter;
	
	public Square(Coordinates _coords) {
		coords = _coords;
		
		isHighlightedAsMovable = false;
		size = Painter.squareSize;
		square = new Rectangle();
		defaultPainter = ((coords.i+coords.j) % 2 == 0)? new Default1(this) : new Default2(this);
		
		setDefaultSquareColor();
		updateDrawingPosition();
	}
	
	public void drawSquare(Graphics2D g) {
		painter.paintSquare(g);
	}
	
	public void updateDrawingPosition() {
		size = Painter.squareSize;
		coords.updateDrawingCoordsSquare();
		square.setBounds(coords.x, coords.y, size, size);
	}
	
	public void setColorAsMovable() {
		isHighlightedAsMovable = true;
		painter = ((coords.i+coords.j) % 2 == 0)? new HighLighted1(this) : new HighLighted2(this);
	}
	
	public void highlightSquareAsAttacked() {
		painter = ((coords.i+coords.j) % 2 == 0)? new Attacked1(this) : new Attacked2(this);
	}
	
	public void setColorAsAttacked() {
		painter = ((coords.i+coords.j) % 2 == 0)? new Attacked1(this) : new Attacked2(this);
	}
	
	public void setDefaultSquareColor() {

		painter = defaultPainter;
	}

	public void highlightSquareAsHovered() {
		painter = ((coords.i+coords.j) % 2 == 0)? new Hovered1(this) : new Hovered2(this);
		
	}
	
	public void setColorAsSelected() {
		setColorAsMovable();
	}
	
}
