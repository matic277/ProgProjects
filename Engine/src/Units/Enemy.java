package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;
import Graphics.ResourceLoader;

public class Enemy extends Unit {
	
	Vector movingDirection;
	Vector facingDirection; // face to player
	
	Player player;
	double speed = 2;
	
	Thread controller;

	public Enemy(Vector position, Vector movingDirection, Dimension hitbox, Image image, Player player) {
		super(position, hitbox, image);
		this.movingDirection = (movingDirection == null)? new Vector(speed, 0) : movingDirection;
		this.facingDirection = new Vector(0, 0);
		this.player = player;
		
		controller = new Thread(() -> {
			Random r = new Random();
			while (true){
				this.movingDirection.rotateRandomly(30);
				
				
				if (r.nextDouble() < 0.01) {
					this.movingDirection.rotateRandomly(90);
				}
				Engine.Engine.sleep(100);
			}
		});
		controller.start();
	}

	@Override
	public void move() {
		updatePosition(movingDirection);
		
		// face player
		facingDirection.x = player.position.x - position.x;
		facingDirection.y = player.position.y - position.y;
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
