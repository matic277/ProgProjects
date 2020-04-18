package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Environment;
import Engine.Vector;
import Graphics.ResourceLoader;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

public class Enemy extends Unit {

	public Enemy(Vector position, Dimension hitbox, Image image, Environment env,
			IUnitMovement<Enemy> move, IUnitRenderer<Unit>  render, IUnitBehaviour<Enemy>  behave) {
		super(position, hitbox, image, env, move, render, behave);
		super.speed = 2;
		this.movingDirection = (movingDirection == null)? new Vector(speed, 0) : movingDirection;
		this.facingDirection = new Vector(0, 0);
	}

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

}
