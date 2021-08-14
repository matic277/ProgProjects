package implementation.core;

import implementation.graphics.MainPanel;
import implementation.listeners.InputHandler;

import java.awt.*;


public class Main {
    
    public static void main(String[] args) {
        GameState gameState = new GameState();
    
        MainPanel panel = new MainPanel(new Dimension(800, 600), gameState);
        InputHandler inputHandler = panel.getGamePanel().getInputHandler();
        gameState.setInputHandler(inputHandler);
        gameState.getCurrentPlayers().redPlayer.player.getPlayer().setMovingStrat(inputHandler);
        gameState.getCurrentPlayers().yellowPlayer.player.getPlayer().setMovingStrat(inputHandler);
    }

}
