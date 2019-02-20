package InputListener;

import java.util.ArrayList;

import Main.Board;
import Pieces.Piece;
import Squares.ISquare;
import Squares.Square;

public class InputHandler {
	
	Board board;
	
	ISquare clickedSquare;
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
			for (int j=0; j<Board.SQUARES; j++) {
				if (board.squares[i][j].getRect().contains(mx, my) && board.pieces[i][j] != null) {
					if (board.colorToPlay == board.pieces[i][j].color) {
						clickedSquare = board.squares[i][j];
						clickedPiece = board.pieces[i][j];
						break loop;
					}
				}
			}
			
			if (clickedSquare != null) {
				board.highlightSelectedSquare(clickedSquare);
				board.highlightPossibleMoveSquares(clickedPiece);
				
				//System.out.println("clicked square: "+clickedSquare.getCoords().toString());
			}
		}
		
		// a piece has already been selected, see if this click is on a movable square that selected piece can move to
		else {
			ArrayList<ISquare> moves = board.possibleMoves;
			ISquare clickedSquare2 = null;
			
			if (moves.size() != 0) {
				System.out.println("Move size is not 0");
				
				loop:
				for (int i=0; i<Board.SQUARES; i++)
				for (int j=0; j<Board.SQUARES; j++) {
					if (board.squares[i][j].getRect().contains(mx, my)) {
						clickedSquare2 = board.squares[i][j];
						break loop;
					}
				}
			
				if (clickedSquare2 != null) {
					// a square has been clicked. is it in possible moves?
					//System.out.println("Clicked2 square: " + clickedSquare.getCoords().toString());
					for (ISquare s : moves) {
						if (clickedSquare2.getCoords().compare(s.getCoords())) {
							board.movePieceToSquare(clickedSquare, clickedSquare2);
							break;
						}
					}
				}
			}
			
			clickedSquare = null;
			clickedSquare2 = null;
			clickedPiece = null;
			board.resetPossibleMoveSquares();
		}
		
	}

	public void mouseMoved(int mx, int my) {
		loop:
		for (int i=0; i<Board.SQUARES; i++)
		for (int j=0; j<Board.SQUARES; j++) {
			/*
			if (clickedSquare == null) {
				if (board.squares[i][j].getRect().contains(mx, my)) board.highlightHoveredSquare(board.squares[i][j]);
				else board.squares[i][j].setDefaultSquareColor();
			}
			else {
				if(!clickedSquare.getCoords().compare(board.squares[i][j].getCoords()) && board.squares[i][j].isHighlightedAsMovable)
					board.squares[i][j].setDefaultSquareColor();
			}
			*/
			if (board.squares[i][j].getRect().contains(mx, my)) {
				board.highlightHoveredSquare(board.squares[i][j]);
				break loop;
			}
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
