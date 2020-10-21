package implementation.graphics;

import implementation.core.Pair;
import implementation.core.TokenColor;
import interfaces.IDrawable;

import java.awt.*;
import java.awt.geom.Point2D;

public class Token implements IDrawable {
    
    // drawn as circle
    Point2D drawingPos;
    Point2D pos;
    public static int rad; // ugly
    
    TokenColor type;
    Pair<Color, Color> colors;
    Color transparentColor = new Color(0, 0, 0, 0);
    
    static int fallingSpeed = 20;
    
    public Token(TokenColor type, Point2D pos, Point2D drawingPos, int rad, Pair<Color, Color> colors) {
        this.type = type;
        this.pos = pos;
        this.drawingPos = drawingPos;
        Token.rad = rad;
        this.colors = colors;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(colors.getB());
        fillOval(g, (int)drawingPos.getX(), (int)drawingPos.getY(), rad);
        g.setColor(colors.getA());
        fillOval(g, (int)drawingPos.getX(), (int)drawingPos.getY(), rad-10);
        g.setColor(colors.getB());
        fillOval(g, (int)drawingPos.getX(), (int)drawingPos.getY(), rad-15);
        g.setColor(colors.getA());
        fillOval(g, (int)drawingPos.getX(), (int)drawingPos.getY(), rad-25);
    
//        g.setColor(Color.black);
//        drawOval(g, (int)drawingPos.getX(), (int)pos.getY(), rad);
        
//        g.setColor(Color.red);
//        g.fillRect((int)drawingPos.getX()-3, (int)pos.getY()-3, 6, 6);
    }
    
    public void drawOval(Graphics2D g, int x, int y, int rad) {
        x = x - (rad / 2);
        y = y - (rad / 2);
        g.drawOval(x, y, rad, rad);
    }
    
    public void fillOval(Graphics2D g, int x, int y, int rad) {
        x = x - (rad / 2);
        y = y - (rad / 2);
        g.fillOval(x, y, rad, rad);
    }
    
    public void drawTransparent(Graphics2D g) {
        g.setColor(transparentColor);
        fillOval(g, (int)pos.getX(), (int)pos.getY(), rad);
    }
    
    // TODO
    public boolean drop() {
        if (drawingPos.getY() >= pos.getY()) {
            drawingPos = new Point((int)pos.getX(), (int)pos.getY());
            return false;
        }
        // drop it till positions are the same
        drawingPos.setLocation(drawingPos.getX(), drawingPos.getY()+fallingSpeed);
        return true;
    }
    
    public Point2D getPosition() { return pos; }
    public TokenColor getType() { return type; }
    //public int getRad() { return rad; }
}
