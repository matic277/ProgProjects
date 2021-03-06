package Graphics;

import Agent.Algorithm;
import Agent.KnowledgeBase;
import Game.Grid;
import Game.Tile;
import Game.TileRect;
import Listeners.MousepadListener;
import interfaces.IObserver;
import interfaces.IShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;


public class Painter extends JPanel implements IObserver {
    
    private static final long serialVersionUID = 1L;
    
    Color bgColor = Color.white;
    
    MousePointerShape pointerShape = new MousePointerShape();
    Grid grid;
    
    JFrame frame;
    Dimension bounds;
    Dimension tileSize;
    Point gridStartPosition;
    Set<Character> keys = new HashSet<>(1);
    Point mouse = new Point();
    
    boolean mousePressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;
    
    int fps = 144;
    
    public Painter(Dimension gridSize, MousepadListener ml, Grid grid) {
        this.tileSize = new Dimension(70, 70);
        this.bounds = new Dimension(gridSize.width * 2 * tileSize.width + 4 * 20, gridSize.height * tileSize.height + 2 * 50);
        if (bounds.width < 750) bounds.width = 750;
        this.grid = grid;
        this.gridStartPosition = new Point(20, 80);
        this.setFocusable(true);
        this.setLayout(null);
        this.setPreferredSize(bounds);
        this.addMouseListener(ml);
        this.addMouseMotionListener(ml);
        
        ml.addObserver(this);
        
        int truthGridOffset = gridStartPosition.x + tileSize.width*gridSize.width + gridStartPosition.x;
        grid.setTilePositionsAndSize(gridStartPosition, tileSize, truthGridOffset);
        
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
        
        g.setColor(Color.BLACK);
        g.drawString("Left / Right click to see calculated path and to move.", 22, 25);
        g.drawString("Indicates visited square.", 360, 55);
        g.drawString("Indicates path.", 360, 25);
        g.drawString("Indicates safe / to_explore square.", 540, 25);
    
        g.setColor(Color.RED);
        g.drawOval(330, 10, 20, 20);
    
        g.setColor(Color.PINK);
        g.fillOval(330, 40, 20, 20);
    
        g.setColor(new Color(162, 239, 48));
        g.fillRect(510, 10, 20, 20);
        
        grid.draw(g);
        
        pointerShape.draw(mouse, g);
        
        sleep(fps);
        super.repaint();
    }

    private void sleep(int t) {
        try { Thread.sleep(1000 / (long)t); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
    
    public void changeMousePointerShape(IShape shape) {
        pointerShape.setCurrentShape(shape);
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
    public void notifyMouseClicked(Point location) {
        synchronized (Algorithm.lock) {
            Algorithm.makeNextMove = true;
            Algorithm.lock.notify();
        }
//        TileRect clickedTile = grid.getClickedTile(location);
//        System.out.println(grid.toString());
//        if (clickedTile == null || pointerShape.getHeldTile() == null ||
//            clickedTile.getTileType() == Tile.PLAYER) {
//            pointerShape.setShapeToBlank();
//            pointerShape.setHoldingTile(null);
//            return;
//        }
//        if (clickedTile.getTileType() == pointerShape.getHeldTile()) {
//            clickedTile.setTileType(Tile.UNKNOWN, grid.getTileDrawer(Tile.UNKNOWN));
//            return;
//        }
//        clickedTile.setTileType(pointerShape.getHeldTile(), grid.getTileDrawer(pointerShape.getHeldTile()));
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
    
    public void changeMousePointerHeldType(Tile tile) {
        pointerShape.setHoldingTile(tile);
    }
}
