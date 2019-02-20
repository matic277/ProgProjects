package Main;

import Pieces.Piece;
import Squares.ISquare;

public class Player {
	
	String name;
	History history;
	
	boolean color;
	int movesNumber;
	
	public Player(boolean _color, String _name) {
		name = _name;
		color = _color;
		movesNumber = 0;
		
		history = new History();
	}
	// ISquare _source, ISquare _dest, Piece _pieceCaptured, int _timeSpent, int _moveNumber, boolean _color
	public void recordMove(ISquare source, ISquare dest, Board board, int timeSpent) {
		movesNumber++;
		
		int si = dest.getCoords().getI(),
			sj = dest.getCoords().getJ();
		
		// can be null
		Piece pieceCaptured = board.pieces[si][sj];
		
		history.recordMove(new Move(
			source,
			dest,
			pieceCaptured,
			timeSpent,
			movesNumber,
			color
		));
	}
	
	public void undoLastMove() {
		history.undoLastMove();
	}
	
	public void setName(String newName) {
		name = newName;
	}
	

}
