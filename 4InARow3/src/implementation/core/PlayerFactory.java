package implementation.core;

import implementation.algorithm.MiniMaxMovingStrategy;
import implementation.algorithm.Player;
import implementation.algorithm.PlayerType;
import interfaces.IMovingStrategy;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class PlayerFactory {
    
    public static Player getHumanPlayer(GameState gameState) {
        Player p = new Player(null, null, null);
        p.setGameState(gameState);
        p.setPlayerType(PlayerType.HUMAN);
        return p;
    }
    
    public static Player getRandomPlayer(GameState gameState) {
        Player p = new Player(null, null, null);
        IMovingStrategy movStrat = getComputerMovingStrategy(gameState, p);
        p.setGameState(gameState);
        p.setPlayerType(PlayerType.RANDOM);
        p.setMovingStrat(movStrat);
        return p;
    }
    
    public static Player getMiniMaxPlayer(GameState gameState) {
        Player p = new Player(null, null, null);
        IMovingStrategy movStrat = getMiniMaxMovingStrategy(gameState, p);
        p.setGameState(gameState);
        p.setPlayerType(PlayerType.MINIMAX);
        p.setMovingStrat(movStrat);
        return p;
    }
    
    private static final Map<PlayerType, Function<GameState, Player>> typeMap;
    static {
        typeMap = new HashMap<>();
        typeMap.put(PlayerType.HUMAN, PlayerFactory::getHumanPlayer);
        typeMap.put(PlayerType.RANDOM, PlayerFactory::getRandomPlayer);
        typeMap.put(PlayerType.MINIMAX, PlayerFactory::getMiniMaxPlayer);
    }
    public static Player getPlayerByType(PlayerType type, GameState gameState) {
        return typeMap.get(type).apply(gameState);
    }
    
    public static IMovingStrategy getComputerMovingStrategy(GameState gameState, Player p) {
        final Random r = new Random();
        RandomMovingStrategy strategy = new RandomMovingStrategy();
        strategy.gameState = gameState;
        strategy.player = p;
        return strategy;
    }
    
    public static IMovingStrategy getMiniMaxMovingStrategy(GameState gameState, Player p) {
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
