package Pieces;


import Main.Painter;

public class Coordinates {
	public int i, j;
	int x, y;
	
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
		x = Painter.BOARD_EDGE + Painter.EDGE + j * Painter.squareSize;
		y = Painter.BOARD_EDGE + Painter.EDGE + i * Painter.squareSize;
		
		// some drawing adjustments
		x += Painter.squareSize / 11;
		y += Painter.squareSize / 1.25;
	}
	
	public String toString() {
		return ("Coords: (" + i + ", " + j + ") -> (" + x + ", " + y + ")");
	}

	public boolean compare(Coordinates coords) {
		if (coords.i == i && coords.j == j) return true;
		return false;
	}
}
