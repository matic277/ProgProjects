package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;

import Engine.Vector;

public class Bullet extends Unit {
	
	public Bullet(Vector position, Vector direction, Dimension size, Image image) {
		super(position, size, image);
		super.movingDirection = direction;
		super.facingDirection = direction;
	}

	@Override
	public void move() {
		updatePosition(movingDirection);
	}

	@Override
	public void draw(Graphics2D g) {
		rotateAndDraw(g, facingDirection);
		//drawHitbox(g);
	}
}
