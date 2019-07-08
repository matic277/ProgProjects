package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Vector;
	
public class Missile extends Unit {
		
	public Unit target;
	public Missile(Vector position, Dimension hitbox, Image image, Unit unit) {
		super(position, hitbox, image);;
		super.speed = 5;
		this.target = unit;
	}

	@Override
	public void move() {
		movingDirection.x = target.getLocation().x - position.x;
		movingDirection.y = target.getLocation().y - position.y;
		movingDirection.norm();
		movingDirection.multi(speed);
		
		facingDirection = movingDirection;
		updatePosition(movingDirection);
	}
	
	@Override
	public void draw(Graphics2D g) {
		rotateAndDraw(g, facingDirection);
	}
 }

