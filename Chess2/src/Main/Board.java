package Main;
import java.awt.Graphics2D;

import Pieces.Coordinates;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import Pieces.Square;

public class Board {
	
	public Piece pieces[][];
	public Square squares[][];
	
	PieceMoveHandler moveHandler;
	
	History history;
	Player player1, player2;
	
	public static int SQUARES = 8;
	
	public Board() {
		moveHandler = new PieceMoveHandler(this);
	}
	
	// returns true if piece was moved (by calling movePiece method)
	public boolean tryMovingPieceToSquare() {
		return false;
	}
	public void movePieceToSquare() {
		
	}
	
	
	public void highlightPossibleMoveSquares(Piece piece) {
		
	}
	public void resetPossibleMoveSquares() {
		
	}
	
	
	public void highlightSelectedSquare(Square square) {
		square.setColorAsSelected();
	}
	
	
	public void resetSelectedPiece() {
		
	}

	
	

	
	
	

	
	

	public void undoOneMove() {
		
	}
	public void redoOneMove() {
		
	}
	public void jumpToCurrentState() {
		
	}
	public void resetMatch() {
		
	}
	
	
	
	public void resizeSquaresAndPieces() {
		for (int i=0; i<SQUARES; i++)
		for (int j=0; j<SQUARES; j++) {
			if (pieces[i][j] != null) pieces[i][j].updateDrawingPosition();
			squares[i][j].updateDrawingPosition();
		}
	}
	public void drawSquares(Graphics2D g) {
		for (int i=0; i<SQUARES; i++)
		for (int j=0; j<SQUARES; j++)
			squares[i][j].drawSquare(g);
	}
	public void drawPieces(Graphics2D g) {
		for (int i=0; i<SQUARES; i++)
		for (int j=0; j<SQUARES; j++)
			if (pieces[i][j] != null) pieces[i][j].drawPiece(g);
	}
	
	
	public void initSquares() {
		squares = new Square[SQUARES][SQUARES];
		for (int i=0; i<SQUARES; i++)
		for (int j=0; j<SQUARES; j++)
			squares[i][j] = new Square(new Coordinates(i, j));
	}
	public void initPieces() {
		pieces = new Piece[SQUARES][SQUARES];
		
		// WHITE PIECES, color = true
		// pawns
		for (int j=0, i=SQUARES-2; j<SQUARES; j++) pieces[i][j] = new Pawn(new Coordinates(i, j), true, "\u2659");
		
		// rooks
		pieces[SQUARES-1][0] = new Rook(new Coordinates(SQUARES-1, 0), true, "\u2656");
		pieces[SQUARES-1][SQUARES-1] = new Rook(new Coordinates(SQUARES-1, SQUARES-1), true, "\u2656");
		
		// king
		pieces[SQUARES-1][4] = new King(new Coordinates(SQUARES-1, 4), true, "\u2654");
		
		// queen
		pieces[SQUARES-1][3] = new Queen(new Coordinates(SQUARES-1, 3), true, "\u2655");
		
		// bishop
		pieces[SQUARES-1][2] = new Bishop(new Coordinates(SQUARES-1, 2), true, "\u2657");
		pieces[SQUARES-1][5] = new Bishop(new Coordinates(SQUARES-1, 5), true, "\u2657");
		
		
		// knights
		pieces[SQUARES-1][1] = new Knight(new Coordinates(SQUARES-1, 1), true, "\u2658");
		pieces[SQUARES-1][6] = new Knight(new Coordinates(SQUARES-1, 6), true,  "\u2658");
		
		// BLACK PIECES, color = false
		// pawns
		for (int j=0, i=1; j<SQUARES; j++) pieces[i][j] = new Pawn(new Coordinates(i, j), false, "\u265F");
		
		// rooks
		pieces[0][0] = new Rook(new Coordinates(0, 0), false, "\u2656");
		pieces[0][SQUARES-1] = new Rook(new Coordinates(0, SQUARES-1), false, "\u2656");
		
		// king
		pieces[0][4] = new King(new Coordinates(0, 4), false, "\u2654");
		
		// queen
		pieces[0][3] = new Queen(new Coordinates(0, 3), false, "\u2655");
		
		// bishop
		pieces[0][2] = new Bishop(new Coordinates(0, 2), false, "\u2657");
		pieces[0][5] = new Bishop(new Coordinates(0, 5), false, "\u2657");
		
		
		// knights
		pieces[0][1] = new Knight(new Coordinates(0, 1), false, "\u2658");
		pieces[0][6] = new Knight(new Coordinates(0, 6), false, "\u2658");
	}

	public void highlightHoveredSquare(Square square) {
		square.highlightSquareAsHovered();	
	}
	

}
