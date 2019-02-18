package Pieces;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Main.PieceMoveHandler;
import Squares.ISquare;

public class Piece {
	
	public Coordinates coords;
	public Coordinates startingCoords;
	int pieceSizeScale;
	
	public boolean color;
	public boolean hasMoved;
	Color pieceColor;
	
	String unicodeChar;
	
	public Piece(Coordinates _coords, boolean _color, String _unicodeChar) {
		coords = startingCoords = _coords;
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
		coords = _coords;
		hasMoved = true;
		updateDrawingPosition();
	}
	
	public void drawPiece(Graphics2D g) {
		g.setColor(pieceColor);
		g.drawString(unicodeChar, coords.x, coords.y);
		
		// debug
		g.setFont(new Font("Arial Unicode MS", Font.BOLD, 10));
		g.setColor(Color.black);
		g.drawString(coords.toString(), coords.x+5, coords.y+25);
	}
	
	public void updateDrawingPosition() {
		coords.updateDrawingCoordsPiece();
	}
}
