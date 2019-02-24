package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.sql.Time;
import java.util.Timer;

import javax.swing.JLabel;

public class Clock {
	
	int x, y, width, heigth;
	
	Painter painter;
	Interface inter;
	
	Rectangle border;
	JLabel leftClock, rightClock;
	Color borderColor;
	
	public Clock(Painter _painter, Interface _inter) {
		painter = _painter;
		inter = _inter;
		
		initComponents();
	}
	
	public void initComponents() {
		borderColor = Color.blue;
		
		border = new Rectangle(0, 0, 0, 0);
		leftClock = new JLabel();
		rightClock = new JLabel();
	}
	
	public void resizeComponents() {
		x = inter.x + inter.width / 4;
		y = inter.y + inter.height / 2;
		width = inter.width / 2;
		heigth = inter.height / 10;
		
		border.setBounds(x, y, width, heigth);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(borderColor);
		g.draw(border);
	}

}
