package factories;

import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;
import Graphics.ResourceLoader;
import Units.Player;
import Units.Unit;
import Units.Wall;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;
import implementation.EnemyMovement;
import implementation.RotationRenderer;
import implementation.ShootingBehaviour;
import implementation.SimpleLinearMovement;

public class UnitFactory {
	
	ResourceLoader res;
	Player player;
	ConcurrentLinkedQueue<Wall> walls;
	ConcurrentLinkedQueue<Unit> bullets;
	MovementFactory movFact;
	RenderingFactory renFact;
	
	public UnitFactory(ResourceLoader res, Player player, ConcurrentLinkedQueue<Wall> walls, ConcurrentLinkedQueue<Unit> bullets,  MovementFactory movFact, RenderingFactory renFact) {
		this.res = res;
		this.player = player;
		this.walls = walls;
		this.movFact = movFact;
		this.renFact = renFact;
		this.bullets = bullets;
	}
	
	public Unit getInstanceOfPlayer() {
//		return new Unit(
//			
//		);
		return null;
	}
	
	public Unit getInstanceOfBullet() {
		return null;
	}
	
	public Unit getInstanceOfGuard() {
		return null;
	}
	
	public Unit getInstanceOfEnemy() {
		IUnitRenderer renderer = renFact.getSimpleUnitRenderer();
		EnemyMovement movement = movFact.getEnemeyMovement();
		IUnitBehaviour behaviour = new ShootingBehaviour(res, player, bullets);
		Unit u = new Unit(
			new Vector(50, 50),
			null,
			res.getEnemyImage(),
			movement,
			renderer,
			behaviour
		);
		movement.assignUnitToThisClass(u);
		return u;
	}
	
	public Unit getInstanceOfEnemyBullet(Vector from, Vector to) {
		Vector direction = new Vector(to.x - from.x, to.y - from.y);
		direction.norm();
		direction.multi(5);
		
		SimpleLinearMovement movement = movFact.getSimpleLinearMovement(direction);
		IUnitRenderer renderer = renFact.getSimpleUnitRenderer();
		
		Unit u = new Unit(
			from,
			null,
			res.getEnemyBulletImage(),
			movement,
			renderer,
			null
		);
		u.setMovingDirection(direction);
		return u;
	}
	
	public Unit getInstanceOfMissile() {
		return null;
	}
	
	public Unit getInstanceOfWall() {
		return null;
	}
	

}
