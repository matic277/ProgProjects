import java.awt.Image;
import java.util.ArrayList;

public class King extends Piece {

	public King(int _i, int _j, boolean _color, Image _img, String _unicodeChar) {
		super(_i, _j, _color, _img, _unicodeChar);
		// TODO Auto-generated constructor stub
	}
	
	public void updateImage() {
		if (color) img = ResourceLoader.kingWhiteScaled;
		else img = ResourceLoader.kingblackScaled;
	}
	
	public ArrayList<Integer> getMovableSquares(Piece[][] board) {
		ArrayList<Integer> moves = new ArrayList<Integer>(15);
		
		System.out.println("I am "+color+" as "+this.getClass().getName()+" at "+i+" "+j);
		
		int seq[] = {-1, 0, 1};
		int pi, pj;
		
		for (int i=0; i<seq.length; i++)
		for (int j=0; j<seq.length; j++) {
			if (!(seq[i] == 0 && seq[j] == 0)) {
				pi = this.i + seq[i];
				pj = this.j + seq[j];
				
				if (stillInBounds(pi, pj) && !isSquareTaken(pi, pj, board)) addMove(pi, pj, moves);
				System.out.println(pi+" "+pj);
			}
		}

		return moves;
	}
}
