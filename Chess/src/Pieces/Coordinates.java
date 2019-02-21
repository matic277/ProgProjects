package Pieces;
import Main.Painter;

public class Coordinates {
	private int i, j;
	public int x, y;
	
	public Coordinates(int _i, int _j) {
		i = _i;
		j = _j;
	}
	
	public void updateDrawingCoordsSquare() {
		int size = Painter.squareSize;
		x = Painter.BOARD_EDGE + Painter.EDGE + j * size;
		y = Painter.BOARD_EDGE + Painter.EDGE + i * size;
	}
	
	public void updateDrawingCoordsPiece() {
		int x1 = x, y1 = y;
		x = Painter.BOARD_EDGE + Painter.EDGE + j * Painter.squareSize;
		y = Painter.BOARD_EDGE + Painter.EDGE + i * Painter.squareSize;
		
		// some drawing adjustments
		y += Painter.squareSize - Painter.squareSize / 11 ;
	}
	
	public void setNewCoords(Coordinates coords) {
		i = coords.i;
		j = coords.j;
	}
	
	public String toString() {
		return ("Coords: (" + i + ", " + j + ") -> (" + x + ", " + y + ")");
	}
	
	public String toStringCompact() {
		return ("(" + i + ", " + j + ") -> (" + x + ", " + y + ")");
	}
	
	public String toStringDrawingCoords() {
		return ("(" + x + ", " + y + ")");
	}

	public boolean compare(Coordinates coords) {
		if (coords.i == i && coords.j == j) return true;
		return false;
	}
	
	public int getI() {
		return i;
	}
	
	public int getJ() {
		return j;
	}
}
