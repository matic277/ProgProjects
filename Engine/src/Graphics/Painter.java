package Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Engine.Engine;
import Engine.KeyboardListener;
import Engine.MousepadListener;
import core.IObserver;

public class Painter extends JPanel implements IObserver {

	private static final long serialVersionUID = 1L;
	
	Color bgColor = Color.white;
	Color infoColor = Color.black;
	
	JFrame frame;
	Engine engine;
	Dimension bounds;
	
	
	Set<Character> keys = new HashSet<Character>(1);
	Point mouse = new Point();
	boolean mousePressed = false;
	boolean leftPressed = false;
	boolean rightPressed = false;
	
	int fps = 144;
	
	public Painter(Engine engine, Dimension panelSize, MousepadListener ml, KeyboardListener kl) {
		this.engine = engine;
		this.bounds = panelSize;
		
		this.setFocusable(true);
		this.setLayout(null);
		this.setPreferredSize(panelSize);
		this.addMouseListener(ml);
		this.addMouseMotionListener(ml);
		this.addKeyListener(kl);
		
		kl.addObserver(this);
		ml.addObserver(this);
		
		mousepad = new Rectangle(20, bounds.height-100, 20, 34);
		leftButton = new Rectangle(22, bounds.height-98, 8, 15);
		rightButton = new Rectangle(30, bounds.height-98, 8, 15);
		
		w = new Rectangle(70, bounds.height-100, 17, 17);
		a = new Rectangle(53, bounds.height-83, 17, 17);
		s = new Rectangle(70, bounds.height-83, 17, 17);
		d = new Rectangle(87, bounds.height-83, 17, 17);

		frame = new JFrame("Window");
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}

	@Override
	protected void paintComponent(Graphics gr){
		Graphics2D g = (Graphics2D) gr;
		
		g.setColor(bgColor);
		g.fillRect(0, 0, bounds.width, bounds.height);
		
		engine.dummyUnits.forEach(b -> b.draw(g));
		engine.walls.forEach(b -> b.draw(g));
		
		engine.bullets.forEach(b -> b.draw(g));
		engine.missiles.forEach(b -> b.draw(g));
		
		engine.asteroids.forEach(b -> b.draw(g));
		engine.enemies.forEach(b -> b.draw(g));
		engine.guards.forEach(b -> b.draw(g));
		
		if (engine.dragon != null) engine.dragon.draw(g);
		
		engine.player.draw(g);

		drawInputs(g);
		
		sleep(fps);
		super.repaint();
	}
	
	Rectangle mousepad; 
	Rectangle leftButton;
	Rectangle rightButton;
	Rectangle w, a, s, d;
	
	private void drawInputs(Graphics2D g) {
		g.setFont(new Font("Consolas", Font.PLAIN, 14));
		g.setColor(infoColor);
		
		g.draw(mousepad);
		if (leftPressed)  g.fill(leftButton);
		if (rightPressed) g.fill(rightButton);
		g.draw(rightButton);
		g.draw(leftButton);
		
		g.draw(w);
		if (keys.contains('w'))  g.fill(w);
		if (keys.contains('a'))  g.fill(a);
		if (keys.contains('s'))  g.fill(s);
		if (keys.contains('d'))  g.fill(d);
		g.draw(w);
		g.draw(a);
		g.draw(s);
		g.draw(d);
		
		g.drawString("Mouse: ("+mouse.x+", "+mouse.y+")", 20, bounds.height-7-5-14-14);
		
		String[] s = {""};
		keys.forEach(c -> s[0] += c + " ");
		
		g.drawString("Keys: ( "+s[0]+")", 20, bounds.height-7-14);
		
	}
	
	private void sleep(int t) {
		try { Thread.sleep(1000 / (long)t); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

	@Override
	public void notifyMouseMoved(Point location) {
		mouse = location;
	}


	@Override
	public void notifyMousePressed(MouseEvent event) {
		mousePressed = !mousePressed;
	}

	@Override
	public void notifyMouseReleased(MouseEvent event) {
		mousePressed = !mousePressed;
	}

	@Override
	public void notifyKeysPressed(boolean[] keyCodes) {}


	@Override
	public void notifyCharacterKeyPressed(Set<Character> keys) {
		this.keys = keys;
	}


	@Override
	public void notifyRightPress(Point location) {
		rightPressed = !rightPressed;
	}


	@Override
	public void notifyRightRelease(Point location) {
		rightPressed = !rightPressed;
		
	}


	@Override
	public void notifyLeftPress(Point location) {
		leftPressed = !leftPressed;
	}


	@Override
	public void notifyLeftRelease(Point location) {
		leftPressed = !leftPressed;
	}
}
