package interfaces;

import implementation.core.GameState;
import implementation.core.GameState.CurrentPlayer.Player;
import implementation.graphics.Token;

public interface IGameState {
    
    boolean inputToken(Token token, IPlayer byPlayer);
    void nextPlayer();
    Player getCurrentPlayer();
    
    boolean checkIfGameOver(Token[][] grid);
    boolean isGameOver();
    
    Token[][] getGrid();
    void setGrid(Token[][] grid);
}
