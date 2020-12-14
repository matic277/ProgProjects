package implementation.core;

import implementation.graphics.GamePainter;
import implementation.graphics.GameStatPainter;
import implementation.graphics.PanelPainter;
import implementation.listeners.InputHandler;
import implementation.listeners.MousepadListener;
import interfaces.IMyAtomicReference;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import implementation.algorithm.*;
import interfaces.IPlayer;

public class Main {
    
    public static void main(String[] args) {
        MousepadListener ml = new MousepadListener();
        GameState gameState = new GameState();
        
        // painters
        PanelPainter mainPanel = new PanelPainter(new Dimension(1100, 800), ml);
        GamePainter gamePainter = new GamePainter(new Rectangle(100, 100, 700, 600));
        GameStatPainter statPainter = new GameStatPainter(new Rectangle(900, 100, 200, 100), gameState);
        mainPanel.addDrawableComponent(gamePainter);
        mainPanel.addDrawableComponent(statPainter);
        
        // input handler
        InputHandler inputHandler = new InputHandler();
        inputHandler.init(gameState, gamePainter);
        
        // players
        Player p1 =
//                PlayerFactory.getMiniMaxPlayer(gameState);
                PlayerFactory.getHumanPlayer(gameState, inputHandler);
        Player p2 =
//                PlayerFactory.getHumanPlayer(gameState, inputHandler);
                PlayerFactory.getMiniMaxPlayer(gameState);
        
        gamePainter.setGameState(gameState);
        statPainter.initPlayers(p1, p2);
        gameState.setPlayers(p1, p2);
        ml.addMouseObserver(inputHandler);
    
    
        new Thread(p1).start();
        new Thread(p2).start();
        PanelPainter.sleep(10); // badly avoiding race condition
        new Thread(inputHandler).start();
    }

}
