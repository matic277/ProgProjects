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
    
    public Player(GameState gameState, PlayerType pType, IMovingStrategy movStrat) {
        this.gameState = gameState;
        this.movingStrategy = movStrat;
        this.pType = pType;
        freeColumns = new int[] { 0, 1, 2, 3, 4, 5, 6 };
    }
    
    @Override
    public int makeMove(TokenType[][] grid) {
        this.move = movingStrategy.makeMove();
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
    
    public IMovingStrategy getMovingStrat() {
        return this.movingStrategy;
    }
    
    public int getNumberOfMovesMade() { return movesMade; }
    
    public void incrementNumberOfMoves() { this.movesMade++; };
    
    @Override
    public String toString() {
        return "PLAYER[" + tType + ", " + pType + "]";
    }
}
