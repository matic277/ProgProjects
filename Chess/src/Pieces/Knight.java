package Pieces;

import java.util.ArrayList;

import Main.PieceMoveHandler;
import Squares.ISquare;
import Squares.Square;

public class Knight extends Piece {

	public Knight(Coordinates _coords, boolean _color, String _unicodeChar) {
		super(_coords, _color, _unicodeChar);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<ISquare> getAvalibleMoves(PieceMoveHandler moveHandler) {
		return moveHandler.getAvalibleMovesKnight(this);
	}

}
