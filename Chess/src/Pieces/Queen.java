package Pieces;

import java.util.ArrayList;

import Main.PieceMoveHandler;
import Squares.ISquare;
import Squares.Square;

public class Queen extends Piece {

	public Queen(Coordinates _coords, boolean _color, String _unicodeChar) {
		super(_coords, _color, _unicodeChar);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<ISquare> getAvalibleMoves(PieceMoveHandler moveHandler) {
		ArrayList<ISquare> moves = moveHandler.getAvalibleMovesVertical(this);
		moves.addAll(moveHandler.getAvalibleMovesHorizontal(this));
		moves.addAll(moveHandler.getAvalibleMovesDiagonal(this));
		return moves;
	}
	
	public String getCharValue() {
		return (color)? "wQ " : "bQ ";
	}

}
