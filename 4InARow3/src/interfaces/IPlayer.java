package interfaces;

import enums.TokenType;
import implementation.algorithm.Player;
import implementation.algorithm.PlayerType;

public interface IPlayer extends Runnable {
    
    /**
     * input:   Token[][] : state of game
     * output:  int       : column in which to drop token
     * @param grid
     */
    int makeMove(TokenType[][] grid, Object lock);
    
    TokenType getTokenType();
    void setTokenType(TokenType red);
    
    Player getPlayer();
    PlayerType getPlayerType();
}
