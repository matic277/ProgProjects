package implementation.algorithm;

import implementation.core.GameState;
import implementation.core.TokenColor;
import implementation.graphics.Token;

import java.util.ArrayList;
import java.util.Arrays;

public class AbsPlayer {
    
    GameState gameState;
    int[] freeColumns;
    
    public AbsPlayer(GameState gameState) {
        this.gameState = gameState;
        freeColumns = new int[] { 0, 1, 2, 3, 4, 5, 6 };
    }
    

}
