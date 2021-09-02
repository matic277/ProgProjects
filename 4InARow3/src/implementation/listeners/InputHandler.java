package implementation.listeners;

import enums.TokenType;
import implementation.core.GameState;
import implementation.core.Token;
import implementation.core.TokenFactory;
import implementation.graphics.GamePanel;
import interfaces.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.CompletableFuture;

public class InputHandler implements MouseMotionListener, MouseListener, IMovingStrategy {
    
    GameState gameState;
    
    GamePanel gamePanel;
    Rectangle[] columnIndicators; // hover-able columns
    Rectangle activeIndicator;
    int activeColumnIndex;
    
    final int activeColumnIndexHeight = 5;
    
    Point mouse = new Point(0, 0);
    
    private final String x;
    
    public InputHandler(String x) { this.x=x; }
    
    public void init(GamePanel gamePanel) {
        this.activeColumnIndex = -1;
        this.gamePanel = gamePanel;
        
        // hoverable columns
        columnIndicators = new Rectangle[7];
        
        int unit = gamePanel.getBoardBounds().width / 7,
               x = gamePanel.getBoardBounds().x,
               y = gamePanel.getBoardBounds().y;
        
        for (int i=0; i<7; i++, x+=unit) {
            columnIndicators[i] = new Rectangle(
                    x, y,
                    unit,
                    gamePanel.getBoardBounds().height);
        }
        this.activeIndicator = new Rectangle();
        this.activeIndicator.width = unit;
        this.activeIndicator.height = activeColumnIndexHeight; // dimension ignored when drawing
    }
    
    public void processMove(int inColumn) {
        gameState.isLegalMoveOrThrowException(inColumn);
        if (gameState.isGameOver() ||
            gameState.isColumnFull(inColumn))
            return;
        
        IPlayer player = gameState.getCurrentPlayer();
        int inRow = gameState.getEmptyRowAtColumn(inColumn);
        
        TokenType type = player.getTokenType();
        Token token = new Token();
        token.setType(type);
        token.setPos(gamePanel.getPositionForGridIndex(inRow, inColumn));
        token.setGridPosition(inRow, inColumn);
        token.setDrawingColors(TokenFactory.getTokenFactory().getColorMap().get(type));
        token.setDrawingPosition(new Point(
                (int) columnIndicators[inColumn].getCenterX() - Token.rad,
                -2*Token.rad));
        
        player.getPlayer().incrementNumberOfMoves();
        gameState.addToken(token, inRow, inColumn);
        gameState.nextPlayer();
    }
    
    public Rectangle[] getColumnIndicators() { return this.columnIndicators; }
    public int getActiveColumnIndex() { return activeColumnIndex; }
    
    @Override
    public int makeMove() {  return activeColumnIndex; }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        for (int i = 0; i < columnIndicators.length; i++) {
            if (columnIndicators[i].contains(e.getPoint())) {
                activeIndicator.x = columnIndicators[i].x;
                activeIndicator.y = columnIndicators[i].y + columnIndicators[i].height - activeColumnIndexHeight;
                gamePanel.setActiveColumnIndicator(activeIndicator);
                activeColumnIndex = i;
                return;
            }
        }
        gamePanel.setActiveColumnIndicator(null);
        //activeColumnIndex = -1;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        this.mouse = e.getPoint();
        if (gameState.isGameOver()) return;
        
        System.out.println("CLICKED: " + x);
        
        CompletableFuture
                // make a move and process it
                .runAsync(() -> {
                    System.out.println("Running async");
                    int move = gameState
                            .getCurrentPlayer()
                            .makeMove(gameState.getGrid());
                    System.out.println("MOVE: " + move);
                    processMove(move);
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                })
                // if next move is computers turn, then call this function again
                .thenRun(() -> {
                    if (gameState.getCurrentPlayer().getPlayerType().isComputerPlayer())
                        mouseClicked(e);
                });
    }
    
    public void setGameState(GameState gameState) { this.gameState = gameState; }
    
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseDragged(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
}
