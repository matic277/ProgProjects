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
import java.util.concurrent.atomic.AtomicBoolean;

public class InputHandler implements Runnable, IMouseObserver {
    
    GameState gameState;
    
    GamePainter gamePainter;
    Rectangle[] columnIndicators; // hover-able columns
    Rectangle activeIndicator;
    int activeColumnIndex;
    
    Point mouse = new Point(0, 0);
    
    private final Object lock = new Object();
    String threadName;
    AtomicBoolean isWaiting;
    
    public InputHandler() { }
    
    public void init(GameState gameState, GamePainter gamePainter) {
        this.isWaiting = new AtomicBoolean(true);
        this.gameState = gameState;
        this.activeColumnIndex = -1;
        this.gamePainter = gamePainter;
    
        // hoverable columns
        int m = 7;
        columnIndicators = new Rectangle[m];
        int columnWidth = gamePainter.getPanelSpace().width / m;
        for (int i = 0, x = gamePainter.getPanelSpace().x, y = gamePainter.getPanelSpace().y; i < m; i++, x+=columnWidth) {
            columnIndicators[i] = new Rectangle(
                    x,
                    y,
                    columnWidth,
                    gamePainter.getPanelSpace().height);
        }
        this.activeIndicator = new Rectangle();
        this.activeIndicator.width = columnWidth;
        this.activeIndicator.height = 10;
    }
    
    @Override
    public void run() {
        this.threadName = this.getClass().getSimpleName() + "[" + Thread.currentThread().getId() + "]";
        Thread.currentThread().setName(threadName);
//        System.out.println(" + Thread " + threadName + " started.");
        
        while (true) {
            
            // if game is over, then go
            // to sleep until woken up
            // by reset button
            if (gameState.isGameOver()) {
//                System.out.println("   ~ " + threadName +" detected game is over.");
                pause();
            }
    
//            System.out.println("   ~ " + threadName +" waiting for move by: " + gameState.getCurrentPlayer().getPlayerType());
    
            // if current player is human
            // then put yourself to sleep
            // (woken up upon a click)
            if (gameState.getCurrentPlayer().getPlayerType() == PlayerType.HUMAN) {
                pause();
            }
    
            // something has awaken this thread
            // get the next move to play
            // TODO: isWaiting does not get set in makeMove() method
            int move = gameState
                    .getCurrentPlayer()
                    .makeMove(gameState.getGrid(), lock);
    
            System.out.println("   -> INH: move made: " + move);
            // a move has been made
            // process it
            this.processMove(move);
        }
    }
    
    @Override
    public void onMouseClick(MouseEvent event) {
        this.mouse = event.getPoint();
        
        // don't do anything if
        // its computers turn
        if (gameState.getCurrentPlayer().getPlayerType() == PlayerType.COMPUTER)
            return;
        
        // wake up this thread and
        // let it process the click
        synchronized (lock) {
            lock.notify();
        }
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
    
    private void pause() {
        // signal sleep/waiting mode
        isWaiting.set(true);
    
        // self-pause
        System.out.println("   ~ " + threadName + " pausing.");
        synchronized (lock) {
            try { lock.wait(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        System.out.println("\n   ~ " + threadName + " woken-up.");
        isWaiting.set(false);
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
