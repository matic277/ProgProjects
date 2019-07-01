package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;

public class Player extends Unit {
	
	Point mouse;
	Vector direction;
	double speed = 10;

	public Player(Vector position, Dimension hitboxSize, Image image, Point mouse) {
		super(position, hitboxSize, image);
		this.image = image;
		this.mouse = mouse;
		
		direction = new Vector(0, 0);
	}

	@Override
	public void move() {
		// handled by engine
	}
	
	public void updateDirection() {
		direction.x = mouse.x - position.x;
		direction.y = mouse.y - position.y;
	}

	@Override
	public void draw(Graphics2D g) {
		rotateAndDraw(g, direction);
		drawHP(g);
		drawHitbox(g);
	}

}
