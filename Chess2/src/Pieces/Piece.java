package Pieces;
import java.awt.Color;
import java.awt.Graphics2D;

public class Piece {
	
	Coordinates coords;
	int pieceSizeScale;
	
	boolean color;
	Color pieceColor;
	
	String unicodeChar;
	
	public Piece(Coordinates _coords, boolean _color, String _unicodeChar) {
		coords = _coords;
		color = _color;
		unicodeChar = _unicodeChar;
		
		pieceColor = (color)? Color.WHITE : Color.black;
		
		updateDrawingPosition();
	}
	
	public void movePiece(Coordinates _coords) {
		coords = _coords;
	}
	
	public void drawPiece(Graphics2D g) {
		g.setColor(pieceColor);
		g.drawString(unicodeChar, coords.x, coords.y);
	}
	
	public void updateDrawingPosition() {
		coords.updateDrawingCoordsPiece();
	}
	
	
	

}
