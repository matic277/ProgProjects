package Powerups;

import java.awt.Graphics2D;
import java.awt.Image;

import Units.Unit;

public abstract class Powerup {
	
	Image image;
	Unit user;
	
	public Powerup(Unit user, Image image) {
		this.user = user;
		this.image = image;
	}
	
	public abstract void applyPowerup();
	
	public abstract void draw(Graphics2D g);

}
