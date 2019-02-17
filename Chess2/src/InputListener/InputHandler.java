package InputListener;

import Main.Board;
import Pieces.Piece;
import Pieces.Square;

public class InputHandler {
	
	Board board;
	
	Square clickedSquare;
	Piece clickedPiece;
	
	public InputHandler(Board _board) {
		board = _board;
		clickedSquare = null;
		clickedPiece = null;
	}

	public void mouseClicked(int mx, int my) {
		// no square has previously been clicked, so check only if a piece was clicked on
		if (clickedSquare == null) {
			loop:
			for (int i=0; i<Board.SQUARES; i++)
			for (int j=0; j<Board.SQUARES; j++)
				if (board.squares[i][j].square.contains(mx, my) && board.pieces[i][j] != null) {
					clickedSquare = board.squares[i][j];
					clickedPiece = board.pieces[i][j];
					break loop;
				}
			
			if (clickedSquare != null) {
				board.highlightSelectedSquare(clickedSquare);
				board.highlightPossibleMoveSquares(clickedPiece);
			}
			
			System.out.println("clicked square: "+clickedSquare.coords.toString());
		}
		
		// a piece has already been selected, see if this click is on a movable square that selected piece can move to
		else {
			clickedSquare = null;
			clickedPiece = null;
		}
		
	}

	public void mouseMoved(int mx, int my) {
		for (int i=0; i<Board.SQUARES; i++)
		for (int j=0; j<Board.SQUARES; j++)
			if (clickedSquare == null) {
				if (board.squares[i][j].square.contains(mx, my)) board.highlightHoveredSquare(board.squares[i][j]);
				else board.squares[i][j].setDefaultSquareColor();
			}
			else {
				if(!clickedSquare.coords.compare(board.squares[i][j].coords) && board.squares[i][j].isHighlightedAsMovable) board.squares[i][j].setDefaultSquareColor();
			}
			
			
			
			/*
			if (board.squares[i][j].square.contains(mx, my) && clickedSquare.coords.compare(board.squares[i][j].coords)) board.highlightHoveredSquare(board.squares[i][j]);
			else {
				if (clickedSquare == null) {
					board.squares[i][j].setDefaultSquareColor();
				}
				else {
					if (!board.squares[i][j].coords.compare(clickedSquare.coords)) board.squares[i][j].setDefaultSquareColor();
					else {
						System.out.println("not resetting: "+board.squares[i][j].coords.toString());
					}
				}
			}
			*/
			
	}
	
	

}
