package Squares;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.Painter;
import Pieces.Coordinates;

public class Square2 implements ISquare{
	
	//public boolean isHighlightedAsMovable;
	public Coordinates coords;
	public Rectangle square;
	
	int size;
	
	SquarePainter painter;
	SquarePainter defaultPainter;
	
	public Square2(Coordinates _coords) {
		coords = _coords;
		
		//isHighlightedAsMovable = false;
		size = Painter.squareSize;
		square = new Rectangle();
		defaultPainter = new Default2(this);
		
		setDefaultSquareColor();
		updateDrawingPosition();
	}

	public void drawSquare(Graphics2D g) {
		painter.paintSquare(g);
		
		// debug
		g.setFont(new Font("Arial Unicode MS", Font.BOLD, 10));
		g.setColor(Color.black);
		g.drawString(coords.toStringCompact(), coords.x+5, coords.y+20);
	}

	public void updateDrawingPosition() {
		size = Painter.squareSize;
		coords.updateDrawingCoordsSquare();
		square.setBounds(coords.x, coords.y, size, size);
	}
	
	public void setColorAsMovable() {
		//isHighlightedAsMovable = true;
		painter = new HighLighted2(this);
	}
	
	public void highlightSquareAsAttacked() {
		painter = new Attacked2(this);
	}
	
	public void setColorAsAttacked() {
		painter = new Attacked2(this);
	}
	
	public void setDefaultSquareColor() {
		painter = defaultPainter;
	}

	public void highlightSquareAsHovered() {
		painter =  new Hovered2(this);
	}
	
	public void setColorAsSelected() {
		painter = new Hovered(this);
	}

	public Coordinates getCoords() { return coords; }
	public Rectangle getRect() { return square; }
	public int getSize() { return size; }
}


