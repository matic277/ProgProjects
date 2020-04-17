package implementation;

import java.awt.*;
import java.awt.geom.AffineTransform;

import Units.Unit;
import core.IUnitMovement;
import core.IUnitRenderer;

public class RotationRenderer implements IUnitRenderer {

	public void draw(Graphics2D g, Unit u) {
		u.imageAngleRad = Math.atan2(u.facingDirection.y, u.facingDirection.x);

	    int cx = u.image.getWidth(null) / 2;
	    int cy = u.image.getHeight(null) / 2;
	    
	    AffineTransform oldAT = g.getTransform();
	    
	    g.translate(cx+u.hitbox.x, cy+u.hitbox.y);
	    g.rotate(u.imageAngleRad);
	    g.translate(-cx, -cy);
	    g.drawImage(u.image, 0, 0, null);;
	    g.draw(u.hitbox);
	    g.setTransform(oldAT);

	    // courtesy of stack overflow
	}

}
