package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;

import Engine.Environment;
import Engine.Vector;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

public class Bullet extends Unit {
//	public Unit(Vector position, Dimension size, Image image, IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave) {

//	public Bullet(Vector position, Vector direction, Dimension size, Image image) {
//		super(position, size, image);
//		super.movingDirection = direction;
//		super.facingDirection = direction;
//	}
	public Bullet(Vector position, Vector direction, Dimension size, Image image, Environment env,
				IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave) {
		super(position, size, image, env, move, render, behave);
		super.movingDirection = direction;
		super.facingDirection = direction;
	}

//	@Override
//	public void move() {
//		updatePosition(movingDirection);
//	}
//
//	@Override
//	public void draw(Graphics2D g) {
//		rotateAndDraw(g);
////		drawHitbox(g);
//	}
}
