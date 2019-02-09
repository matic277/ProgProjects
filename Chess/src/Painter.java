import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Painter extends JPanel{
	
	ResourceLoader resources;
	Listener listener;
	
	Board board;
	JFrame frame;
	
	// height and width of frame
	int height, width;
	
	// dimensions of board
	int boardSize;
	int pieceSize;
	static int squareSize;
	
	static int SQUARES = 8;
	
	static int fontSize;
	
	// edges (space between board and frame, on all 4 sides)
	static final int EDGE = 15;
	static final int BOARD_EDGE = 10; // edge of the board itself
	
	Color background;
	Color boardEdge;
	
	public Painter(Board _board, ResourceLoader _resources) {
		board = _board;
		resources = _resources;
		listener = new Listener(_board, this);
		init();
	}
	
	public void init() {
		height = width = 800;
		boardSize = height - (2 * EDGE);
		squareSize = (boardSize - 2*BOARD_EDGE) / SQUARES;
		pieceSize = squareSize;
		
		resources.rescale(pieceSize);
		
		background = new Color(170, 170, 170);
		boardEdge = Color.black;
		
		board.initPieces();
		board.initSquares();
		
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
		board.updateSquareSize();
		
		super.repaint();
	}
	
	public void resize(Graphics2D g) {
		height = this.getHeight();
		width = this.getWidth();
		boardSize = height - (2 * EDGE);
		squareSize = (boardSize - 2*BOARD_EDGE) / SQUARES;
		pieceSize = squareSize;
		fontSize = (int) (squareSize * 0.8);
		
		resources.rescale(pieceSize);
		board.setNewImages();
		
		// resizing font size if pieces are unicode
		g.setFont(new Font("Arial Unicode MS", Font.PLAIN, fontSize));
	}

}
