package implementation.graphics;

import implementation.core.GameState;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    
    JFrame frame;
    
    GamePanel gamePanel;
    ControlPanel controlPanel;
    
    GameState gameState;
    
    public MainPanel(GameState gameState) {
        this.gameState = gameState;
        
        this.setLayout(new BorderLayout());
        
        gamePanel = new GamePanel(this, gameState);
        controlPanel = new ControlPanel(this, gameState);
        
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.EAST);
        
        frame = new JFrame("4 in a row");
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.repaint(1000/144);
    }
    
    public GameState getGameState() { return this.gameState; }
    public GamePanel getGamePanel() { return this.gamePanel; }
}
