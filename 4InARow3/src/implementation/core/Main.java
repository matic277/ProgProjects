package implementation.core;

import implementation.graphics.MainPanel;
import implementation.listeners.InputHandler;

import java.awt.*;


public class Main {
    
    // it is preferable that:
    // (unit - 2*space) / 2 => integer
    // So first define radius of circle and spacing!
    public static final int rad = 40;  // radius of token
    public static final int space = 5; // spacing between tokens
    public static final int borderSize = 75; // spacing around game board
    
    public static void main(String[] args) {
        GameState gameState = new GameState();
        
        MainPanel panel = new MainPanel(gameState);
        InputHandler inputHandler = panel.getGamePanel().getInputHandler();
        gameState.setInputHandler(inputHandler);
        gameState.getCurrentPlayers().redPlayer.player.getPlayer().setMovingStrat(inputHandler);
        gameState.getCurrentPlayers().yellowPlayer.player.getPlayer().setMovingStrat(inputHandler);
    }

}
