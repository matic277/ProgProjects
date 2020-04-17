package factories;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Environment;
import Engine.Vector;
import Graphics.ResourceLoader;
import Units.*;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;
import implementation.*;

public class UnitFactory {
	
	ResourceLoader res;
	Environment env;
	MovementFactory movFact;
	RenderingFactory renFact;
	
	public UnitFactory(ResourceLoader res, Environment env,  MovementFactory movFact, RenderingFactory renFact) {
		this.res = res;
		this.env = env;
		this.movFact = movFact;
		this.renFact = renFact;
	}
	
	public Unit getInstanceOfPlayer() {
//		return new Unit(
//			
//		);
		return null;
	}

	// TODO: method is a copy of get enemy instance bullet
	public Bullet getInstanceOfBullet(Vector from, Vector to) {
		Vector direction = new Vector(to.x - from.x, to.y - from.y);
		Vector startingPosition = new Vector(from);
		direction.norm();
		direction.multi(15);

		SimpleLinearMovement move = movFact.getSimpleLinearMovement(direction);
		IUnitRenderer render = renFact.getSimpleUnitRenderer();
		IUnitBehaviour behave = (u) -> {}; // don't behave

		return new Bullet(
				startingPosition,
				direction,
				null,
				res.getEnemyBulletImage(),
				env,
				move,
				render,
				behave);
	}
	
	public Unit getInstanceOfGuard() {
		return null;
	}
	
	public Unit getInstanceOfEnemy() {
		IUnitRenderer renderer = renFact.getSimpleUnitRenderer();
		EnemyMovement movement = movFact.getEnemeyMovement();
		IUnitBehaviour behaviour = new ShootingBehaviour(res, env.player, env.bullets);

		Enemy e = new Enemy(
			new Vector(50, 50),
			//null,
			 null,
			res.getEnemyImage(),
			env,
			movement,
			renderer,
			behaviour);

		movement.assignUnitToThisClass(e);
		return e;
	}
	
	public EnemyBullet getInstanceOfEnemyBullet(Vector from, Vector to) {
		Vector direction = new Vector(to.x - from.x, to.y - from.y);
		Vector startingPosition = new Vector(from);
		direction.norm();
		direction.multi(5);

		SimpleLinearMovement move = movFact.getSimpleLinearMovement(direction);
		IUnitRenderer render = renFact.getSimpleUnitRenderer();
		IUnitBehaviour behave = (u) -> {}; // don't behave

		return new EnemyBullet(
				startingPosition,
				direction,
				null,
				res.getEnemyBulletImage(),
				env,
				move,
				render,
				behave);
	}
	
	public <T extends Unit> Missile<?> getInstanceOfMissile(Vector from, T target) {
		IUnitMovement move = movFact.getTrackingMovement(target);
		IUnitRenderer render = renFact.getSimpleUnitRenderer();
		IUnitBehaviour behave = (u) -> {}; // don't behave

//			public Missile(Vector position, Dimension hitbox, Image image, T target, Environment env,
//				IUnitMovement move, IUnitRenderer render, IUnitBehaviour behave)

		Missile<?> missile = new Missile<>(
				new Vector(from),
				null,
				res.getMissileImage(),
				target,
				env,
				move,
				render,
				behave);

		return missile;
	}
	
	public Wall getInstanceOfWall() {
		return null;
	}
	

}
