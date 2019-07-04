package Units;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;

public abstract class Unit {
	
	Image image;
	
	Rectangle hitbox;
	Vector position;
	public Vector centerposition;
	
	Color c = Color.black;
	
	double hp = 100;
	protected Rectangle hpContainer;
	protected Rectangle hpAmmount;
	
	double imageAngleRad = 0;
	
	public Unit(Vector position, Dimension size, Image image) {
		if (size == null) {
			if (image == null) size = new Dimension(50, 50);
			else size = new Dimension(image.getWidth(null), image.getHeight(null));
		}
		this.hitbox = new Rectangle((int)position.x, (int)position.y, size.width, size.height);
		this.image = image;
		this.position = position;
		this.centerposition = new Vector(hitbox.getCenterX(), hitbox.getCenterY());		

		hpContainer = new Rectangle(hitbox.x, hitbox.y+hitbox.height + 10, 50, 10);
		hpAmmount = new Rectangle(hitbox.x, hitbox.y+hitbox.height + 10, 50, 10);
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
	
	public abstract void move();
	
	public abstract void draw(Graphics2D g);
	
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
	
	public void rotateAndDraw(Graphics2D g, Vector direction) {
	    imageAngleRad = Math.atan2(direction.y, direction.x);

	    int cx = image.getWidth(null) / 2;
	    int cy = image.getHeight(null) / 2;
	    
	    AffineTransform oldAT = g.getTransform();
	    
	    g.translate(cx+hitbox.x, cy+hitbox.y);
	    g.rotate(imageAngleRad);
	    g.translate(-cx, -cy);
	    g.drawImage(image, 0, 0, null);
	    g.setTransform(oldAT);
	    
	    // courtesy of stack overflow
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
	
	public String toString() {
		return ("["+this.getClass().getName()+", "+this.position.toString()+"]");
	}

}
