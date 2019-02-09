import java.awt.Image;
import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(int _i, int _j, boolean _color, Image _img, String _unicodeChar) {
		super(_i, _j, _color, _img, _unicodeChar);
		// TODO Auto-generated constructor stub
	}
	
	public void updateImage() {
		if (color) img = ResourceLoader.queenWhiteScaled;
		else img = ResourceLoader.queenblackScaled;
	}

	public ArrayList<Integer> getMovableSquares(Piece[][] board) {
		ArrayList<Integer> moves = new ArrayList<Integer>(15);
		
		System.out.println("I am "+color+" as "+this.getClass().getName()+" at "+i+" "+j);
		
		
		// top left diagonal
		for (int i=this.i, j=this.j, s=0; s<Board.SQUARES; i--, j--, s++) {
			if (s == 0) continue;
			if (i >= 0 && j >= 0) {
				if (board[i][j] == null) moves = addMove(i, j, moves);
				else if (board[i][j].color != color) {
					moves = addMove(i, j, moves);
					break;
				}
				else break;
			}
		}
		
		// top right diagonal
		for (int i=this.i, j=this.j, s=0; s<Board.SQUARES; i--, j++, s++) {
			if (s == 0) continue;
			if (i >= 0 && j <= Board.SQUARES-1) {
				if (board[i][j] == null) moves = addMove(i, j, moves);
				else if (board[i][j].color != color) {
					moves = addMove(i, j, moves);
					break;
				}
				else break;
			}
		}
		
		// bottom left diagonal
		for (int i=this.i, j=this.j, s=0; s<Board.SQUARES; i++, j--, s++) {
			if (s == 0) continue;
			if (i < Board.SQUARES && j >= 0) {
				if (board[i][j] == null) moves = addMove(i, j, moves);
				else if (board[i][j].color != color) {
					moves = addMove(i, j, moves);
					break;
				}
				else break;
			}
		}
		
		// bottom right diagonal
		for (int i=this.i, j=this.j, s=0; s<Board.SQUARES; i++, j++, s++) {
			if (s == 0) continue;
			if (i < Board.SQUARES && j < Board.SQUARES) {
				if (board[i][j] == null) moves = addMove(i, j, moves);
				else if (board[i][j].color != color) {
					moves = addMove(i, j, moves);
					break;
				}
				else break;
			}
		}
		
		// left
		for (int j=this.j, s=0; s<Board.SQUARES; j--, s++) {
			if (s == 0) continue;
			if (j >= 0) {
				if (board[i][j] == null) moves = addMove(i, j, moves);
				else if (board[i][j].color != color) {
					moves = addMove(i, j, moves);
					break;
				}
				else break;
			}
		}
		
		// right
		for (int j=this.j, s=0; s<Board.SQUARES; j++, s++) {
			if (s == 0) continue;
			if (j < Board.SQUARES) {
				if (board[i][j] == null) moves = addMove(i, j, moves);
				else if (board[i][j].color != color) {
					moves = addMove(i, j, moves);
					break;
				}
				else break;
			}
		}
		
		// top
		for (int i=this.i, s=0; s<Board.SQUARES; i--, s++) {
			if (s == 0) continue;
			if (i >= 0) {
				if (board[i][j] == null) moves = addMove(i, j, moves);
				else if (board[i][j].color != color) {
					moves = addMove(i, j, moves);
					break;
				}
				else break;
			}
		}
		
		// bottom
		for (int i=this.i, s=0; s<Board.SQUARES; i++, s++) {
			if (s == 0) continue;
			if (i < Board.SQUARES) {
				if (board[i][j] == null) moves = addMove(i, j, moves);
				else if (board[i][j].color != color) {
					moves = addMove(i, j, moves);
					break;
				}
				else break;
			}
		}
		return moves;
	}

}
