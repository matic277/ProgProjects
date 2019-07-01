package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Vector;

public class Asteroid extends Unit {
	
	double rotationRate = 2.5;
	Vector facingDirection;
	Vector movingDirection;
	double speed = 2;

	public Asteroid(Vector position, Dimension size, Image image) {
		super(position, size, image);
		facingDirection = new Vector(1, 1);
		movingDirection = new Vector(speed, 0);
	}
	public Asteroid(Vector position, Vector movingDirection, Dimension size, Image image) {
		super(position, size, image);
		facingDirection = new Vector(1, 1);
		this.movingDirection = movingDirection;
	}

	@Override
	public void move() {
		updatePosition(movingDirection);
		facingDirection.rotate(rotationRate);
	}

	@Override
	public void draw(Graphics2D g) {
		rotateAndDraw(g, facingDirection);
	}
	
	public void reposition() {
		if (position.x > Engine.Engine.bounds.width) {
			position.x = 0 - hitbox.getWidth() + 1;
		} else if (position.x < 0) {
			position.x = Engine.Engine.bounds.width;
		} else if (position.y > Engine.Engine.bounds.height) {
			position.y = 0 - hitbox.getHeight() + 1;
		} else if (position.y < 0) {
			position.y = Engine.Engine.bounds.height;
		}
	}
}
