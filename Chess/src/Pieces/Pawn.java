package Pieces;

import java.util.ArrayList;

import Main.PieceMoveHandler;
import Squares.ISquare;
import Squares.Square;

public class Pawn extends Piece {

	public Pawn(Coordinates _coords, boolean _color, String _unicodeChar) {
		super(_coords, _color, _unicodeChar);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<ISquare> getAvalibleMoves(PieceMoveHandler moveHandler) {
		return moveHandler.getAvalibleMovesPawn(this);
	}

}
