package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Vector;

public class Bullet extends Unit {
	
	Vector direction;

	public Bullet(Vector position, Vector direction, Dimension size, Image image) {
		super(position, size, image);
		this.direction = direction;
	}

	@Override
	public void move() {
		updatePosition(direction);
	}

	@Override
	public void draw(Graphics2D g) {
		rotateAndDraw(g, direction);
		//drawHitbox(g);
	}
}
