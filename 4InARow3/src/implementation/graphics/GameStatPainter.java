package implementation.graphics;

import implementation.algorithm.Player;
import implementation.algorithm.PlayerType;
import implementation.core.GameState;
import interfaces.IDrawable;

import java.awt.*;

public class GameStatPainter implements IDrawable {
    
    Color fontColor = Color.black;
    Color bgColor = Color.white;
    
    Rectangle bounds;
    GameState gameState;
    
    Player p1, p2;
    Color p1Color, p2Color;
    
    String spacing;
    
    public GameStatPainter(Rectangle bounds, GameState gameState) {
        this.bounds = bounds;
        this.gameState = gameState;
    }
    
    public void initPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        initOutputString();
    }
    
    private void initOutputString() {
        StringBuilder sb = new StringBuilder();
        int numOfSpaces = p1.getPlayerType().toString().length() + 2;
        sb.append(" ".repeat(Math.max(0, numOfSpaces)));
        spacing = sb.toString();
        System.out.println("spacing: " + spacing.length());
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(bgColor);
        g.fill(bounds);
        
        //Font oldFont = g.getFont();
        g.setFont(new Font("Consolas", Font.BOLD, 16));
        
        g.setColor(fontColor);
        g.drawString("Game Over: " + gameState.isGameOver(), bounds.x, bounds.y);
        g.drawString("STATS:", bounds.x, bounds.y+15);
        g.drawString(p1.getPlayerType().toString() + "   " + p2.getPlayerType().toString(), bounds.x, bounds.y+30);
        g.drawString(p1.getNumberOfMovesMade() + spacing + p2.getNumberOfMovesMade(), bounds.x, bounds.y+45);
//        g.drawString("EVAL: " + gameState., , );
    }
}
