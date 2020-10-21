package implementation.listeners;

import implementation.core.GameState;
import implementation.graphics.Painter;
import implementation.graphics.Token;
import interfaces.IObserver;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Set;

public class InputHandler implements IObserver {
    
    Painter painter;
    GameState gameState;
    Token[][] grid;
    
    Rectangle[] columns; // hover-able columns
    Rectangle[] activeColumnIndicators;
    protected int activeColumnIndex;
    
    EventHandler eventHandler;
    
    
    public InputHandler(Painter painter) {
        this.painter = painter;
        gameState = GameState.getGameState();
        grid = gameState.getGrid();
        
        // hoverable columns
        int m = grid[0].length;
        columns = new Rectangle[m];
        Rectangle gamePainterPanel = painter.getGamePainter().getPanelSpace();
        int columnWidth = gamePainterPanel.width / m;
        for (int i = 0, x = gamePainterPanel.x, y = gamePainterPanel.y; i < m; i++, x+=columnWidth) {
            columns[i] = new Rectangle(
                    x,
                    y,
                    columnWidth,
                    gamePainterPanel.height);
        }
        
        // active indicators
        // copy
        Rectangle[] tmp = columns;
        activeColumnIndicators = new Rectangle[tmp.length];
        for (int i=0; i<tmp.length; i++) {
            Rectangle r = new Rectangle(tmp[i]);
            r.setBounds(r.x, r.y + r.height-10, r.width, 10);
            activeColumnIndicators[i] = r;
        }
    }
    
    @Override
    public void notifyMouseMoved(Point location) {
        activeColumnIndex = -1;
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].contains(location)) {
                painter.getGamePainter().setActiveColumnIndicator(activeColumnIndicators[i]);
                activeColumnIndex = i;
                return;
            }
        }
        painter.getGamePainter().setActiveColumnIndicator(null);
    }
    
    @Override
    public void notifyMousePressed(MouseEvent event) {
        // if game is over just never wake up thread with
        // a task again? OR terminate it and then recreate it
        // if reset button is pressed?
        if (activeColumnIndex == -1 || gameState.isGameOver()) return;
        
        Runnable task = getMousePressedTask(event);
        executeEvent(task);
    }
    
    public Runnable getMousePressedTask(MouseEvent event) {
        return () -> {
            // calculate where to spawn token
            Rectangle activeIndicator = activeColumnIndicators[activeColumnIndex];
            Point2D spawnPos = new Point((int)activeIndicator.getCenterX(), (int)activeIndicator.getCenterY() - columns[activeColumnIndex].height);
            
            // insert token
            boolean inserted = gameState
                    .inserTokenInColumn(spawnPos, activeColumnIndex, activeIndicator.width-10); // cheating, determine token rad?
            
            if (inserted) gameState.nextPlayer();
        };
    }
    
    protected void executeEvent(Runnable event) {
        if (eventHandler == null) {
            eventHandler = new EventHandler(event);
            eventHandler.start();
        }
        else eventHandler.wakeThreadAndExecuteTask(event);
    }
    
    @Override
    public void notifyMouseReleased(MouseEvent event) { }
    @Override
    public void notifyRightPress(Point location) { }
    @Override
    public void notifyRightRelease(Point location) { }
    @Override
    public void notifyLeftPress(Point location) { }
    @Override
    public void notifyLeftRelease(Point location) { }
    @Override
    public void notifyKeysPressed(boolean[] keyCodes) { }
    @Override
    public void notifyCharacterKeyPressed(Set<Character> keys) { }
}
