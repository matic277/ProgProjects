package implementation.core;

import implementation.graphics.Painter;
import implementation.listeners.InputHandler;
import implementation.listeners.KeyboardListener;
import implementation.listeners.MousepadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class Main {
    
    
    public static void main(String[] args) {
        // init
        GameState.getGameState();
        Thread.currentThread().setName(Main.class.getSimpleName() + "[" + Thread.currentThread().getId() + "]");

        MousepadListener ml = new MousepadListener();
        KeyboardListener kl = new KeyboardListener();

        Dimension panelSize = new Dimension(1200, 1000);
        Painter p = new Painter(panelSize, ml, kl);

//        ml.addObserver(new InputHandler(p));
        ml.addObserver(new PlayerHandler(p));
    }
}
