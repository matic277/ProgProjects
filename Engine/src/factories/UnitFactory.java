package factories;

import java.awt.*;
import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Environment;
import Engine.Vector;
import Graphics.ResourceLoader;
import Units.*;
import core.ISoundingBehaviour;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;
import implementation.*;

public class UnitFactory {
	
	ResourceLoader res;
	Environment env;

	MovementFactory movFact;
	RenderingFactory renFact;
	BehaviourFactory behFact;
	
	public UnitFactory(ResourceLoader res, Environment env,MovementFactory movFact,
					   RenderingFactory renFact, BehaviourFactory behFact) {
		this.res = res;
		this.env = env;
		this.movFact = movFact;
		this.renFact = renFact;
		this.behFact = behFact;
	}

	public Turret getInstanceOfTurret() {
		IUnitMovement<Turret> move = (u, e) -> { }; // don't move
		IUnitRenderer<Turret> render = renFact.getTurretRenderer();
		IUnitBehaviour<Turret> behave = behFact.getTurretBehaviour();

		return new Turret(
				new Vector(500, 300),
				null,
				res.getEnemyImage(),
				env,
				300,
				move,
				render,
				behave);
	}
	
	public Player getInstanceOfPlayer(Point mouse) {
		File f = new File("./Resources/sounds/sf_bullet.wav");
		ISoundingBehaviour sound = new SimpleSoundBehaviour(f, 0.2F);

		return new Player(
				new Vector(600, 400),
				null,
				res.getPlayerImage(),
				mouse,
				env,
				(u, e) -> {},
				null,
				(u) -> sound.produceSound()); // behaviour is just making a sound
	}
	
	public Guard getInstanceOfGuard() {
		IUnitMovement<Guard> move = movFact.getGuardMovement();
		IUnitRenderer<Guard> render = renFact.getGuardRenderer();
		IUnitBehaviour behave = (u) -> { };

		return new Guard(
				new Vector(50, 50),
				null,
				res.getEnemyImage(),
				env,
				move,
				render,
				behave);
	}
	
	public Unit getInstanceOfEnemy() {
		File f = new File("./Resources/sounds/sf_enemybullet.wav");

		IUnitRenderer renderer = renFact.getSimpleUnitRenderer();
		EnemyMovement movement = movFact.getEnemeyMovement();
		ISoundingBehaviour sound = new SimpleSoundBehaviour(f, 0.2F);
		IUnitBehaviour behaviour = new ShootingBehaviour(res, sound, env.player, env);

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

	// TODO: method is a copy of get enemy instance bullet
	public Bullet getInstanceOfBullet(Vector from, Vector to) {
		Vector direction = new Vector(to.x - from.x, to.y - from.y);
		Vector startingPosition = new Vector(from);
		direction.norm();
		direction.multi(15);

		SimpleLinearMovement move = movFact.getSimpleLinearMovement(direction);
		IUnitRenderer render = renFact.getSimpleUnitRenderer();
		IUnitBehaviour behave = (u) -> { };

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

		return new Missile<>(
				new Vector(from),
				null,
				res.getMissileImage(),
				target,
				env,
				move,
				render,
				behave);
	}
	
	public Wall getInstanceOfWall() {
		return null;
	}
	

}
