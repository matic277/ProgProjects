package implementation.core;

import enums.TokenType;

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
    HashMap<TokenType, Pair<Color, Color>> colorMap;
    
    int tokenRad = 50;
    
    private static TokenFactory factory = new TokenFactory();
    
    private TokenFactory() {
        colorMap = new HashMap<>();
        colorMap.put(TokenType.RED,    new Pair<>(redTokenColor1, redTokenColor2));
        colorMap.put(TokenType.YELLOW, new Pair<>(yellowTokenColor1, yellowTokenColor2));
        colorMap.put(TokenType.NONE,   new Pair<>(Color.white, Color.white));
    }
    
    // singleton
    public static TokenFactory getTokenFactory() {
        return factory;
    }
    
    // TODO
    public Token getToken(TokenType type, int inRow, int inColumn) {
//        Rectangle rect = new Rectangle();
//        rect.setSize(tokenSize);
        Token t = new Token();
        t.setDrawingColors(colorMap.get(type));
        t.setGridPosition(inRow, inColumn);
        
        return t;
    }
    
    public HashMap<TokenType, Pair<Color, Color>> getColorMap() { return colorMap; }
    
    public int getTokenRad() { return tokenRad; }
}
