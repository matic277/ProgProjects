package Units;

import java.awt.*;

import Engine.Environment;
import Engine.Vector;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

public class EnemyBullet extends Bullet {

	public EnemyBullet(Vector position, Vector direction, Dimension size, Image image, Environment env,
					   IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave) {
		super(position, direction, size, image, env, move, render, behave);
		// TODO Auto-generated constructor stub
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
