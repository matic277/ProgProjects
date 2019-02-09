import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceLoader {
	
	static Image rookWhite, rookWhiteScaled;
	static Image bishopWhite, bishopWhiteScaled;
	static Image pawnWhite, pawnWhiteScaled;
	static Image kingWhite, kingWhiteScaled;
	static Image queenWhite, queenWhiteScaled;
	static Image knightWhite, knightWhiteScaled;
	
	static Image rookblack, rookblackScaled;
	static Image bishopblack, bishopblackScaled;
	static Image kingblack, kingblackScaled;
	static Image pawnblack, pawnblackScaled;
	static Image queenblack, queenblackScaled;
	static Image knightblack, knightblackScaled;
	
	// make pieces 80% of size of squares
	static double pieceSizeScale = 0.8;
	
	public ResourceLoader() {
		// white pieces
		rookWhite = loadImage("rook_white.png");
		bishopWhite = loadImage("bishop_white.png");
		pawnWhite = loadImage("pawn_white.png");
		kingWhite = loadImage("king_white.png");
		queenWhite = loadImage("queen_white.png");
		knightWhite = loadImage("knight_white.png");
		
		// black pieces
		rookblack = loadImage("rook_black.png");
		bishopblack = loadImage("bishop_black.png");
		pawnblack = loadImage("pawn_black.png");
		kingblack = loadImage("king_black.png");
		queenblack = loadImage("queen_black.png");
		knightblack = loadImage("knight_black.png");
	}
	
	public void rescale(int sizeInt) {
		double sizeDbl = sizeInt;
		double sizeDbl2 = sizeDbl * pieceSizeScale;
				
		int size = (int) sizeDbl2;
		
		// white pieces
		rookWhiteScaled = rookWhite.getScaledInstance(size, size, 1);
		bishopWhiteScaled = bishopWhite.getScaledInstance(size, size, 1);
		pawnWhiteScaled = pawnWhite.getScaledInstance(size, size, 1);
		kingWhiteScaled = kingWhite.getScaledInstance(size, size, 1);
		queenWhiteScaled = queenWhite.getScaledInstance(size, size, 1);
		knightWhiteScaled = knightWhite.getScaledInstance(size, size, 1);
		
		// black pieces
		rookblackScaled = rookblack.getScaledInstance(size, size, 1);
		bishopblackScaled = bishopblack.getScaledInstance(size, size, 1);
		pawnblackScaled = pawnblack.getScaledInstance(size, size, 1);
		kingblackScaled = kingblack.getScaledInstance(size, size, 1);
		queenblackScaled = queenblack.getScaledInstance(size, size, 1);
		knightblackScaled = knightblack.getScaledInstance(size, size, 1);
	}
	
	public static BufferedImage loadImage(String path) {
		BufferedImage img = null;
		try { System.out.println("Loading: "+"Resources/" + path); img = ImageIO.read(new File("Resources/" + path)); }
		catch (IOException e) { System.out.println("Could not load: "+"Resources/" + path); e.printStackTrace(); }
		return img;
	}

}
