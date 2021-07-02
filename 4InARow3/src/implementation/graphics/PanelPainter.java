package implementation.graphics;

import implementation.listeners.MousepadListener;
import interfaces.IDrawable;
import interfaces.IObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;


public class PanelPainter extends JPanel implements IObserver {
    
    private static final long serialVersionUID = 1L;
    
    Color bgColor = Color.white;
    Color infoColor = Color.black;
    
    JFrame frame;
    Dimension bounds;
//    MyButton resetButton;
    
    ArrayList<IDrawable> drawableComponents;

    
    Set<Character> keys = new HashSet<Character>(1);
    Point mouse = new Point();
    boolean mousePressed = false;
    
    int fps = 144;
    
    public PanelPainter(Dimension panelSize, MousepadListener ml) {
        this.bounds = panelSize;
        
        this.setFocusable(true);
        this.setLayout(null);
        this.setPreferredSize(panelSize);
        this.addMouseListener(ml);
        this.addMouseMotionListener(ml);
        this.drawableComponents = new ArrayList<>(30);
        
        ml.addObserver(this);

        frame = new JFrame("Window");
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(bgColor);
        g.fillRect(0, 0, bounds.width, bounds.height);
        
        // anti-aliasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        drawableComponents.forEach(d -> d.draw(g));
        
        // cursor
        g.setColor(mousePressed ? Color.red : Color.black);
        g.drawRect(mouse.x-2, mouse.y-2, 4, 4);
        g.drawString("(" + mouse.x + ", " + mouse.y + ")", mouse.x, mouse.y-10);
        
        // game over
        //g.setColor(Color.black);
        //g.drawString("Game Over: " + GameState.getGameState().isGameOver(), 30, 30);
        
        sleep(fps);
        super.repaint();
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
    public void notifyRightPress(Point location) { }
    @Override
    public void notifyRightRelease(Point location) { }
    @Override
    public void notifyLeftPress(Point location) { }
    @Override
    public void notifyLeftRelease(Point location) { }
}
