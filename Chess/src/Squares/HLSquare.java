package Squares;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.Painter;
import Pieces.Coordinates;

public class HLSquare implements ISquare{
	
	Coordinates coords;
	public Rectangle square;
	int size;
	SquarePainter painter;
	
	public HLSquare(Coordinates _coords) {
		coords = _coords;
		
		//isHighlightedAsMovable = false;
		size = Painter.squareSize;
		square = new Rectangle();
		painter = new Hovered(this);
		
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
	
	public void highlightSquareAsHovered() {
		painter =  new Hovered(this);
	}


	@Override
	public void setColorAsMovable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void highlightSquareAsAttacked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorAsAttacked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefaultSquareColor() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setColorAsSelected() {
		// TODO Auto-generated method stub
		
	}

	public Coordinates getCoords() { return coords; }
	public Rectangle getRect() { return square; }
	public int getSize() { return size; }

}
