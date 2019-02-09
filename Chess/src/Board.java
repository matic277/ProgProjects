import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Board {
	
	ArrayList<Piece> pieces;
	ArrayList<Integer> possibleMoves;
	
	Piece board[][];
	Square square[][];
	
	static int SQUARES = 8;
	
	public Board() {

	}
	
	public void initPieces() {
		pieces = new ArrayList<Piece>();
		board = new Piece[SQUARES][SQUARES];
		
		
		
		// white pieces, color = true
		for (int i=0; i<SQUARES; i++) {
			Pawn pawn = new Pawn(SQUARES-2, i, true, ResourceLoader.pawnWhiteScaled, "\u2659");
			pieces.add(pawn);
			board[SQUARES-2][i] = pawn;
		}
		
		King k = new King(SQUARES-1, 4, true, ResourceLoader.kingWhiteScaled, "\u2654");
		pieces.add(k);
		board[SQUARES-1][4] = k;
		
		Queen q = new Queen(SQUARES-1, 3, true, ResourceLoader.queenWhiteScaled, "\u2655");
		pieces.add(q);
		board[SQUARES-1][3] = q;
		
		Bishop b = new Bishop(SQUARES-1, 2, true, ResourceLoader.bishopWhiteScaled, "\u2657");
		pieces.add(b);
		board[SQUARES-1][2] = b;
		b = new Bishop(SQUARES-1, 5, true, ResourceLoader.bishopWhiteScaled, "\u2657");
		pieces.add(b);
		board[SQUARES-1][5] = b;
		
		Knight n = new Knight(SQUARES-1, 1, true, ResourceLoader.knightWhiteScaled, "\u2658");
		pieces.add(n);
		board[SQUARES-1][1] = n;
		n = new Knight(SQUARES-1, 6, true, ResourceLoader.knightWhiteScaled, "\u2658");
		pieces.add(n);
		board[SQUARES-1][6] = n;
		
		Rook r = new Rook(SQUARES-1, 0, true, ResourceLoader.rookWhiteScaled, "\u2656");
		pieces.add(r);
		board[SQUARES-1][0] = r;
		r = new Rook(SQUARES-1, SQUARES-1, true, ResourceLoader.rookWhiteScaled, "\u2656");
		pieces.add(r);
		board[SQUARES-1][SQUARES-1] = r;
		
		// black pieces, color = false
		for (int i=0; i<SQUARES; i++) {
			Pawn pawn = new Pawn(1, i, false, ResourceLoader.pawnblackScaled, "\u265F");
			pieces.add(pawn);
			board[1][i] = pawn;
		}
		
		k = new King(0, 4, false, ResourceLoader.kingblackScaled, "\u265A");
		pieces.add(k);
		board[0][4] = k;
		
		q = new Queen(0, 3, false, ResourceLoader.queenblackScaled, "\u265B");
		pieces.add(q);
		board[0][3] = q;
		
		b = new Bishop(0, 2, false, ResourceLoader.bishopblackScaled, "\u265D ");
		pieces.add(b);
		board[0][2] = b;
		b = new Bishop(0, 5, false, ResourceLoader.bishopblackScaled, "\u265D ");
		pieces.add(b);
		board[0][5] = b;
		
		n = new Knight(0, 1, false, ResourceLoader.knightblackScaled, "\u265E");
		pieces.add(n);
		board[0][1] = n;
		n = new Knight(0, 6, false, ResourceLoader.knightblackScaled, "\u265E");
		pieces.add(n);
		board[0][6] = n;
		
		r = new Rook(0, 0, false, ResourceLoader.rookblackScaled, "\u265C");
		pieces.add(r);
		board[0][0] = r;
		r = new Rook(0, 7, false, ResourceLoader.rookblackScaled, "\u265C");
		pieces.add(r);
		board[0][7] = r;
	}
	
	public void drawPieces(Graphics2D g) {
		for (Piece p : pieces) p.draw(g);
	}

	public void setNewImages() {
		for (Piece p : pieces) {
			p.updateImage();
			p.updatePosition();
		}
	}
	
	public void initSquares() {
		square = new Square[SQUARES][SQUARES];
		for (int i=0; i<SQUARES; i++)
		for (int j=0; j<SQUARES; j++) 
			square[i][j] = new Square(i, j);
	}
	
	public void updateSquareSize() {
		for (int i=0; i<SQUARES; i++)
		for (int j=0; j<SQUARES; j++) 
			square[i][j].updateSize();
	}

	public void drawSquares(Graphics2D g) {
		for (int i=0; i<SQUARES; i++)
		for (int j=0; j<SQUARES; j++) 
			square[i][j].draw(g);
		
	}
	
	// select piece and show possible moves
	public void selectPiece(int i, int j) {
		if (!squareContainsPiece(i, j)) return;
		
		possibleMoves = board[i][j].getMovableSquares(board);
		highlightPossibleMoves(possibleMoves);
	}
	
	private void highlightPossibleMoves(ArrayList<Integer> possibleMoves) {
		int ii, jj;
		for (int i=0; i<possibleMoves.size(); i+=2) {
			ii = possibleMoves.get(i);
			jj = possibleMoves.get(i+1);
			
			square[ii][jj].highlight();
		}
	}
	
	public void resetHighlightedMoves() {
		System.out.println("highlight reset");
		for (int i=0; i<SQUARES; i++)
		for (int j=0; j<SQUARES; j++) 
			square[i][j].resetHighlight();
	}

	// useless function, but is being used atm
	private boolean squareContainsPiece(int i, int j) {
		if (board[i][j] != null) return true;
		return false;
	}

	public boolean clickedOnMovableSquare(Square clickedSquare) {
		int pmi, pmj;
		int si = clickedSquare.i;
		int sj = clickedSquare.j;
		
		if (possibleMoves == null) {
			System.out.println("Something went wrong, or there are no possible moves for this piece");
			return false;
		}
		
		for (int i=0; i<possibleMoves.size(); i+=2) {
			pmi = possibleMoves.get(i);
			pmj = possibleMoves.get(i+1);
			//System.out.print(pmi+" "+pmj+" ");
			if (si == pmi && sj == pmj) return true;
		}
		return false;
	}

	public void movePiece(Square clickedSquare1, Square clickedSquare2) {
		int i = clickedSquare1.i;
		int j = clickedSquare1.j;
		
		int ii = clickedSquare2.i;
		int jj = clickedSquare2.j;
		
		printBoard();
		
		// set new location of piece on board and set current location to empty
		// capture scenario
		if(board[ii][jj] != null) {
			System.out.println("removed: " + pieces.remove(board[ii][jj]) );
		}
		board[ii][jj] = board[i][j];
		board[i][j] = null;
		
		
		// move piece (drawing and update its logical i, j position)
		board[ii][jj].move(ii, jj);
	}

	private void printBoard() {
		for(int i=0; i<SQUARES; i++) {
			for(int j=0; j<SQUARES; j++) {
				String name = "_";
				if (board[i][j] != null) name = board[i][j].getClass().getName();
				switch (name) {
					case "King":
						System.out.print("K");
					break;
					
					case "Queen":
						System.out.print("Q");
					break;
						
					case "Pawn":
						System.out.print("P");
					break;
						
					case "Bishop":
						System.out.print("B");
					break;
						
					case "Knight":
						System.out.print("N");
					break;
						
					case "Rook":
						System.out.print("R");
					break;
					
					default:
						System.out.print("_");
					break;
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		
	}

}
