package Units;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Path2D;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;
import Graphics.ResourceLoader;

public class Guard extends Unit {

	Vector movingDirection;
	Vector facingDirection; // face to player
	
	Player player;
	double speed = 2;
	
	double seeingAngle = 30;
	
	Vector sight1;
	Vector sight2;
	
	boolean canSeePlayer = false;
	
	Thread controller;
	
	Path2D triangle;

	public Guard(Vector position, Vector movingDirection, Dimension hitbox, Image image, Player player) {
		super(position, hitbox, image);
		this.movingDirection = (movingDirection == null)? new Vector(speed, 0) : movingDirection;
		this.facingDirection = new Vector(0, 0);
		this.player = player;
		
		sight1 = new Vector(facingDirection);
		sight1.rotate(seeingAngle/2);
		sight2 = new Vector(facingDirection);
		sight2.rotate(360-seeingAngle/2);
		
		triangle = new Path2D.Double();
		triangle.moveTo(centerposition.x, centerposition.y);
		triangle.lineTo(centerposition.x+sight1.x, centerposition.y+sight1.y);
		triangle.lineTo(centerposition.x+sight2.x, centerposition.y+sight2.y);
		
		controller = new Thread(() -> {
			Random r = new Random();
			while (true){
				this.movingDirection.rotateRandomly(15);
				
				if (r.nextDouble() < 0.01) {
					this.movingDirection.rotateRandomly(45);
				}
				Engine.Engine.sleep(100);
			}
		});
		controller.start();
	}
	
	@Override
	public void move() {
		
	}

	public void move(ConcurrentLinkedQueue<Wall> walls) {
		sight1.set(facingDirection);
		sight1.rotate(seeingAngle/2);
		sight1.norm();
		sight1.multi(600);
		
		sight2.set(facingDirection);
		sight2.rotate(360-seeingAngle/2);
		sight2.norm();
		sight2.multi(600);
		
		
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
		
		updatePosition(movingDirection);
		
		// create a path from *sightX* vectors and
		// current position, result is a triangle.
		// if this shape contains the point(position)
		// of *player* then face towards it, otherwise
		// keep facing the traveling direction
//		triangle = new Path2D.Double();
		triangle.reset();
		triangle.moveTo(centerposition.x, centerposition.y);
		triangle.lineTo(centerposition.x+sight1.x, centerposition.y+sight1.y);
		triangle.lineTo(centerposition.x+sight2.x, centerposition.y+sight2.y);
		if (!triangle.contains(player.getLocation().x, player.getLocation().y)) {
			facingDirection.x = movingDirection.x;
			facingDirection.y = movingDirection.y;
			canSeePlayer = false;
			return;
		}
		
		// face player
		facingDirection.x = player.position.x - position.x;
		facingDirection.y = player.position.y - position.y;
		canSeePlayer = true;
	}

	@Override
	public void draw(Graphics2D g) {
		rotateAndDraw(g, facingDirection);
		drawHP(g);	
		
		Color c = new Color(255, 0, 255, 150);
		g.setColor(c);
		g.fill(triangle);
		
//		g.drawLine((int)centerposition.x, (int)centerposition.y, (int)(centerposition.x+sight1.x), (int)(centerposition.y+sight1.y));
//		g.drawLine((int)centerposition.x, (int)centerposition.y, (int)(centerposition.x+sight2.x), (int)(centerposition.y+sight2.y));
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
//		if (!canSeePlayer) return;
//		Vector direction = new Vector(player.position.x - position.x, player.position.y - position.y);
//		direction.norm();
//		direction.multi(5);
//		bullets.add(new EnemyBullet(
//			new Vector(position.x, position.y),
//			direction,
//			null,
//			res.getEnemyBulletImage()
//		));
	}

}
