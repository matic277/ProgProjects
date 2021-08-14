package interfaces;

import enums.TokenType;
import implementation.core.Token;
import implementation.graphics.GamePanel;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface IGameState {
    
    void inputToken(Token token, int inColumn, GamePanel gamePanel);
    void nextPlayer();
    IPlayer getCurrentPlayer();
    
    boolean checkIfGameOver(TokenType[][] grid);
    boolean checkIfGameOver(int[][] grid);
    boolean isGameOver();
    
    TokenType[][] getGrid();
    void setGrid(TokenType[][] grid);
    ConcurrentLinkedQueue<Token> getTokens();
    
    void addToken(Token token, int inRow, int inColumn);
}
