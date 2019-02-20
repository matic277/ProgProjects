package Pieces;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Main.Painter;
import Main.PieceMoveHandler;
import Squares.ISquare;

public class Piece {
	
	public Coordinates coords;
	public Coordinates startingCoords;
	int pieceSizeScale;
	
	public boolean color;
	public boolean hasMoved;
	Color pieceColor;
	
	public String unicodeChar;
	
	public Piece(Coordinates _coords, boolean _color, String _unicodeChar) {
		coords = _coords;
		startingCoords = new Coordinates(_coords.getI(), _coords.getJ());
		color = _color;
		unicodeChar = _unicodeChar;
		hasMoved = false;
		pieceColor = (color)? Color.WHITE : Color.black;
		
		updateDrawingPosition();
	}
	
	public ArrayList<ISquare> getAvalibleMoves(PieceMoveHandler moveHandler) {
		return null;
	}
	
	public void movePiece(Coordinates _coords) {
		coords.setNewCoords(_coords);
		hasMoved = true;
		updateDrawingPosition();
	}
	
	public void drawPiece(Graphics2D g) {
		g.setFont(new Font("Arial Unicode MS", Font.PLAIN, Painter.squareSize));
		g.setColor(pieceColor);
		g.drawString(unicodeChar, coords.x, coords.y);
		
		// debug
		g.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
		g.setColor(Color.black);
		g.drawString(coords.toStringDrawingCoords(), coords.x+5, coords.y+10);
		
//		g.setColor(Color.red);
//		g.fillOval(coords.x, coords.y, 10, 10);
//		g.drawString("TEXT", coords.x, coords.y);
	}
	
	public void updateDrawingPosition() {
		coords.updateDrawingCoordsPiece();
	}
	
	public String getCharValue() {
		return " _ ";
	}
}
