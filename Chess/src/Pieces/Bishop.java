package Pieces;

import java.util.ArrayList;

import Main.PieceMoveHandler;
import Squares.ISquare;
import Squares.Square;

public class Bishop extends Piece {

	public Bishop(Coordinates _coords, boolean _color, String _unicodeChar) {
		super(_coords, _color, _unicodeChar);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<ISquare> getAvalibleMoves(PieceMoveHandler moveHandler) {
		return moveHandler.getAvalibleMovesDiagonal(this);
	}
	
	public String getCharValue() {
		return (color)? "wB " : "bB ";
	}

}
