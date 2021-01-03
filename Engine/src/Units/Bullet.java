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

	public Bullet(Vector position, Vector direction, Dimension size, Image image, Environment env,
				IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave) {
		super(position, size, image, env, move, render, behave);
		super.movingDirection = direction;
		super.facingDirection = direction;

		// reposition slightly
		position.x = position.x - (super.hitbox.width / 2);
		position.y = position.y - (super.hitbox.height / 2);
	}
}
