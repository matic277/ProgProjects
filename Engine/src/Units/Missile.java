package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Environment;
import Engine.Vector;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

// generics prolly not needed
public class Missile<T extends Unit> extends Unit {
		
	public T target;

	public Missile(Vector position, Dimension hitbox, Image image, T target, Environment env,
				   IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave) {
		super(position, hitbox, image, env, move, render, behave);;
		super.speed = 5;
		this.target = target;
	}

	public T getTarget() { return target; }

//	@Override
//	public void move() {
//		movingDirection.x = target.getLocation().x - position.x;
//		movingDirection.y = target.getLocation().y - position.y;
//		movingDirection.norm();
//		movingDirection.multi(speed);
//
//		facingDirection = movingDirection;
//		updatePosition(movingDirection);
//	}
	
//	@Override
//	public void draw(Graphics2D g) {
//		rotateAndDraw(g);
//	}

 }

