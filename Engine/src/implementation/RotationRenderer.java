package implementation;

import java.awt.*;
import java.awt.geom.AffineTransform;

import Units.Unit;
import core.IUnitRenderer;

public class RotationRenderer <T extends Unit> implements IUnitRenderer<T> {

	@Override
	public void draw(Graphics2D g, T unit) {
		unit.imageAngleRad = Math.atan2(unit.facingDirection.y, unit.facingDirection.x);

	    int cx = unit.image.getWidth(null) / 2;
	    int cy = unit.image.getHeight(null) / 2;
	    
	    AffineTransform oldAT = g.getTransform();
	    
	    g.translate(cx+unit.hitbox.x, cy+unit.hitbox.y);
	    g.rotate(unit.imageAngleRad);
	    g.translate(-cx, -cy);
	    // image
	    g.drawImage(unit.image, 0, 0, null);;
	    // hitbox
		g.setColor(Color.black);
	    g.drawRect(0, 0, unit.hitbox.width, unit.hitbox.height);
	    g.setTransform(oldAT);

	    // courtesy of stack overflow
	}
}
