package interfaces;

import implementation.graphics.Token;

public interface IPlayer {
    /**
     * input:   Token[][] : state of game
     * output:  int       : column in which to drop token
     */
    int makeMove(Token[][] grid);
}
