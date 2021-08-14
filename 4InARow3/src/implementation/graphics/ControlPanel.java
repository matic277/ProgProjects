package implementation.graphics;

import enums.TokenType;
import implementation.algorithm.MiniMaxMovingStrategy;
import implementation.algorithm.PlayerType;
import implementation.core.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class ControlPanel extends JPanel{
    
    MainPanel mainPanel;
    GameState gameState;
    
    JSlider depthSlider;
    
    JComboBox<PlayerType> redPlayerDropdown;
    JComboBox<PlayerType> yellowPlayerDropdown;

    public ControlPanel(MainPanel parent, GameState gameState) {
        this.mainPanel = parent;
        this.gameState = gameState;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        initDepthSlider();
        initPlayerPickers();
    }
    
    private void initDepthSlider() {
        JLabel sliderInfo2 = new JLabel("Change minimax depth: ");
        sliderInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderInfo2.setSize(new Dimension(30, 120));
        sliderInfo2.setPreferredSize(new Dimension(30, 120));
        
        int initialMinMaxDepth = 3;
        int minDepth = 1, maxDepth = 10;
        
        depthSlider = new JSlider(minDepth, maxDepth, initialMinMaxDepth);
        depthSlider.setSnapToTicks(true);
        depthSlider.setValue(initialMinMaxDepth);
        depthSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        Hashtable<Integer, JLabel> sliderMap2 = new Hashtable<>();
        JLabel minLbl2 = new JLabel(minDepth+"");
        JLabel maxLbl2 = new JLabel(maxDepth+"");
        JLabel maxLblMiddle = new JLabel((((maxDepth + minDepth)-1)/2)+"");
        sliderMap2.put(minDepth, minLbl2);
        sliderMap2.put(maxDepth, maxLbl2);
        sliderMap2.put((((maxDepth + minDepth)-1)/2), maxLblMiddle);
        depthSlider.setLabelTable(sliderMap2);
        depthSlider.setMajorTickSpacing(5);
        depthSlider.setMinorTickSpacing(1);
        depthSlider.setPaintTicks(true);
        depthSlider.setPaintLabels(true);
        depthSlider.setPreferredSize(new Dimension(150, 50));
        depthSlider.setMaximumSize(new Dimension(150, 50));
        depthSlider.setMinimumSize(new Dimension(150, 50));
        //depth.setEnabled(false);
        depthSlider.addChangeListener(c -> setNewDepth());
    
        this.add(new JLabel(" ")); // spacing
        this.add(sliderInfo2);
        this.add(new JLabel(" "));
        this.add(depthSlider);
        this.add(new JLabel(" "));
    }
    
    private void setNewDepth() {
        var players = gameState.getCurrentPlayers().getBothPlayers();
        if (players.getA().getMovingStrat() instanceof MiniMaxMovingStrategy miniMax)
            miniMax.setDepth(depthSlider.getValue());
        if (players.getB().getMovingStrat() instanceof MiniMaxMovingStrategy miniMax)
            miniMax.setDepth(depthSlider.getValue());
    }
    
    private void initPlayerPickers() {
        JLabel title1 = new JLabel("Pick player types:");
        
        JPanel container = new JPanel();
        container.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,Color.lightGray));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        redPlayerDropdown = new JComboBox<>(PlayerType.values());
        redPlayerDropdown.setBackground(Color.red);
        redPlayerDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        redPlayerDropdown.setMaximumSize(new Dimension(150, 30));
        redPlayerDropdown.addActionListener(a -> setPlayer((PlayerType)redPlayerDropdown.getSelectedItem(), TokenType.RED));
        
        yellowPlayerDropdown = new JComboBox<>(PlayerType.values());
        yellowPlayerDropdown.setMaximumSize(new Dimension(150, 30));
        yellowPlayerDropdown.setBackground(Color.yellow);
        yellowPlayerDropdown.addActionListener(a -> setPlayer((PlayerType)yellowPlayerDropdown.getSelectedItem(), TokenType.YELLOW));
        
        container.add(title1);
        container.add(redPlayerDropdown);
        container.add(yellowPlayerDropdown);
        
        this.add(container);
    }
    
    private void setPlayer(PlayerType playerType, TokenType tokenColor) {
        System.out.println("try to set new player");
        gameState.setNewPlayer(playerType, tokenColor);
    }
    
    
}
