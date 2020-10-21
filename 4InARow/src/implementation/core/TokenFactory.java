package implementation.core;

import implementation.graphics.Token;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

public class TokenFactory {
    
    Color redTokenColor1 = new Color(250, 12, 12);
    Color redTokenColor2 = new Color(231, 13, 13);
    
    Color yellowTokenColor1 = new Color(255, 199, 0);
    Color yellowTokenColor2 = new Color(253, 217, 0);
    
    Color transparentColor = new Color(0, 0, 0, 0);
    
    Dimension tokenSize = new Dimension(50, 50);
    HashMap<TokenColor, Pair<Color, Color>> colorMap;
    
    int tokenRad = 50;
    
    private static TokenFactory factory;
    
    private TokenFactory() {
        colorMap = new HashMap<>();
        colorMap.put(TokenColor.RED,    new Pair<>(redTokenColor1, redTokenColor2));
        colorMap.put(TokenColor.YELLOW, new Pair<>(yellowTokenColor1, yellowTokenColor2));
        colorMap.put(TokenColor.NONE, new Pair<>(Color.white, Color.white));
    }
    
    // TODO might not have to be singleton
    // useful for now that it can be called anywhere
    public static TokenFactory getTokenFactory() {
        if (factory == null) factory = new TokenFactory();
        return factory;
    }
    
    // TODO
    public Token getToken(Point2D pos, Point2D drawingPos, TokenColor type, int tokenRad) {
        Rectangle rect = new Rectangle();
        rect.setSize(tokenSize);
        Token t = new Token(type, pos, drawingPos, tokenRad, colorMap.get(type));
        
        return t;
    }
    
    public int getTokenRad() { return tokenRad; }
}
