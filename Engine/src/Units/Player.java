package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Environment;
import Engine.Vector;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

public class Player extends Unit {
	
	Point mouse;

	public Player(Vector position, Dimension hitboxSize, Image image, Point mouse, Environment env,
				  IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave) {
		super(position, hitboxSize, image, env, move, render, behave);
		super.speed = 10;
		this.image = image;
		this.mouse = mouse;
		
		facingDirection = new Vector(0, 0);
	}

//	@Override
//	public void move() {
//		// handled by engine
//	}
	
	public void updateDirection() {
		facingDirection.x = mouse.x - position.x;
		facingDirection.y = mouse.y - position.y;
	}

//	@Override
//	public void draw(Graphics2D g) {
//		rotateAndDraw(g);
//		drawHP(g);
//		drawHitbox(g);
//	}

}
