package implementation.core;

import enums.TokenType;
import implementation.algorithm.Player;

// recursive structure to get rid of if statements
// ... -> player1 -> player2 -> player1 -> ...
// probably not worth the complicated recursive code
public class CurrentPlayers {
    
    SubPlayer redPlayer;
    SubPlayer yellowPlayer;
    
    public SubPlayer currentPlayer;
    
    public CurrentPlayers(Player redPlayer, Player yellowPlayer) {
        this.redPlayer = new SubPlayer(redPlayer, null);
        this.yellowPlayer = new SubPlayer(yellowPlayer, this.redPlayer);
        this.redPlayer.next = this.yellowPlayer;
        
        this.currentPlayer = this.redPlayer;
        
        redPlayer.setTokenType(TokenType.RED);
        yellowPlayer.setTokenType(TokenType.YELLOW);
    }
    
    public void setNewRedPlayer(Player newRedPlayer) {
        this.redPlayer = new SubPlayer(newRedPlayer, yellowPlayer);
        this.yellowPlayer.next = this.redPlayer;
    
        System.out.println("New red player set: " + this.redPlayer);
        
        assert this.redPlayer.next == this.yellowPlayer &&
                this.redPlayer.next.next == this.redPlayer;
        
        if (currentPlayer.player.getPlayer().getTokenType() == TokenType.RED) {
            this.currentPlayer = this.redPlayer;
        }
    }
    
    public void setNewYellowPlayer(Player newYellowPlayer) {
        this.yellowPlayer = new SubPlayer(newYellowPlayer, redPlayer);
        this.redPlayer.next = this.yellowPlayer;
    
        System.out.println("New yellow player set: " + this.yellowPlayer);
    
        assert this.yellowPlayer.next == this.redPlayer &&
                this.yellowPlayer.next.next == this.yellowPlayer;
    
        if (currentPlayer.player.getPlayer().getTokenType() == TokenType.YELLOW) {
            this.currentPlayer = this.yellowPlayer;
        }
    }
    
    public void nextPlayer() { currentPlayer = currentPlayer.next; }
    public TokenType getTokenType() { return currentPlayer.player.getTokenType(); }
    public Pair<Player, Player> getBothPlayers() { return new Pair<>(redPlayer.player, yellowPlayer.player); }
    
    public static class SubPlayer {
        public Player player;
        SubPlayer next;
        public SubPlayer(Player player, SubPlayer next) { this.player = player; this.next = next; }
    }
}
