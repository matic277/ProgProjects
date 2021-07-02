package implementation.core;

import enums.TokenType;
import implementation.algorithm.MiniMaxMovingStrategy;
import implementation.algorithm.Player;
import implementation.algorithm.PlayerType;
import implementation.graphics.PanelPainter;
import implementation.listeners.InputHandler;
import interfaces.IMouseObserver;
import interfaces.IMovingStrategy;
import interfaces.IPlayer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

public class PlayerFactory {
    
    public static Player getHumanPlayer(GameState gameState, InputHandler inputHandler) {
        Player p = new Player(null, null, null);
        IMovingStrategy movStrat = getHumanMovingStrategy(inputHandler, p);
        p.setGameState(gameState);
        p.setPlayerType(PlayerType.HUMAN);
        p.setMovingStrat(movStrat);
        return p;
    }
    
    public static Player getRandomPlayer(GameState gameState) {
        Player p = new Player(null, null, null);
        IMovingStrategy movStrat = getComputerMovingStrategy(gameState, p);
        p.setGameState(gameState);
        p.setPlayerType(PlayerType.COMPUTER);
        p.setMovingStrat(movStrat);
        return p;
    }
    
    public static Player getMiniMaxPlayer(GameState gameState) {
        Player p = new Player(null, null, null);
        IMovingStrategy movStrat = getMiniMaxMovingStrategy(gameState, p);
        p.setGameState(gameState);
        p.setPlayerType(PlayerType.COMPUTER);
        p.setMovingStrat(movStrat);
        return p;
    }
    
    private static IMovingStrategy getHumanMovingStrategy(InputHandler inputHandler, Player p) {
        System.out.println("   ~ " + Thread.currentThread().getName() + " (" + p.getPlayerType() + ") is trying to make a move.");
        return inputHandler::getActiveColumnIndex;
    }
    
    private static IMovingStrategy getComputerMovingStrategy(GameState gameState, Player p) {
        final Random r = new Random();
        RandomMovingStrategy strategy = new RandomMovingStrategy();
        strategy.gameState = gameState;
        strategy.player = p;
        return strategy;
    }
    
    private static IMovingStrategy getMiniMaxMovingStrategy(GameState gameState, Player p) {
        MiniMaxMovingStrategy strategy = new MiniMaxMovingStrategy(gameState, p);
        strategy.gameState = gameState;
        strategy.player = p;
        return strategy;
    }
    
    static class RandomMovingStrategy implements IMovingStrategy {
        public GameState gameState;
        public Player player;
        Random r = new Random();
        
        @Override
        public int makeMove() {
            System.out.println("   ~ " + Thread.currentThread().getName() + " (" + player.getPlayerType() + ") is trying to make a move.");
            try { Thread.sleep(1000);}
            catch (Exception e) { e.printStackTrace(); }
            int[] freeCols = gameState.getFreeColumns();
            int move = freeCols[r.nextInt(freeCols.length)];
            System.out.println("   ~ " + Thread.currentThread().getName() + " move made = " + move);
            return move;
        }
    }
}
