package implementation.core;

import enums.TokenType;
import implementation.algorithm.Player;
import interfaces.IPlayer;

// TODO: this should be removed and simplified

// recursive structure to get rid of if statements
// ... -> player1 -> player2 -> player1 -> ...
public class CurrentPlayer {
    SubPlayer player;
    public CurrentPlayer(IPlayer p1, IPlayer p2) {
        p1.setTokenType(TokenType.RED);
        p2.setTokenType(TokenType.YELLOW);
        player = new SubPlayer(p1, null);
        SubPlayer nextPlayer = new SubPlayer(p2, player);
        player.next = nextPlayer;
        nextPlayer.next = player;
    }
    public void nextPlayer() { player = player.next; }
    public TokenType getTokenType() { return player.color; }
    
    public static class SubPlayer {
        TokenType color;
        IPlayer player;
        SubPlayer next;
        public SubPlayer(IPlayer player, SubPlayer next) {this.player = player; this.next = next; }
        public IPlayer getPlayer() { return player; }
    }
}
