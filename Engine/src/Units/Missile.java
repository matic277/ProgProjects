package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Vector;
	
public class Missile extends Unit {
		
	public Unit target;
	Vector direction;
	double speed = 5;

	public Missile(Vector position, Dimension hitbox, Image image, Unit unit) {
		super(position, hitbox, image);;
		this.target = unit;
		
		direction = new Vector(0, 0);
	}

	@Override
	public void move() {
		direction.x = target.getLocation().x - position.x;
		direction.y = target.getLocation().y - position.y;
		direction.norm();
		direction.multi(speed);

		updatePosition(direction);
	}
	
	@Override
	public void draw(Graphics2D g) {
		rotateAndDraw(g, direction);
	}
 }

