package Main;

import java.util.ArrayList;

import Pieces.Piece;
import Squares.ISquare;
import Squares.Square;

public class PieceMoveHandler {
	
	// TODO: missing features
	// a) move can't be made if it inflicts a self-made discovered check
	// b) implement en passant for pawns
	// c) king can't step in check
	// d) castling
	// e) pawn promotion
	
	Board board;
	
	public PieceMoveHandler(Board _board) {
		board = _board;
	}
	
	public ArrayList<ISquare> getAvalibleMovesVertical(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		// down
		for (int i=piece.coords.i, j=piece.coords.j; i<Board.SQUARES; i++) {
			if (board.pieces[i][j] == null) {
				moves.add(board.squares[i][j]);
			} else if (board.pieces[i][j].color == piece.color) {
				break;
			} else {
				moves.add(board.squares[i][j]);
			}
		}
		
		// up
		for (int i=piece.coords.i, j=piece.coords.j; i>=0; i--) {
			if (board.pieces[i][j] == null) {
				moves.add(board.squares[i][j]);
			} else if (board.pieces[i][j].color == piece.color) {
				break;
			} else {
				moves.add(board.squares[i][j]);
			}
		}
		
		return moves;
	}
	
	public ArrayList<ISquare> getAvalibleMovesHorizontal(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		// right
		for (int i=piece.coords.i, j=piece.coords.j; j<Board.SQUARES; j++) {
			if (board.pieces[i][j] == null) {
				moves.add(board.squares[i][j]);
			} else if (board.pieces[i][j].color == piece.color) {
				break;
			} else {
				moves.add(board.squares[i][j]);
			}
		}
		
		// left
		for (int i=piece.coords.i, j=piece.coords.j; j>=0; j--) {
			if (board.pieces[i][j] == null) {
				moves.add(board.squares[i][j]);
			} else if (board.pieces[i][j].color == piece.color) {
				break;
			} else {
				moves.add(board.squares[i][j]);
			}
		}
		
		return moves;
	}
	
	public ArrayList<ISquare> getAvalibleMovesDiagonal(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		// top left
		for (int i=piece.coords.i, j=piece.coords.j; i>=0 && j>=0; i--, j--) {
			if (board.pieces[i][j] == null) {
				moves.add(board.squares[i][j]);
			} else if (board.pieces[i][j].color == piece.color) {
				break;
			} else {
				moves.add(board.squares[i][j]);
			}			
		}
		
		// top right
		for (int i=piece.coords.i, j=piece.coords.j; i>=0 && j<Board.SQUARES; i--, j++) {
			if (board.pieces[i][j] == null) {
				moves.add(board.squares[i][j]);
			} else if (board.pieces[i][j].color == piece.color) {
				break;
			} else {
				moves.add(board.squares[i][j]);
			}			
		}
		
		// bottom left
		for (int i=piece.coords.i, j=piece.coords.j; i<Board.SQUARES && j>=0; i++, j--) {
			if (board.pieces[i][j] == null) {
				moves.add(board.squares[i][j]);
			} else if (board.pieces[i][j].color == piece.color) {
				break;
			} else {
				moves.add(board.squares[i][j]);
			}			
		}
		
		// bottom right
		for (int i=piece.coords.i, j=piece.coords.j; i<Board.SQUARES && j<Board.SQUARES; i++, j++) {
			if (board.pieces[i][j] == null) {
				moves.add(board.squares[i][j]);
			} else if (board.pieces[i][j].color == piece.color) {
				break;
			} else {
				moves.add(board.squares[i][j]);
			}			
		}
		
		return moves;
	}
	
	public ArrayList<ISquare> getAvalibleMovesPawn(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		// can i capture any pieces?
		int i = piece.coords.i,
			j = piece.coords.j;
		int ni, nj;
		boolean color = piece.color;
		int offset = (piece.color)? -1 : 1;
		Piece pieceToCapture;
		
		// capture to left
		ni = i + offset;
		nj = j - 1;
		if (stillInBounds(ni, nj)) {
			pieceToCapture = board.pieces[i+offset][j-1];
			if (pieceToCapture != null && pieceToCapture.color != color) {
				moves.add(board.squares[ni][nj]);
			}
		}
		
		// capture to right
		ni = i + offset;
		nj = j + 1;
		if (stillInBounds(ni, nj)) {
			pieceToCapture = board.pieces[ni][nj];
			if (pieceToCapture != null && pieceToCapture.color != color) {
				moves.add(board.squares[ni][nj]);
			}
		}
		
		// move 1 square if not blocked
		ni = i + offset;
		nj = j;
		boolean blocked = true;
		if (board.pieces[ni][nj] == null) {
			moves.add(board.squares[ni][nj]);
			blocked = false;
		}
		
		// offer to move 2 squares if pawn is in starting position and is not blocked
		if (isInStartingPosition(piece)) {
			ni = i + 2 * offset;
			nj = j;
			if (!blocked && board.pieces[ni][nj] == null) {
				moves.add(board.squares[ni][nj]);
			}
		}
		
		return moves;
	}
	
	public ArrayList<ISquare> getAvalibleMovesKnight(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		/*
		 	i-2, j-1		i-2, j+1
		i-1, j-2				i-1, j+2
					i, j
		i+1, j-2				i+1, j+2
			i+2, j-1		i+2, j+1
		 */
		
		int offsets[][] = {
			{-2, -2, -1, -1,  1,  1,  2,  2},
			{-1,  1, -2,  2, -2,  2, -1,  1}
		};
		int i = piece.coords.i,
			j = piece.coords.j;
		int ni, nj;
		boolean color = piece.color;
		Piece possiblePiece;
		
		for (int k=0; k<offsets[0].length; k++) {
			ni = i + offsets[0][k];
			nj = j + offsets[1][k];
			if (stillInBounds(ni, nj)) {
				possiblePiece = board.pieces[ni][nj];
				if (possiblePiece == null || possiblePiece.color != color) {
					moves.add(board.squares[ni][nj]);
				}
			}
		}
		
		return moves;
	}
	
	public ArrayList<ISquare> getAvalibleMovesKing(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		int offsets[] = { -1, 0, 1 };
		int ni, nj;
		int i = piece.coords.i;
		int j = piece.coords.j;
		boolean color = piece.color;
		Piece possiblePiece;
		
		for (int n=0; n<offsets.length; n++) {
			for (int m=0; m<offsets.length; m++) {
				if (offsets[n] != 0 && offsets[m] != 0) {
					ni = i + offsets[n];
					nj = j + offsets[m];
					if (stillInBounds(ni, nj)) {
						possiblePiece = board.pieces[ni][nj];
						if (possiblePiece == null || possiblePiece.color != color) {
							moves.add(board.squares[ni][nj]);
						}
					}
				}
			}
		}
		
		return moves;
	}
	
	public boolean isInStartingPosition(Piece piece) {
		if (piece.coords.compare(piece.startingCoords)) {
			return true;
		}
		return false;
	}
	
	public boolean stillInBounds(int i, int j) {
		if (i>= 0 && i<Board.SQUARES &&
			j>= 0 && j<Board.SQUARES)
		{
			return true;
		}
		return false;
	}
	
	public void print(String piece, ArrayList<ISquare> moves) {
		System.out.println("Avalible moves for " + piece + ":");
		if (moves.size() == 0) {
			System.out.println("No moves");
			return;
		}
		for (ISquare s : moves) System.out.println(s.getCoords().toString());
	}

}
