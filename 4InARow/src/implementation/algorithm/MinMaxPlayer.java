package implementation.algorithm;

import implementation.core.GameState;
import implementation.graphics.Token;
import interfaces.IPlayer;

public class MinMaxPlayer extends AbsPlayer implements IPlayer {
    
    public MinMaxPlayer(GameState gameState) {
        super(gameState);
    }
    
    @Override
    public int makeMove(Token[][] grid) {
        Token[][] copy = grid.clone();
        sim(copy);
        return 0;
    }
    
    public int sim(Token[][] grid) {
        return 0;
    }
    
    public double eval(Token[][] state) {
        // count number of 3-rows
        gameState.checkIfGameOver();
        return 0;
    }
}
