package implementation.core;

import enums.TokenType;
import interfaces.IDrawable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.atomic.AtomicReference;

public class Token implements IDrawable {
    
    // drawn as circle
    AtomicReference<Point> drawingPos;
    AtomicReference<Point> pos;
    Pair<Integer, Integer> gridIndex;
    public static int rad = Main.INITIAL_RADIUS;
    
    TokenType type;
    Pair<Color, Color> colors;
    Color transparentColor = new Color(0, 0, 0, 0);
    
    static int fallingSpeed = 15;
    
    public Token() {
        this.drawingPos = new AtomicReference<>(new Point(0, 0));
        this.pos = new AtomicReference<>(new Point(0, 0));
        this.gridIndex = new Pair<>(0, 0);
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(colors.getB());
        
        g.fillOval((int)drawingPos.get().getX(), (int)drawingPos.get().getY(), rad*2, rad*2);
        
        g.setColor(colors.getB().darker());
        g.setStroke(new BasicStroke(8));
        g.drawOval((int)drawingPos.get().getX()+3, (int)drawingPos.get().getY()+3, rad*2-6, rad*2-6);
    }
    
    public void drawWhiteSpaces(Graphics2D g) {
        g.setColor(Color.white);
//        fillOval(g, (int)pos.getX(), (int)pos.getY(), rad);
    }
    
    // TODO
    public boolean drop() {
        boolean[] stopDropping = { false };
        drawingPos.getAndUpdate(p -> { p.setLocation(p.getX(), p.getY() + fallingSpeed); return p; });
        drawingPos.getAndUpdate(p -> {
            if (p.getY() >= pos.get().getY()) {
                stopDropping[0] = true;
                return new Point((int) pos.get().getX(), (int) pos.get().getY());
            }
            return p;
        });
        return stopDropping[0];
    }
    
    @Override
    public String toString() {
        return "Token=[pos:"+pos.get()+", draw:"+drawingPos.get()+", type:"+type+", clr:"+colors+"]";
    }
    
    public void setDrawingPosition(Point2D pos) { this.drawingPos.getAndUpdate(p -> { p.setLocation(pos); return p; }); }
    public AtomicReference<Point> getDrawingPosition() { return drawingPos; }
    
    public TokenType getType() { return type; }
    public void setType(TokenType type) { this.type = type; }
    
    public void setDrawingColors(Pair<Color, Color> colors) { this.colors = colors; }
    
    public void setGridPosition(int inRow, int inColumn) {
        gridIndex = new Pair<>(inRow, inColumn);
    }
    
    public void setPos(Point pos) {
        this.pos.set(pos);
    }
    
    public Pair<Integer, Integer> getGridPosition() {
        return gridIndex;
    }
    
    //public void drawIndicator(Graphics2D g) {
    //    fillOval(g, (int)pos.get().getX(), (int)pos.get().getY(), rad-25);
    //}
    
    //public int getRad() { return rad; }
}
