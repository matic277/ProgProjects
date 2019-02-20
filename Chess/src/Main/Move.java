package Main;

import Pieces.Piece;
import Squares.ISquare;

public class Move {
	
	int moveNumber;
	int timeSpent;
	boolean color;
	ISquare source;
	ISquare dest;
	Piece pieceCaptured;
	
	
	public Move(ISquare _source, ISquare _dest, Piece _pieceCaptured, int _timeSpent, int _moveNumber, boolean _color) {
		source = _source;
		dest = _dest;
		timeSpent = _timeSpent;
		pieceCaptured = _pieceCaptured;
		moveNumber = _moveNumber;
		color = _color;
	}
	
	public Piece getPieceCaptured() {
		return pieceCaptured;
	}
	
	public ISquare getSource() {
		return source;
	}
	
	public ISquare getDest() {
		return dest;
	}
	
	public int getTimeSpent() {
		return timeSpent;
	}
	
	public int getMoveNumber() {
		return moveNumber;
	}

}

