package implementation.algorithm;

import enums.TokenType;
import implementation.core.GameState;
import interfaces.IMovingStrategy;
import interfaces.IPlayer;

import java.util.concurrent.atomic.AtomicBoolean;

public class Player implements IPlayer {
    
    GameState gameState;
    int[] freeColumns;
    PlayerType pType;
    TokenType tType;
    
    IMovingStrategy movingStrategy;
    int move;
    int movesMade;
    
    private final Object lock = new Object();
    private Object lockOfCaller = new Object();
    String threadName;
    AtomicBoolean isWaiting;
    
    public Player(GameState gameState, PlayerType pType, IMovingStrategy movStrat) {
        this.isWaiting = new AtomicBoolean(true);
        this.gameState = gameState;
        this.movingStrategy = movStrat;
        this.pType = pType;
        freeColumns = new int[] { 0, 1, 2, 3, 4, 5, 6 };
    }
    
    @Override
    public void run() {
        this.threadName = this.getClass().getSimpleName() + "[" + Thread.currentThread().getId() + "]";
        Thread.currentThread().setName(threadName);
        System.out.println(" + Thread " + threadName + " started.");
        
        while (true) {
            // signal sleep/waiting mode
            isWaiting.set(true);
        
            // self-pause after task is completed
            System.out.println("   ~ " + threadName + " pausing.");
            synchronized(lock) {
                try { lock.wait(); }
                catch (Exception e) { /* Do nothing */ e.printStackTrace(); }
            }
            System.out.println("   ~ " + threadName + " woken-up.");
            isWaiting.set(false);
            
            // this thread has been woken up
            // make a move
            this.move = movingStrategy.makeMove();
            
            // report that a move
            // has been made, inputHandler
            // will read this.move field
            synchronized (lockOfCaller) {
                lockOfCaller.notify();
            }
        }
        // unreachable
    }
    
    @Override
    public int makeMove(TokenType[][] grid, final Object lockOfCaller) {
        // save the lock of caller
        // and wake up this thread
        this.lockOfCaller = lockOfCaller;
        synchronized (lock) {
            lock.notify();
        }
        
        // make caller put himself to sleep
        synchronized (lockOfCaller) {
            try {lockOfCaller.wait(); }
            catch (Exception e ) { e.printStackTrace(); }
        }
        
        // here, caller is woken up
        // by this thread and this
        // means the result (move)
        // has been made
        return move;
    }
    
    @Override
    public TokenType getTokenType() {
        return tType;
    }
    
    @Override
    public void setTokenType(TokenType type) {
        this.tType = type;
    }
    
    @Override
    public Player getPlayer() { return this; }
    
    @Override
    public PlayerType getPlayerType() { return pType; }
    
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    public void setPlayerType(PlayerType type) {
        this.pType = type;
    }
    
    public void setMovingStrat(IMovingStrategy movStrat) {
        this.movingStrategy = movStrat;
    }
    
    public int getNumberOfMovesMade() { return movesMade; }
    
    public void incrementNumberOfMoves() { this.movesMade++; };
}
