package Units;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;
import Graphics.ResourceLoader;

public class Guard extends Unit {

	Player player;
	
	Object lock = new Object();
	
	Vector sight1;
	Vector sight2;
	Path2D visionPoly;
	double seeingAngle = 30;
	double[][] path;
	int currentPathPosition = 0;
	
	boolean canSeePlayer = false;

	public Guard(Vector position, Vector movingDirection, Dimension hitbox, Image image, Player player) {
		super(position, hitbox, image);
		super.speed = 2;
		this.movingDirection = (movingDirection == null)? new Vector(speed, 0) : movingDirection;
		this.facingDirection = new Vector(0, 0);
		this.player = player;
		
		sight1 = new Vector(facingDirection);
		sight1.rotate(seeingAngle/2);
		sight2 = new Vector(facingDirection);
		sight2.rotate(360-seeingAngle/2);
		
		visionPoly = new Path2D.Double();
		visionPoly.moveTo(centerposition.x, centerposition.y);
		visionPoly.lineTo(centerposition.x+sight1.x, centerposition.y+sight1.y);
		visionPoly.lineTo(centerposition.x+sight2.x, centerposition.y+sight2.y);
		
		path = new double[][] {
			{200, 200},
			{250, 150},
			{300, 150},
			{350, 200},
			{350, 600},
			{300, 650},
			{250, 650},
			{200, 600}
		};
		position.x = path[0][0];
		position.y = path[0][1];
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
//		Vector nextPosition = new Vector(position);
//		nextPosition.add(movingDirection);
//		for (int i=0; i<wallsArr.length; i++) {
//			// TODO:
//			// (fix lazy way of detecting collision here)
//			// this method of detecting becomes a
//			// problem if enemies can move faster
//			// than the thickness of walls. Rewrite
//			// the same way collision is done with
//			// bullets and walls
//			if (wallsArr[i].hitbox.intersects(this.hitbox)) {
//				movingDirection.multi(-1);
//			}
//		}
		
		if ((Math.abs(position.x - path[currentPathPosition][0])) < 2 &&
			(Math.abs(position.y - path[currentPathPosition][1])) < 2)
		{
			currentPathPosition++;
			if (currentPathPosition > path.length-1) currentPathPosition = 0;
		}
		movingDirection.x = path[currentPathPosition][0] - position.x;
		movingDirection.y = path[currentPathPosition][1] - position.y;
		movingDirection.norm();
		movingDirection.multi(speed);
		updatePosition(movingDirection);
		
		// create a path from *sightX* vectors and
		// current position, result is a triangle.
		// if this shape contains the point(position)
		// of *player* then face towards it, otherwise
		// keep facing the traveling direction
		// --
		// Engine thread resetting visionPoly and
		// Painter thread using visionPoly to draw
		// the visible area causes thread-safety
		// errors, so lock is required here and when
		// drawing in the draw() function
		synchronized (lock) {
			visionPoly.reset();
			visionPoly.moveTo(centerposition.x, centerposition.y);
			visionPoly.lineTo(centerposition.x+sight1.x, centerposition.y+sight1.y);
			visionPoly.lineTo(centerposition.x+sight2.x, centerposition.y+sight2.y);
		}
		if (!visionPoly.contains(player.getLocation().x, player.getLocation().y)) {
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
		rotateAndDraw(g);
		drawHP(g);	
		
		Color c = new Color(255, 0, 255, 100);
		
		
		g.setColor(c);
		
		synchronized (lock) {
			g.fill(visionPoly); // TODO: method randomly crashes due to Path2D not being thread safe?
		}
		
		// debug: drawing path points
		for (int i=0; i<path.length; i++) {
			g.drawRect((int)path[i][0], (int)path[i][1], 5, 5);
		}
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
