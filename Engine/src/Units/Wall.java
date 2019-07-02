package Units;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Vector;

public class Wall extends Unit {
	
	//Color c = Color.black;

	public Wall(Vector position, Dimension size, Image image) {
		super(position, size, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() { }

	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		g.fill(hitbox);
	}

}
