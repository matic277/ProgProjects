package implementation.core;

import implementation.algorithm.RandomPlayer;
import implementation.graphics.Painter;
import implementation.listeners.InputHandler;
import interfaces.IPlayer;

import java.awt.event.MouseEvent;

public class PlayerHandler extends InputHandler {
    
    RandomPlayer player1, player2;
    GameState gameState;
    
    public PlayerHandler(Painter painter) {
        super(painter);
        gameState = GameState.getGameState();
        player1 = new RandomPlayer(gameState);
        player2 = new RandomPlayer(gameState);
        gameState.initPlayers(player1, player2);
        start();
    }
    
    private void start() {
        while (true) {
            IPlayer player = gameState.getCurrentPlayer();
    
            activeColumnIndex = player.makeMove(gameState.getGrid());
            notifyMousePressed(null);
            
        
//            System.out.println(Thread.currentThread().getName());
//            System.out.print("Going to sleep...");
            
            if (gameState.isGameOver()) break;
            sleep(1000);
//            System.out.println(" waking up.");
        }
    }
    
    @Override
    public void notifyMousePressed(MouseEvent event) {
        // if game is over just never wake up thread with
        // a task again? OR terminate it and then recreate it
        // if reset button is pressed?
        System.out.println("notified");
        if (activeColumnIndex == -1 || gameState.isGameOver()) return;
        
        Runnable task = getMousePressedTask(event);
        executeEvent(task);
    }
    
    public static void sleep(int t) {
        try { Thread.sleep((long)t); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
