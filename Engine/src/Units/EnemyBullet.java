package Units;

import java.awt.*;

import Engine.Environment;
import Engine.Vector;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

public class EnemyBullet extends Bullet {

	public EnemyBullet(Vector position, Vector direction, Dimension size, Image image, Environment env,
					   IUnitMovement<Unit> move, IUnitRenderer<Unit> render, IUnitBehaviour<Bullet> behave) {
		super(position, direction, size, image, env, move, render, behave);
	}



}
