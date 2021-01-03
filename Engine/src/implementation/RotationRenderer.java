package implementation;

import java.awt.*;
import java.awt.geom.AffineTransform;

import Engine.Engine;
import Units.Unit;
import core.IUnitRenderer;

public class RotationRenderer <T extends Unit> implements IUnitRenderer<T> {

	@Override
	public void draw(Graphics2D g, T unit) {
		unit.imageAngleRad = Math.atan2(unit.facingDirection.y, unit.facingDirection.x);

	    int cx = unit.hitbox.width / 2;
	    int cy = unit.hitbox.height / 2;

	    g.setColor(Color.BLACK);
	    g.drawRect(cx-2, cy-2, 4, 4);


	    AffineTransform oldAT = g.getTransform();

	    g.drawOval(Engine.mouse.x-2, Engine.mouse.y-2, 4, 4);
	    
	    g.translate(cx+unit.hitbox.x, cy+unit.hitbox.y);
	    g.rotate(unit.imageAngleRad);
	    g.translate(-cx, -cy);

	    // image
	    g.drawImage(unit.image, 0, 0, null);

	    // hitbox
		g.setColor(Color.black);
	    g.drawRect(0, 0, unit.hitbox.width, unit.hitbox.height);

	    g.setTransform(oldAT);

	    // from stack overflow
	}
}
