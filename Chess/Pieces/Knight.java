import java.awt.Image;
import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(int _i, int _j, boolean _color, Image _img, String _unicodeChar) {
		super(_i, _j, _color, _img, _unicodeChar);
		// TODO Auto-generated constructor stub
	}
	
	public void updateImage() {
		if (color) img = ResourceLoader.knightWhiteScaled;
		else img = ResourceLoader.knightblackScaled;
	}

	public ArrayList<Integer> getMovableSquares(Piece[][] board) {
		ArrayList<Integer> moves = new ArrayList<Integer>(15);
		
		System.out.println("I am "+color+" as "+this.getClass().getName()+" at "+i+" "+j);
		
		int ni, nj;
		
		// top
		ni = this.i + 2;
		nj = this.j + 1;
		if (stillInBounds(ni, nj) && (board[ni][nj] == null || board[ni][nj].color != color)) moves = addMove(ni, nj, moves);
		
		nj = this.j - 1;
		if (stillInBounds(ni, nj) && (board[ni][nj] == null || board[ni][nj].color != color)) moves = addMove(ni, nj, moves);
		
		// right
		ni = this.i + 1;
		nj = this.j + 2;
		if (stillInBounds(ni, nj) && (board[ni][nj] == null || board[ni][nj].color != color)) moves = addMove(ni, nj, moves);
		
		ni = this.i - 1;
		if (stillInBounds(ni, nj) && (board[ni][nj] == null || board[ni][nj].color != color)) moves = addMove(ni, nj, moves);
		
		// left
		ni = this.i + 1;
		nj = this.j - 2;
		if (stillInBounds(ni, nj) && (board[ni][nj] == null || board[ni][nj].color != color)) moves = addMove(ni, nj, moves);
		
		ni = this.i - 1;
		if (stillInBounds(ni, nj) && (board[ni][nj] == null || board[ni][nj].color != color)) moves = addMove(ni, nj, moves);
		
		// bottom
		ni = this.i - 2;
		nj = this.j + 1;
		if (stillInBounds(ni, nj) && (board[ni][nj] == null || board[ni][nj].color != color)) moves = addMove(ni, nj, moves);
		
		nj = this.j - 1;
		if (stillInBounds(ni, nj) && (board[ni][nj] == null || board[ni][nj].color != color)) moves = addMove(ni, nj, moves);
		
		return moves;
	}
}
