package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;
import Graphics.ResourceLoader;

public class Enemy extends Unit {
	
	Player player;
	
	boolean canSeePlayer = false;
	
	Thread controller;

	public Enemy(Vector position, Vector movingDirection, Dimension hitbox, Image image, Player player) {
		super(position, hitbox, image);
		super.speed = 2;
		this.movingDirection = (movingDirection == null)? new Vector(speed, 0) : movingDirection;
		this.facingDirection = new Vector(0, 0);
		this.player = player;
		
		controller = new Thread(() -> {
			Random r = new Random();
			while (true){
				super.movingDirection.rotateRandomly(15);
				
				
				if (r.nextDouble() < 0.01) {
					super.movingDirection.rotateRandomly(45);
				}
				Engine.Engine.sleep(100);
			}
		});
		controller.start();
	}
	
	@Override
	public void move() {
		// handled below
	}

	public void move(ConcurrentLinkedQueue<Wall> walls) {
		super.updatePosition(movingDirection);

		// if colliding with the wall, change
		// the moving direction
		Wall[] wallsArr = walls.toArray(new Wall[0]);
		Vector nextPosition = new Vector(position);
		nextPosition.add(movingDirection);
		for (int i=0; i<wallsArr.length; i++) {
			// TODO:
			// (fix lazy way of detecting collision here)
			// this method of detecting becomes a
			// problem if enemies can move faster
			// than the thickness of walls. Rewrite
			// the same way collision is done with
			// bullets and walls
			if (wallsArr[i].hitbox.intersects(this.hitbox)) {
				movingDirection.multi(-1);
			}
		}
		
		// face player if there is no wall in 
		// between the player and this enemy
		// face to played if player is in direct
		// line of sight, otherwise face to where
		// its heading
		Line2D enemy_playerLine = new Line2D.Double(
			new Point((int)position.x, (int)position.y),
			new Point((int)player.centerposition.x, (int)player.centerposition.y)
		);
		
		for (int i=0; i<wallsArr.length; i++) {
			if (wallsArr[i].hitbox.intersectsLine(enemy_playerLine)) {
				// face direction
				facingDirection.x = movingDirection.x;
				facingDirection.y = movingDirection.y;
				canSeePlayer = false;
				return;
			}
		};
		// face player
		facingDirection.x = player.position.x - position.x;
		facingDirection.y = player.position.y - position.y;
		canSeePlayer = true;
	}

	@Override
	public void draw(Graphics2D g) {
		rotateAndDraw(g, facingDirection);
		drawHP(g);	
//		drawHitbox(g);
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

	public void shoot(ResourceLoader res, ConcurrentLinkedQueue<Bullet> bullets) {
		if (!canSeePlayer) return;
		Vector direction = new Vector(player.position.x - position.x, player.position.y - position.y);
		direction.norm();
		direction.multi(5);
		bullets.add(new EnemyBullet(
			new Vector(position.x, position.y),
			direction,
			null,
			res.getEnemyBulletImage()
		));
	}

}
