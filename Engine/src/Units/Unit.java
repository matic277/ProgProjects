package Units;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Environment;
import Engine.Vector;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;
import implementation.*;

public abstract class Unit {

	public final Object lock = new Object();
	
	public Image image;
	
	public Rectangle hitbox;
	public Vector position;
	public Vector centerposition;
	
	Color c = Color.black;
	
	double hp = 100;
	Rectangle hpContainer;
	Rectangle hpAmmount;
	
	public double imageAngleRad = 0;
	public double speed;
	public boolean canSeePlayer = false;
	
	public Vector facingDirection;
	public Vector movingDirection;

	public Environment env;
	
	public IUnitMovement move;
	public IUnitRenderer render;
	public IUnitBehaviour behave;

	public Unit(Vector position, Dimension size, Image image, Environment env,
				IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave) {
		if (size == null) {
			if (image == null) size = new Dimension(50, 50);
			else size = new Dimension(image.getWidth(null), image.getHeight(null));
		}
		this.hitbox = new Rectangle((int)position.x, (int)position.y, size.width, size.height);
		this.image = image;
		this.env = env;
		this.position = position;
		this.centerposition = new Vector(hitbox.getCenterX(), hitbox.getCenterY());	
		
		this.move = move;
		this.render = render;
		this.behave = behave;

		hpContainer = new Rectangle(hitbox.x, hitbox.y+hitbox.height + 10, 50, 10);
		hpAmmount = new Rectangle(hitbox.x, hitbox.y+hitbox.height + 10, 50, 10);
		
		facingDirection = new Vector(0, 0);
		movingDirection = new Vector(0, 0);
	}

	public boolean isOutOfBounds() {
		return !Engine.Engine.bounds.intersects(hitbox);
	}
	
	public void updatePosition(Vector direction) {
		position.add(direction);
		hitbox.setLocation((int)position.x, (int)position.y);
		centerposition.x = hitbox.getCenterX();
		centerposition.y = hitbox.getCenterY();
		hpContainer.setLocation(hitbox.x, hitbox.y+hitbox.height + 10);
		hpAmmount.setLocation(hitbox.x, hitbox.y+hitbox.height + 10);
		hpAmmount.setSize((int)(hp * 1/2), 10);
	}
	
	public void move() {
		move.move(this, env);
	}
	
	public void behave() {
		if (behave == null) System.out.println("NULL");
		behave.behave(this);
	}
	
	public void draw(Graphics2D g) {
		render.draw(g, this);
//		g.setColor(Color.black);
//		g.drawImage(image, (int)position.x, (int)position.y, null);
//		drawHitbox(g);
	}
	
	public boolean checkCollision(Unit unit) {
		return this.hitbox.intersects(unit.hitbox);
	}
	
	public void defaultDraw(Graphics2D g) {
		g.drawImage(image, (int)position.x, (int)position.y, null);
	}
	
	public void drawHitbox(Graphics2D g) {
		g.setColor(c);
		g.draw(hitbox);
	}
	
	public void drawHP(Graphics2D g) {
		g.setColor(Color.green);
		if (hp < 50) g.setColor(Color.orange);
		if (hp < 30) g.setColor(Color.red);
		g.fill(hpAmmount);
		g.setColor(Color.gray);
		g.draw(hpContainer);
	}
	
//	public void rotateAndDraw(Graphics2D g) {
//	    imageAngleRad = Math.atan2(facingDirection.y, facingDirection.x);
//
//	    int cx = image.getWidth(null) / 2;
//	    int cy = image.getHeight(null) / 2;
//
//	    AffineTransform oldAT = g.getTransform();
//
//	    g.translate(cx+hitbox.x, cy+hitbox.y);
//	    g.rotate(imageAngleRad);
//	    g.translate(-cx, -cy);
//	    g.drawImage(image, 0, 0, null);
//	    g.setTransform(oldAT);
//
//	    // courtesy of stack overflow
//	}
	
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
	
	public boolean lowerHealth(double ammount) {
		hp -= ammount;
		return hp <= 0;
	}
	
	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setLocation(Vector v) {
		position = v;
	}
	
	public Vector getLocation() {
		return position;
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public void setUnitImage(Image img) {
		image = img;
	}
	
	public Image getUnitImage() {
		return image;
	}
	
	public Vector getMovingDirection() {
		return movingDirection;
	}
	
	public Vector getFacingDirection() {
		return facingDirection;
	}
	
	public void setFacingDirection(Vector v) {
		this.facingDirection.set(v);
	}
	
	public void setMovingDirection(Vector direction) {
		this.movingDirection.set(direction);
	}
	
	public String toString() {
		return ("["+this.getClass().getName()+", "+this.position.toString()+"]");
	}

}
