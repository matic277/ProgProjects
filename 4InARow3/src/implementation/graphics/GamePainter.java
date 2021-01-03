package implementation.graphics;

import enums.TokenType;
import implementation.core.GameState;
import implementation.core.Pair;
import implementation.core.Token;
import interfaces.IDrawable;
import interfaces.IMouseObserver;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class GamePainter implements IDrawable {
    
    // Everything should be drawn in this space constraint
    Rectangle bounds;
    Rectangle mouseIndicatorSpace; // height: 10px
    
    Color borderColor = Color.BLACK;
    Color boardColor = new Color(22, 75, 238);
    Color mouseIndicatorBackgroundColor = new Color(15, 59, 203);
    Color activeMouseIndicatorColor = Color.red;
    
    Rectangle activeColumnIndicator;
    
    GameState gameState;
    
    Token[][] drawingGrid; // blank grid only for drawing white token holes in board
    
    
    public GamePainter(Rectangle bounds) {
        this.bounds = bounds;
        this.mouseIndicatorSpace = new Rectangle(
                (int)(bounds.getX()), (int)(bounds.getY() + bounds.getHeight() - 10),
                bounds.width, 10);
        initGrid();
    }
    
    public void initGrid() {
        int n = 6, m = 7;
        int space = 10; // spacing between tokens
        int rad = (bounds.width - (m+1)*space) / m;
        Token.rad = rad;
        
        this.drawingGrid = new Token[n][m];
        
        for (int i=0, y=bounds.x+space+rad/2; i<n; i++, y+=rad+space) {
            for (int j=0, x=bounds.x+space+rad/2; j<m; j++, x+=rad+space) {
                Token t = new Token() {
                    @Override
                    public void draw(Graphics2D g) {
                        g.setColor(Color.WHITE);
                        this.fillOval(g, (int)this.getDrawingPosition().get().getX(),
                                (int)this.getDrawingPosition().get().getY(), Token.rad);
//                        g.setColor(Color.red);
//                        g.fillRect((int)this.getDrawingPosition().get().getX()-2,
//                                (int)this.getDrawingPosition().get().getY()-2, 4, 4);
                    }
                };
                t.setDrawingPosition(new Point(x, y));
                drawingGrid[i][j] = t;
            }
        }
        
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(boardColor);
        g.fill(bounds);

        // white spaces for where tokens can drop
        for (Token[] tokens : drawingGrid) for (Token token : tokens) {
            token.draw(g);
        }
        
        // tokens
        gameState.getTokens().forEach(t -> t.draw(g));
    
//        TokenType[][] gr = gameState.getGrid().clone();
//        for (int i=0; i<6; i++) for (int j=0; j<7; j++) {
//            TokenType tt = gr[i][j];
//            Color c = switch (tt) {
//                case NONE -> Color.black;
//                case YELLOW -> Color.yellow;
//                case RED -> Color.red;
//            };
//            g.setColor(c);
//            int finalI = i;
//            int finalJ = j;
//            gameState.getTokens().stream().filter(t -> {
//                Pair<Integer, Integer> pos = t.getGridPosition();
//                return pos.getA() == finalI && pos.getB() == finalJ;
//            }).forEach(t -> { t.drawIndicator(g); });
//        }

        
        // indicators
        g.setColor(mouseIndicatorBackgroundColor);
        g.fill(mouseIndicatorSpace);
        
        if (activeColumnIndicator != null) {
            g.setColor(activeMouseIndicatorColor);
            g.fill(activeColumnIndicator);
        }
    }
    
    public void setActiveColumnIndicator(Rectangle activeColumnIndicator) {
        this.activeColumnIndicator = activeColumnIndicator;
//        System.out.println(columnIndex);
    }
    
    public Point getPositionForGridIndex(int row, int column) {
        // return drawingPos of dummy tokens
        // that are used to draw white spaces
        return drawingGrid[row][column].getDrawingPosition().get();
    }
    
    public void setGameState(GameState gameState) { this.gameState = gameState; }
    public Rectangle getPanelSpace() { return this.bounds; }
}
