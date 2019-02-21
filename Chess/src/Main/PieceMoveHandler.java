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
		if (piece.coords.getI() != Board.SQUARES-1) {
			for (int i=piece.coords.getI()+1, j=piece.coords.getJ(); i<Board.SQUARES; i++) {
				if (board.pieces[i][j] == null) {
					moves.add(board.squares[i][j]);
				} else if (board.pieces[i][j].color == piece.color) {
					break;
				} else {
					moves.add(board.squares[i][j]);
					break;
				}
			}
		}
		
		// up
		if (piece.coords.getI() != 0) {
			for (int i=piece.coords.getI()-1, j=piece.coords.getJ(); i>=0; i--) {
				if (board.pieces[i][j] == null) {
					moves.add(board.squares[i][j]);
				} else if (board.pieces[i][j].color == piece.color) {
					break;
				} else {
					moves.add(board.squares[i][j]);
					break;
				}
			}
		}
		
		return moves;
	}
	
	public ArrayList<ISquare> getAvalibleMovesHorizontal(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		// right
		if (piece.coords.getJ() != Board.SQUARES-1) {
			for (int i=piece.coords.getI(), j=piece.coords.getJ()+1; j<Board.SQUARES; j++) {
				if (board.pieces[i][j] == null) {
					moves.add(board.squares[i][j]);
				} else if (board.pieces[i][j].color == piece.color) {
					break;
				} else {
					moves.add(board.squares[i][j]);
					break;
				}
			}
		}
		
		// left
		if (piece.coords.getJ() != 0) {
			for (int i=piece.coords.getI(), j=piece.coords.getJ()-1; j>=0; j--) {
				if (board.pieces[i][j] == null) {
					moves.add(board.squares[i][j]);
				} else if (board.pieces[i][j].color == piece.color) {
					break;
				} else {
					moves.add(board.squares[i][j]);
					break;
				}
			}
		}
		
		return moves;
	}
	
	public ArrayList<ISquare> getAvalibleMovesDiagonal(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		// top left
		if (piece.coords.getJ() != 0 && piece.coords.getI() != 0) {
			for (int i=piece.coords.getI()-1, j=piece.coords.getJ()-1; i>=0 && j>=0; i--, j--) {
				if (board.pieces[i][j] == null) {
					moves.add(board.squares[i][j]);
					break;
				} else if (board.pieces[i][j].color == piece.color) {
					break;
				} else {
					moves.add(board.squares[i][j]);
					break;
				}			
			}
		}
		
		// top right
		if (piece.coords.getJ() != Board.SQUARES-1 && piece.coords.getI() != 0) {
			for (int i=piece.coords.getI()-1, j=piece.coords.getJ()+1; i>=0 && j<Board.SQUARES; i--, j++) {
				if (board.pieces[i][j] == null) {
					moves.add(board.squares[i][j]);
				} else if (board.pieces[i][j].color == piece.color) {
					break;
				} else {
					moves.add(board.squares[i][j]);
					break;
				}			
			}
		}
		
		// bottom left
		if (piece.coords.getJ() != 0 && piece.coords.getI() != Board.SQUARES-1) {
			for (int i=piece.coords.getI()+1, j=piece.coords.getJ()-1; i<Board.SQUARES && j>=0; i++, j--) {
				if (board.pieces[i][j] == null) {
					moves.add(board.squares[i][j]);
				} else if (board.pieces[i][j].color == piece.color) {
					break;
				} else {
					moves.add(board.squares[i][j]);
					break;
				}			
			}
		}
		
		// bottom right
		if (piece.coords.getJ() != Board.SQUARES-1 && piece.coords.getI() != Board.SQUARES-1) {
			for (int i=piece.coords.getI()+1, j=piece.coords.getJ()+1; i<Board.SQUARES && j<Board.SQUARES; i++, j++) {
				if (board.pieces[i][j] == null) {
					moves.add(board.squares[i][j]);
				} else if (board.pieces[i][j].color == piece.color) {
					break;
				} else {
					moves.add(board.squares[i][j]);
					break;
				}			
			}
		}
		
		return moves;
	}
	
	public ArrayList<ISquare> getAvalibleMovesPawn(Piece piece) {
		ArrayList<ISquare> moves = new ArrayList<ISquare>(20);
		
		// can i capture any pieces?
		int i = piece.coords.getI(),
			j = piece.coords.getJ();
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
		Piece possiblePiece;
		
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
		int i = piece.coords.getI(),
			j = piece.coords.getJ();
		int ni, nj;
		boolean color = piece.color;
		
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
		Piece possiblePiece;
		
		int offsets[] = { -1, 0, 1 };
		int ni, nj;
		int i = piece.coords.getI();
		int j = piece.coords.getJ();
		boolean color = piece.color;
		
		for (int n=0; n<offsets.length; n++) {
			for (int m=0; m<offsets.length; m++) {
				if (offsets[n] != 0 || offsets[m] != 0) {
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
		if (moves.size() == 0) {
			return;
		}
		for (ISquare s : moves) System.out.println(s.getCoords().toString());
	}

}
