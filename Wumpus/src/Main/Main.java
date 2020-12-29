package Main;

import Agent.Algorithm;
import Game.Grid;
import Graphics.Painter;
import Listeners.MousepadListener;
import Graphics.TopMenu;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Main {
    
    public static void main(String[] args) {
        WorldReader reader = new WorldReader();
        reader.readWorld("./world1.txt");
        Grid g = new Grid(reader);
        
        Painter p = new Painter(new Dimension(reader.n,reader.m), new MousepadListener(), g);
//        new TopMenu(p).addButtons();
        
        Algorithm a = new Algorithm(g.getTileGrid(), g.getTrueTileGrid(), reader.player);
        new Thread(a).start();
    }
}
