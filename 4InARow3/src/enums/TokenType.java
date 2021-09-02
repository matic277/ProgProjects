package enums;

import implementation.algorithm.Player;
import implementation.core.CurrentPlayers;

import java.util.function.BiConsumer;

public enum TokenType {
    NONE(  (short) 0, null),
    RED(   (short) 1, CurrentPlayers::setNewRedPlayer),
    YELLOW((short) 2, CurrentPlayers::setNewYellowPlayer);
    
    short val;
    private final BiConsumer<CurrentPlayers, Player> playerRewireFunctionRef;
    
    TokenType(short i, BiConsumer<CurrentPlayers, Player> mapper) { this.val = i;this.playerRewireFunctionRef = mapper; }
    
    public short getVal() { return val; }
    
    public BiConsumer<CurrentPlayers, Player> getRewireFunction() { return this.playerRewireFunctionRef; }
}
