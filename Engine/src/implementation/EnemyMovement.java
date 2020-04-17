package implementation;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Environment;
import Engine.Vector;
import Units.Player;
import Units.Unit;
import Units.Wall;
import core.IUnitMovement;

public class EnemyMovement implements IUnitMovement {
	
	Environment env;
	
	int speed = 2;
	
	Unit unit;
	
	Thread movementController;
	
	public EnemyMovement(Environment emv) {
		this.env = env;
	}
	
	public void assignUnitToThisClass(Unit unit) {
		this.unit = unit;
		
		// start controller that moves unit
		Thread controller = new Thread(() -> {
			this.unit.setMovingDirection(new Vector(speed, 0));
			Random r = new Random();
			while (true){
				if (r.nextDouble() <= 0.2) {
					this.unit.movingDirection.rotate(90);
				}
				this.unit.movingDirection.rotateRandomly(40);
				
				// sleep for somewhere between x amd y
				// then update direction again
				Engine.Engine.sleep(300 + r.nextInt(500));
			}
		});
		controller.start();
	}

	@Override
	public void move(Unit u, Environment env) {
//		System.out.println("moving for: " + this.unit.hashCode());
		u.updatePosition(u.movingDirection);

		// if colliding with the wall, change
		// the moving direction
		Wall[] wallsArr = env.walls.toArray(new Wall[0]);
		Vector nextPosition = new Vector(u.position);
		nextPosition.add(u.movingDirection);
		for (int i=0; i<wallsArr.length; i++) {
			// TODO:
			// (fix lazy way of detecting collision here)
			// this method of detecting becomes a
			// problem if enemies can move faster
			// than the thickness of walls. Rewrite
			// the same way collision is done with
			// bullets and walls
			if (wallsArr[i].hitbox.intersects(u.hitbox)) {
				u.movingDirection.multi(-1);
			}
		}
		
		// face player if there is no wall in 
		// between the player and this enemy
		// face to played if player is in direct
		// line of sight, otherwise face to where
		// its heading
		Line2D enemy_playerLine = new Line2D.Double(
			new Point((int)u.position.x, (int)u.position.y),
			new Point((int)env.player.centerposition.x, (int)env.player.centerposition.y)
		);
		
		for (int i=0; i<wallsArr.length; i++) {
			if (wallsArr[i].hitbox.intersectsLine(enemy_playerLine)) {
				// face direction
				u.facingDirection.x = u.movingDirection.x;
				u.facingDirection.y = u.movingDirection.y;
				u.canSeePlayer = false;
				return;
			}
		};
		// face player
		u.facingDirection.x = env.player.position.x - u.position.x;
		u.facingDirection.y = env.player.position.y - u.position.y;
		u.canSeePlayer = true;
		
	}

	public void test() {
		System.out.println("TESTED");
	}

}
