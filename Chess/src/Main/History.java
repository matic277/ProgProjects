package Main;
import java.util.ArrayList;

public class History {
	
	ArrayList<Move> moves;
	
	public History() {
		moves = new ArrayList<Move>(100);
	}
	
	public void recordMove(Move move) {
		moves.add(move);
	}
	
	public void undoLastMove() {
		Move lastMove = getLastMove();
		moves.remove(lastMove);
	}
	
	public Move getLastMove() {
		int max = 0;
		Move last = null;
		for (Move m : moves) {
			if (m.getMoveNumber() > max) {
				last = m;
			}
		}
		return last;
	}

}
