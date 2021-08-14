package implementation.graphics;

import implementation.core.GameState;
import implementation.core.Main;
import implementation.core.Token;
import implementation.listeners.InputHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    
    MainPanel mainPanel;
    InputHandler inputHandler;
    GameState gameState;
    
    final int cols = 7; // width
    final int rows = 6; // height
    int unit; // px, one unit of square, inside which a circle is drawn
    int boardBorderThickness;
    
    // Everything should be drawn in this space constraint
    Rectangle boardBounds;
    Rectangle mouseIndicatorSpace; // height: 10px
    
    Color bgColor = new Color(243, 243, 243);
    Color boardColor = new Color(22, 75, 238);
    //Color mouseIndicatorBackgroundColor = boardColor;
    
    
    Rectangle activeColumnIndicator;
    Color activeMouseIndicatorColor = boardColor.brighter().brighter();
    
    Token[][] drawingGrid; // blank grid only for drawing white token holes in board
    
    public GamePanel(MainPanel parent,GameState gameState) {
        this.mainPanel = parent;
        this.gameState = gameState;
        initGrid();
        
        this.setPreferredSize(new Dimension(
                boardBounds.width  + 2 * Main.borderSize,
                boardBounds.height + 2 * Main.borderSize));
    }
    
    /*   This is a single *unit* that makes up the 6x7 board
         ________________
        |    ____|____   |
        |   |         |  |
        |   |    .____|--|  <-- spacing lines, inside should be the circle
        |   |     rad |  |      inner rectangle is bounds for circle
        |   |---------|  |
        |________________|
     */
    public void initGrid() {
        int rad = Main.rad;
        int space = Main.space;
        
        unit = 2 * (space + rad);
        boardBorderThickness = space;
        
        Token.rad = rad;
        
        Rectangle parentConstraints = mainPanel.getBounds();
        this.boardBounds = new Rectangle(
                parentConstraints.x + Main.borderSize,
                parentConstraints.y + Main.borderSize,
                unit * cols,
                unit * rows);
        
        this.drawingGrid = new Token[rows][cols];
        
        for (int i = 0, y = boardBounds.y+space; i<rows; i++, y+=unit)
        for (int j = 0, x = boardBounds.x+space; j<cols; j++, x+=unit) {
            Token t = new Token() {
                final Color ringColor = new Color(248, 248, 248);
                @Override public void draw(Graphics2D g) {
                    g.setColor(bgColor);
                    g.fillOval(
                            (int)this.getDrawingPosition().get().getX(),
                            (int)this.getDrawingPosition().get().getY(), Token.rad*2, Token.rad*2);
                    g.setColor(ringColor);
                    g.setStroke(new BasicStroke(2));
                    g.drawOval(
                            (int)this.getDrawingPosition().get().getX() + 1,
                            (int)this.getDrawingPosition().get().getY() + 1,
                            Token.rad*2-3, Token.rad*2-3) ;
                }
            };
            t.setDrawingPosition(new Point(x, y));
            drawingGrid[i][j] = t;
        }
    
        this.mouseIndicatorSpace = new Rectangle(
                (int)(boardBounds.getX()), (int)(boardBounds.getY() + boardBounds.getHeight() - 10),
                boardBounds.width, 10);
        
        inputHandler = new InputHandler();
        inputHandler.init(this);
        inputHandler.setGameState(mainPanel.getGameState());
        
        this.addMouseListener(inputHandler);
        this.addMouseMotionListener(inputHandler);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        gr.setColor(bgColor);
        gr.fill(this.getBounds());
        
        // anti-aliasing
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        gr.setColor(boardColor);
        gr.fillRoundRect(
                boardBounds.x - boardBorderThickness, boardBounds.y - boardBorderThickness,
                boardBounds.width + 2*boardBorderThickness, boardBounds.height + 2*boardBorderThickness,
                15, 15);
        
        
        // white spaces for where tokens can drop
        for (Token[] tokens : drawingGrid) for (Token token : tokens) {
            token.draw(gr);
        }
    
        // tokens
        gameState.getTokens().forEach(t -> t.draw(gr));
        
        if (activeColumnIndicator != null) {
            gr.setColor(activeMouseIndicatorColor);
            gr.fillRoundRect(
                    activeColumnIndicator.x, activeColumnIndicator.y + boardBorderThickness,
                    activeColumnIndicator.width, boardBorderThickness,
                    15, 15);
        }
    }
    
    public void setActiveColumnIndicator(Rectangle activeColumnIndicator) {
        this.activeColumnIndicator = activeColumnIndicator;
    }
    
    public Point getPositionForGridIndex(int row, int column) {
        // return drawingPos of dummy tokens
        // that are used to draw white spaces
        return drawingGrid[row][column].getDrawingPosition().get();
    }
    
    public void setGameState(GameState gameState) { this.gameState = gameState; }
    public Rectangle getBoardBounds() { return this.boardBounds; }
    
    public InputHandler getInputHandler() { return this.inputHandler; }
    
}
