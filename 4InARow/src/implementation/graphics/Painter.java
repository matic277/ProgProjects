package implementation.graphics;

import implementation.core.GameState;
import implementation.core.Pair;
import implementation.core.TokenColor;
import implementation.core.TokenFactory;
import implementation.listeners.InputHandler;
import implementation.listeners.KeyboardListener;
import implementation.listeners.MousepadListener;
import interfaces.Callable;
import interfaces.IDrawable;
import interfaces.IObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Painter extends JPanel implements IObserver {

	private static final long serialVersionUID = 1L;
	
	Color bgColor = Color.white;
	Color infoColor = Color.black;
	
	JFrame frame;
	Dimension bounds;
	MyButton resetButton;
	
	ArrayList<IDrawable> drawableComponents;
	GamePainter gamePainter; // ugly, already in drawableComponents
	
	Set<Character> keys = new HashSet<Character>(1);
	Point mouse = new Point();
	boolean mousePressed = false;
	boolean leftPressed = false;
	boolean rightPressed = false;
	
	int fps = 144;
	
	public Painter(Dimension panelSize, MousepadListener ml, KeyboardListener kl) {
		this.bounds = panelSize;
		
		this.setFocusable(true);
		this.setLayout(null);
		this.setPreferredSize(panelSize);
		this.addMouseListener(ml);
		this.addMouseMotionListener(ml);
		this.addKeyListener(kl);
		this.drawableComponents = new ArrayList<>(30);
		this.resetButton = new MyButton("Reset");
		this.resetButton.setAction(GameState.getGameState().getResetAction());
		this.resetButton.setBounds(new Rectangle(100, 50, 80, 40));
		this.resetButton.addActionListener(a -> resetButton.executeAction(gamePainter.getPanelSpace()));
		this.resetButton.setFocusable(false); // otherwise keylistener stops working after clicked once
		this.add(resetButton);
		
		kl.addObserver(this);
		ml.addObserver(this);
		
		initGamePainter();
		
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
	
	private void initGamePainter() {
		TokenFactory tfact = TokenFactory.getTokenFactory();
		Rectangle panel = new Rectangle(100, 100, 700, 600);
		GameState.getGameState().initGameStateGrid(panel); // this shouldn't be done here
		gamePainter = new GamePainter(panel, tfact.getTokenRad());
		gamePainter.setGameState(GameState.getGameState());
		this.addDrawableComponent(gamePainter);
	}
	
	@Override
	protected void paintComponent(Graphics gr){
		Graphics2D g = (Graphics2D) gr;
		g.setColor(bgColor);
		g.fillRect(0, 0, bounds.width, bounds.height);
		
		drawableComponents.forEach(d -> d.draw(g));
		
		drawInputs(g);
		
		// cursor
		g.setColor(mousePressed ? Color.red : Color.black);
		g.drawRect(mouse.x-2, mouse.y-2, 4, 4);
		g.drawString("(" + mouse.x + ", " + mouse.y + ")", mouse.x, mouse.y-10);
		
		// game over
		g.setColor(Color.black);
		g.drawString("Game Over: " + GameState.getGameState().isGameOver(), 30, 30);
		
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
		
		g.drawString("Mouse: ("+mouse.x+", "+mouse.y+")", 20, bounds.height-40);
		
		String[] s = {""};
		keys.forEach(c -> s[0] += c + " ");
		g.drawString("Keys: ( "+s[0]+")", 20, bounds.height-7-14);
	}
	
	public void addDrawableComponent(IDrawable drawable) {
		drawableComponents.add(drawable);
	}
	
	public static void sleep(int t) {
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
	public void notifyCharacterKeyPressed(Set<Character> keys) { this.keys = keys; }
	@Override
	public void notifyRightPress(Point location) {
		rightPressed = !rightPressed;
	}
	@Override
	public void notifyRightRelease(Point location) { rightPressed = !rightPressed; }
	@Override
	public void notifyLeftPress(Point location) {
		leftPressed = !leftPressed;
	}
	@Override
	public void notifyLeftRelease(Point location) {
		leftPressed = !leftPressed;
	}
	
	public GamePainter getGamePainter() {
		return gamePainter;
	}
	
	private static class MyButton extends JButton {
		Callable c;
		public MyButton(String text) { super(text); }
		void setAction(Callable c) { this.c = c; }
		void executeAction(Rectangle panelSpace) { c.run(panelSpace); }
	}
}
