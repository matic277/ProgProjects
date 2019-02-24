package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Interface {
	
	int SEPARATOR;
	
	Painter painter;
	Rectangle border;
	JTextField player1Name, player2Name;
	Rectangle player1Rect, player2Rect;
	Color borderColor;
	
	Clock clock;
	
	public JButton undo, redo;
	Rectangle undoRect, redoRect;
	int btnHeight, btnWidth;
	
	// border coordinates
	int x, y, width, height;
	
	public Interface(Painter _painter) {
		painter = _painter;
		border = new Rectangle();
		borderColor = Color.black;

		initComponents();
		resizeInterface();
	}

	public void resizeInterface() {
		int emptySpace = Painter.BOARD_EDGE + Painter.EDGE;
		x = painter.boardSize+SEPARATOR+emptySpace;
		y = emptySpace;
		width = painter.width-painter.boardSize+SEPARATOR-2*emptySpace;
		height = painter.height-2*emptySpace;
		
		resizeComponents();
		clock.resizeComponents();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(borderColor);
		g.draw(border);
		clock.draw(g);
	}
	
	public void initComponents() {
		clock = new Clock(painter, this);
		
		undo = new JButton("<--");
		redo = new JButton("-->");
		undoRect = new Rectangle(0, 0, 0, 0);
		redoRect = new Rectangle(0, 0, 0, 0);
		
		player1Name = new JTextField("Player1White");
		player2Name = new JTextField("Player2Black");
		player1Rect = new Rectangle(0, 0, 0, 0);
		player2Rect = new Rectangle(0, 0, 0, 0);
		
		undo.addActionListener(painter.listener);
		redo.addActionListener(painter.listener);
		painter.add(undo);
		painter.add(redo);
		painter.add(player1Name);
		painter.add(player2Name);
	}
	
	private void resizeComponents() {
		border.setBounds(x, y, width, height);
		
		// buttons
		btnWidth = painter.height / 10;
		btnHeight = btnWidth / 2;
		undoRect.setBounds(x+1, y+1, btnWidth, btnHeight);
		redoRect.setBounds(x+width-btnWidth, y+1, btnWidth, btnHeight);
		
		undo.setBounds(undoRect);
		redo.setBounds(redoRect);
		
		// player names
		player1Rect.setBounds(x+1, y+painter.height/3, btnWidth, btnHeight);
		player2Rect.setBounds(x+width-btnWidth-1, y+painter.height/3, btnWidth, btnHeight);
		
		player1Name.setBounds(player1Rect);
		player2Name.setBounds(player2Rect);
	}

}
