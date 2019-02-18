package Squares;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Pieces.Coordinates;

public interface ISquare {
	public void drawSquare(Graphics2D g);
	public void updateDrawingPosition();
	public void setColorAsMovable();
	public void highlightSquareAsAttacked();
	public void setColorAsAttacked();
	public void setDefaultSquareColor();
	public void highlightSquareAsHovered();
	public void setColorAsSelected();
	public Coordinates getCoords();
	public Rectangle getRect();
	public int getSize();
}



