package Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import InputListener.Listener;

public class Painter extends JPanel{
	
	private static final long serialVersionUID = 1L;

	Listener listener;
	
	Board board;
	JFrame frame;
	public Interface interFace;
	
	// height and width of frame
	int height, width;
	
	// dimensions of board
	int boardSize;
	int pieceSize;
	public static int squareSize;
	
	static int SQUARES = 8;
	
	static int fontSize;
	
	// edges (space between board and frame, on all 4 sides)
	public static int EDGE = 15;
	public static int BOARD_EDGE = 10; // edge of the board itself
	
	Color background;
	Color boardEdge;
	

	
	public Painter(Board _board) {
		board = _board;
		listener = new Listener(_board, this);
		init();
	}
	
	public void init() {
		height = 800;
		width = height + height / 2;
		boardSize = height - (2 * EDGE);
		squareSize = (boardSize - 2*BOARD_EDGE) / SQUARES;
		pieceSize = squareSize;
		
		background = new Color(170, 170, 170);
		boardEdge = Color.black;
		
		board.initPieces();
		board.initSquares();
		
		interFace = new Interface(this);
		
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
		
		frame = new JFrame("Chess");
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println("Total dimensions: "+width+" "+height);
		System.out.println("board: "+boardSize);
		System.out.println("squar: "+squareSize);
	}
	
	@Override
	protected void paintComponent(Graphics gg){
		Graphics2D g = (Graphics2D) gg;
		
		resize(g);
		
		// background
		g.setColor(background);
		g.fillRect(0, 0, width, height);
		
		// board edge
		g.setColor(boardEdge);
		g.fillRect(EDGE, EDGE, boardSize, boardSize);
		
		// board
		board.drawSquares(g);
		
		// pieces
		board.drawPieces(g);
		//board.resizeSquaresAndPieces();
		
		// highlighted square, if set
		if (board.hlSquare != null) board.hlSquare.drawSquare(g);
		
		interFace.draw(g);
		
		//super.repaint();
	}
	
	public void resize(Graphics2D g) {
		if (this.getHeight() != height) {
			board.hlSquare = null;
			height = this.getHeight();
			width = width = height + height / 2;;
			BOARD_EDGE = height / 50;
			boardSize = height - (2 * EDGE);
			squareSize = (boardSize - 2*BOARD_EDGE) / SQUARES;
			pieceSize = squareSize;
			fontSize = (int) (squareSize * 0.8);
			
			interFace.resizeInterface();
			
			g.setFont(new Font("Arial Unicode MS", Font.PLAIN, fontSize));
			
			board.resizeSquaresAndPieces();
			super.repaint();
		}
	}
	


}
