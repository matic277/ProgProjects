package implementation.graphics;

import implementation.core.GameState;
import interfaces.IDrawable;

import java.awt.*;

public class GamePainter implements IDrawable {
    
    // Everything should be drawn in this space constraint
    Rectangle panelSpace;
    Rectangle mouseIndicatorSpace; // height: 10px
    Rectangle mouseIndicator;
    int tokenRad;
    
    Color borderColor = Color.BLACK;
    Color boardColor = new Color(22, 75, 238);
    Color backgroundColor = Color.WHITE;
    Color mouseIndicatorBackgroundColor = new Color(15, 59, 203);
    Color activeMouseIndicatorColor = Color.red;
    
    Rectangle[] activeColumnsIndicators;
//    int activeColumnIndex;
    Rectangle activeColumnIndicator;
    
    GameState gameState;
    Token[][] grid;
    
    
    public GamePainter(Rectangle panelSpace, int tokenRad) {
        this.panelSpace = panelSpace;
        this.tokenRad = tokenRad;
        this.mouseIndicatorSpace = new Rectangle(
                (int)(panelSpace.getX()), (int)(panelSpace.getY() + panelSpace.getHeight() - 10),
                panelSpace.width, 10);
        this.mouseIndicator = new Rectangle(0, 0, tokenRad*2, 10);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(boardColor);
        g.fill(panelSpace);
        
        // tokens
        for (Token[] ts : grid) for (Token t : ts) {
            t.draw(g);
        }
        
        // transparent so tokens can be seen behind game board
//        g.setColor(boardColor);
        g.draw(panelSpace);
        for (Token[] ts : grid) for (Token t : ts) {
            t.draw(g);
        }
        
        // indicators
        g.setColor(mouseIndicatorBackgroundColor);
        g.fill(mouseIndicatorSpace);
        
        if (activeColumnIndicator != null) {
            g.setColor(activeMouseIndicatorColor);
            g.fill(activeColumnIndicator);
        }
        
        g.setColor(borderColor);
        g.draw(panelSpace);
    }
    
    public void setActiveColumnIndicator(Rectangle activeColumnIndicator) {
        this.activeColumnIndicator = activeColumnIndicator;
//        System.out.println(columnIndex);
    }
    
    public void setGameState(GameState gameState) { this.gameState = gameState; grid = gameState.getGrid(); }
    
    public Rectangle getPanelSpace() {
        return this.panelSpace;
    }
}
