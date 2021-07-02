package implementation.listeners;

import enums.TokenType;
import implementation.algorithm.PlayerType;
import implementation.core.GameState;
import implementation.core.Token;
import implementation.core.TokenFactory;
import implementation.graphics.GamePainter;
import interfaces.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;

public class InputHandler implements IMouseObserver {
    
    GameState gameState;
    
    GamePainter gamePainter;
    Rectangle[] columnIndicators; // hover-able columns
    Rectangle activeIndicator;
    int activeColumnIndex;
    
    Point mouse = new Point(0, 0);
    
    public InputHandler() { }
    
    public void init(GameState gameState, GamePainter gamePainter) {
        this.gameState = gameState;
        this.activeColumnIndex = -1;
        this.gamePainter = gamePainter;
    
        // hoverable columns
        int m = 7;
        columnIndicators = new Rectangle[m];
        int columnWidth = gamePainter.getPanelSpace().width / m;
        for (int i = 0, x = gamePainter.getPanelSpace().x, y = gamePainter.getPanelSpace().y; i < m; i++, x+=columnWidth) {
            columnIndicators[i] = new Rectangle(
                    x, y,
                    columnWidth,
                    gamePainter.getPanelSpace().height);
        }
        this.activeIndicator = new Rectangle();
        this.activeIndicator.width = columnWidth;
        this.activeIndicator.height = 10;
    }
    
    @Override
    public void onMouseClick(MouseEvent event) {
        this.mouse = event.getPoint();
        if (gameState.isGameOver()) return;
        
        CompletableFuture
                // make a move and process it
                .runAsync(() -> {
                    int move = gameState
                        .getCurrentPlayer()
                        .makeMove(gameState.getGrid());
                    processMove(move);
                })
                // if next move is computers turn, then call this function again
                .thenRun(() -> {
                    if (gameState.getCurrentPlayer().getPlayerType() == PlayerType.COMPUTER)
                        onMouseClick(event);
                });
    }
    
    public void processMove(int inColumn) {
        gameState.isLegalMoveOrThrowException(inColumn);
        if (inColumn == -1 ||
            gameState.isGameOver() ||
            gameState.isColumnFull(inColumn))
            return;
        
        IPlayer player = gameState.getCurrentPlayer();
        int inRow = gameState.getEmptyRowAtColumn(inColumn);
        
        TokenType type = player.getTokenType();
        Token token = new Token();
        token.setType(type);
        token.setPos(gamePainter.getPositionForGridIndex(inRow, inColumn));
        token.setGridPosition(inRow, inColumn);
        token.setDrawingColors(TokenFactory.getTokenFactory().getColorMap().get(type));
        token.setDrawingPosition(new Point(
                (int) columnIndicators[inColumn].getCenterX(),
                columnIndicators[inColumn].y - Token.rad/5));
        
        player.getPlayer().incrementNumberOfMoves();
        gameState.addToken(token, inRow, inColumn);
        gameState.nextPlayer();
    }
    
    @Override
    public void onMouseMove(MouseEvent event) {
        // processed by panel thread itself
        activeColumnIndex = -1;
        for (int i = 0; i < columnIndicators.length; i++) {
            if (columnIndicators[i].contains(event.getPoint())) {
                activeIndicator.x = columnIndicators[i].x;
                activeIndicator.y = columnIndicators[i].y + columnIndicators[i].height - 10;
                gamePainter.setActiveColumnIndicator(activeIndicator);
                activeColumnIndex = i;
                return;
            }
        }
        gamePainter.setActiveColumnIndicator(null);
    }
 
    public int getActiveColumnIndex() { return activeColumnIndex; }
}
