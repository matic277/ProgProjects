import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Listener implements MouseListener, MouseMotionListener{
	
	Board board;
	Painter painter;
	
	// coordinates and object, clicked
	Square clickedSquare1;
	Square clickedSquare2;
	int si1, sj1;
	int si2, sj2;
	
	public Listener(Board _board, Painter _painter) {
		board = _board;
		painter = _painter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {	
		int mx = e.getX();
		int my = e.getY();
		
		//System.out.println("clicked: "+mx+" "+my);
		
		if (clickedSquare1 != null) {
			loop: 
				for (int i=0; i<Board.SQUARES; i++)
				for (int j=0; j<Board.SQUARES; j++) {
					if (board.square[i][j].rect.contains(mx, my)) {
						clickedSquare2 = board.square[i][j];
						si2 = i;
						sj2 = j;
						break loop;
					} else 
						clickedSquare2 = null;
				}
		
			//System.out.println("square 2 set at "+sy2+" "+sx2);
			
			//if (board.square[0][0] == board.square[0][0]) System.out.println("same");
			
		/*
			if (clickedSquare1 == clickedSquare2) {
				clickedSquare1 = null;
				clickedSquare2 = null;
				board.possibleMoves = null;
				board.resetHighlightedMoves();
				return;
			}
		*/
			
			if (board.clickedOnMovableSquare(clickedSquare2)) {
				//System.out.println("clicked on movable");
				board.movePiece(clickedSquare1, clickedSquare2);

			}
			
			clickedSquare1 = null;
			clickedSquare2 = null;
			board.possibleMoves = null;
			board.resetHighlightedMoves();
			return;
		}
		
		
		
		
		loop: 
		for (int i=0; i<Board.SQUARES; i++)
		for (int j=0; j<Board.SQUARES; j++) {
			if (board.square[i][j].rect.contains(mx, my)) {
				clickedSquare1 = board.square[i][j];
				si1 = i;
				sj1 = j;
				break loop;
			} else 
				clickedSquare1 = null;
		}
		
		if (clickedSquare1 != null) {
			if (board.board[si1][sj1] == null) clickedSquare1 = null;
			else board.selectPiece(si1, sj1);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		//System.out.println("moveddd: "+mx+" "+my);
		
		for (int i=0; i<board.SQUARES; i++)
		for (int j=0; j<board.SQUARES; j++) {
			if (board.square[i][j].rect.contains(mx, my)) board.square[i][j].changeColor();
			else board.square[i][j].resetColor();	
		}
		
	}

}
