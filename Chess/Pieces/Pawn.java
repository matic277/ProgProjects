import java.awt.Image;
import java.util.ArrayList;

public class Pawn extends Piece {

	public Pawn(int _i, int _j, boolean _color, Image _img, String _unicodeChar) {
		super(_i, _j, _color, _img, _unicodeChar);
		// TODO Auto-generated constructor stub
	}


	public void updateImage() {
		if (color) img = ResourceLoader.pawnWhiteScaled;
		else img = ResourceLoader.pawnblackScaled;
	}
	
	public ArrayList<Integer> getMovableSquares(Piece[][] board) {
		ArrayList<Integer> moves = new ArrayList<Integer>(15);
		
		System.out.println("I am "+color+" as "+this.getClass().getName()+" at "+i+" "+j);
		
		// TODO: offer piece exchange when pawn gets pushed to promotion... but not here
		
		// check if pawn is blocked
		if (color) {
			if (board[i-1][j] == null) moves = addMove(i-1, j, moves);
			if (i == Board.SQUARES-2 && board[i-2][j] == null) moves = addMove(i-2, j, moves);
		} else {
			if (board[i+1][j] == null) moves = addMove(i+1, j, moves);
			if (i == 1 && board[i+2][j] == null) moves = addMove(i+2, j, moves);
		}
		
		// can it capture right or left?
		// left
		if (j != 0)
			if (color) {
				if (board[i-1][j-1] != null && board[i-1][j-1].color != color) moves = addMove(i-1, j-1, moves);
			} else {
				if (board[i+1][j-1] != null && board[i+1][j-1].color != color) moves = addMove(i+1, j-1, moves);
			}
			
		// right
		if (j != Board.SQUARES-1) 
			if (color) {
				if (board[i-1][j+1] != null && board[i-1][j+1].color != color) moves = addMove(i-1, j+1, moves);
			} else {
				if (board[i+1][j+1] != null && board[i+1][j+1].color != color) moves = addMove(i+1, j+1, moves);
			}

		return moves;
	}
}
