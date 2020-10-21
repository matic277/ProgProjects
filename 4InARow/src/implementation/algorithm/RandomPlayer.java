package implementation.algorithm;

import implementation.core.GameState;
import implementation.graphics.Token;
import interfaces.IPlayer;

import java.util.Random;

public class RandomPlayer extends AbsPlayer implements IPlayer {
    
    Random r;
    
    public RandomPlayer(GameState gameState) {
        super(gameState);
        r = new Random();
    }
    
    @Override
    public int makeMove(Token[][] grid) {
        int[] freeCols = GameState.getGameState().getAndRecalcFreeColumns();
        return freeCols[r.nextInt(freeCols.length)];
    }
}
