import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class Piece {
	
	// logical position on board
	int i, j;
	
	// drawing position
	int dx, dy;
	
	// drawing offset (beuacse of scale set in resource loader)
	double offset, scale = ResourceLoader.pieceSizeScale;
	
	// piece color, true=white, false=black
	boolean color;
	Color pieceColor;
	
	// unicode char value of piece
	String unicodeChar;
	
	Image img;
	
	public Piece(int _i, int _j, boolean _color, Image _img, String _unicodeChar) {
		i = _i; j = _j;
		color = _color;
		img = _img;
		unicodeChar = _unicodeChar;
		
		pieceColor = (color)? Color.white : Color.black;
		
		updatePosition();
	}
	
	public void move(int i2, int j2) {
		i = i2;
		j = j2;
		updatePosition();
	}
	
	public void remove() {
		
	}
	
	public void draw(Graphics2D g) {
		//g.drawImage(img, dx, dy, null);
		g.setColor(pieceColor);
		g.drawString(unicodeChar, dx, dy);
		
		//g.setStroke(new BasicStroke(3));
		//g.setColor(Color.red);
		//g.drawString(i+" "+j, dx+10, dy+10);
	}
	
	public ArrayList<Integer> getMovableSquares(Piece[][] board) {
		return null;
	}

	public void updateImage() {
		// implemented in each class separately
	}
	
	public void updatePosition() {
		dx = Painter.BOARD_EDGE + Painter.EDGE + j * Painter.squareSize;
		dy = Painter.BOARD_EDGE + Painter.EDGE + i * Painter.squareSize;
		
		offset = Painter.squareSize * (1 - scale) / 2.0;
		dx += (int)  offset;
		dy += (int) (offset * 1.5);
		
		// use this only if drawing unicode chars
		dy += Painter.squareSize / 1.375;
	}
	
	protected boolean stillInBounds(int i, int j) {
		if (i >= 0 && i < Board.SQUARES && j >= 0 && j < Board.SQUARES) return true;
		return false;
	}
	
	protected ArrayList<Integer> addMove(int i, int j, ArrayList<Integer> moves) {
		moves.add(i);
		moves.add(j);
		return moves;
	}
	
	protected boolean isSquareTaken(int i, int j, Piece board[][]) {
		if (board[i][j] == null) return false;
		return true;
	}
	
	protected boolean isSquareTakenByOpposition(int i, int j, boolean color, Piece board[][]) {
		if (board[i][j] == null) return false;
		else if (board[i][j].color != color) return false;
		return true;
	}

}
