package Units;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

import Engine.Environment;
import Engine.Vector;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

public class Wall extends Unit {
	
	//Color c = Color.black;

	public Wall(Vector position, Dimension size, Image image, Environment env,
				IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave) {
		super(position, size, image, env, move, render, behave);
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public void move() { }
//
//	@Override
//	public void draw(Graphics2D g) {
//		g.setColor(c);
//		g.fill(hitbox);
//	}

}
