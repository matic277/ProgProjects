package Engine;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.File;
import java.sql.SQLOutput;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import Graphics.Painter;
import Graphics.ResourceLoader;
import Units.Asteroid;
import Units.Bullet;
import Units.Dragon;
import Units.DummyUnit;
import Units.Enemy;
import Units.EnemyBullet;
import Units.Guard;
import Units.Missile;
import Units.Player;
import Units.Unit;
import Units.Wall;
import core.IObserver;
import core.ISoundingBehaviour;
import factories.MovementFactory;
import factories.RenderingFactory;
import factories.UnitFactory;
import implementation.SimpleSoundBehaviour;

public class Engine implements IObserver, Runnable {
	
	double playerSpeed = 4;
	double bulletSpeed = 30;
	double missileSpeed = 5;
	int fireRate = 75;
	
	final Random r = new Random();
	double enemyFireProbability = 0.03;
	
	Dimension panelSize;
	public static Rectangle bounds; // same size as panelSize
	Painter painter;

	MediaPlayer media;
	
	public static MovementFactory movFact;
	public static RenderingFactory renFact;
	public static UnitFactory unitFact;
	
	public Environment env;
	
	Point mouse;
	
	boolean[] keyCodes = new boolean[256];
	boolean mousePressed = false;
	boolean rightPressed = false;
	boolean leftPressed = false;
	
	ResourceLoader res;

//	SimpleRenderer simpleRenderer;
//	NullRenderer nullRenderer;
//	ImageRenderer imageRenderer;
	
	public Engine(Dimension panelSize) {
		this.panelSize = panelSize;
		Engine.bounds = new Rectangle(0, 0, panelSize.width, panelSize.height);
		res = new ResourceLoader();
		env = new Environment();
		mouse = new Point();

		// listeners
		MousepadListener ml = new MousepadListener();
		KeyboardListener kl = new KeyboardListener();
		ml.addObserver(this);
		kl.addObserver(this);

		
		renFact = new RenderingFactory();
		movFact = new MovementFactory(env);
		unitFact = new UnitFactory(res, env, movFact, renFact);

		// <-- PLAYER -->
		env.player = unitFact.getInstanceOfPlayer(mouse);




		env.player.render = renFact.getSimpleUnitRenderer();

//		enemies.add(new Enemy(
//			new Vector(50, 50),
//			null,
//			null,
//			res.getEnemyImage(),
//			player
//		));

		env.enemies.add(unitFact.getInstanceOfEnemy());
		
//		guards.add(new Guard(
//			new Vector(50, 50),
//			null,
//			null,
//			res.getEnemyImage(),
//			player
//		));
		
//		Unit dummy = new DummyUnit(new Vector(300, 300), new Dimension(50, 40), null);
		//dummyUnits.add(dummy);
		
//		Asteroid a = new Asteroid(new Vector(300, 300), null, res.getAsteroidImage());
//		asteroids.add(a);
		
//		walls.add(new Wall(
//			new Vector(400, 0),
//			new Dimension(10, 500),
//			null
//		));
//		walls.add(new Wall(
//			new Vector(10, 10),
//			new Dimension(10, 500),
//			null
//		));
//		walls.add(new Wall(
//			new Vector(10, 10),
//			new Dimension(500, 10),
//			null
//		));
		
//		dragon = new Dragon(new Vector(0, 0), null, 500, res.getDragonHeadImage(), res.getDragonBodyImage(), res.getDragonTailImage());
		
		painter = new Painter(this, panelSize, ml, kl);
	}
	
	
	@Override
	public void run() {
		while(true)
		{
			movePlayer();
			moveUnits();
			
			sleep(10);
		}
	}
	
	private Unit getClosestEnemy(Point location) {
		if (env.enemies.isEmpty() && env.asteroids.isEmpty()) return null;

		Unit closest = null;
		if (env.enemies.isEmpty()) closest = env.asteroids.element();
		else closest = env.enemies.element();

		double minDistance = Double.MAX_VALUE;
		for (Unit u : env.enemies) {
			double newDistance = u.getLocation().distance(new Vector(location.getX(), location.getY()));
			if (newDistance < minDistance) {
				minDistance = newDistance;
				closest = u;
			}
		}
		for (Unit u : env.asteroids) {
			double newDistance = u.getLocation().distance(new Vector(location.getX(), location.getY()));
			if (newDistance < minDistance) {
				minDistance = newDistance;
				closest = u;
			}
		}
		return closest;
	}
	
	
	private void moveUnits() {
		// can't just call checkCollision between
		// bullets and walls, bullet might be too
		// fast and might skip over walls
//		walls.forEach(w -> {
//			bullets.forEach(b -> {
//				Line2D projectory = new Line2D.Double(
//					new Point((int)b.getLocation().x, (int)b.getLocation().y),
//					new Point((int)(b.getMovingDirection().x + b.getLocation().x), (int)(b.getMovingDirection().y + b.getLocation().y))
//				);
//				if (w.getHitbox().intersectsLine(projectory)) {
//					bullets.remove(b);
//				}
//			});
//			missiles.forEach(m -> {
//				if (w.checkCollision(m)) missiles.remove(m);
//			});
//		});
		
		//System.out.println(bullets.size());
		
		
		// if bullet is type enemy bullet, then don't remove it
		// because it might still be in enemies hitbox (where its created)

		// move enemies and check collisions
		env.enemies.forEach(e -> {
			e.move();
			env.bullets.forEach(b -> {
				// reduce hp remove if unit is dead
//				if (e.checkCollision(b) && !(b instanceof EnemyBullet)) {
//					bullets.remove(b);
//					System.out.println("bullet removed1");
//					// TODO: set bullet dmg and use it here!
//					if (e.lowerHealth(5)) {
//						enemies.remove(e);
//					}
//				}
			});
			// missiles that collide with enemies that
			// aren't their targets
			env.missiles.forEach(m -> {
				// TODO: same here as for bullets
				if (e.checkCollision(m)) {
					env.missiles.remove(m);
					if (e.lowerHealth(50)) {
						env.enemies.remove(e);
					}
				}
			});
			if (r.nextDouble() < enemyFireProbability) e.behave();
			if (e.isOutOfBounds()) e.reposition();
		});
		
		// bullet collisions
		env.bullets.forEach(b -> {
			if (env.player.checkCollision(b) && b instanceof EnemyBullet) {
				env.bullets.remove(b);
				// TODO: var for bullet dmg
				if (env.player.lowerHealth(5)) {
					// TODO: player out of hp
				}
			}
			env.asteroids.forEach(a -> {
				if (b.checkCollision(a)) {
					env.bullets.remove(b);
					if (a.lowerHealth(10)) env.asteroids.remove(a);
				}
			});
		});

		// move bullets
		env.bullets.forEach(b -> {
			b.move();
			if (b.isOutOfBounds()) {
				env.bullets.remove(b);
			}
		});

		// move missiles
		env.missiles.forEach(m -> {
			if (isTargetNotAlive(m.target)) {
				Unit target = getClosestEnemy(mouse);
				if (target != null) m.target = target;
				else env.missiles.remove(m);

				return;
			}
			m.move();

			if (m.checkCollision(m.target)) {
				env.missiles.remove(m);
				if (m.target.lowerHealth(50)) {
					// dirty? but how else?
					if (m.getTarget().getClass() == Enemy.class)
						env.enemies.remove(m.target);
					else
						env.asteroids.remove(m.target);
				}
			}

		});
//
//		asteroids.forEach(a -> {
//			a.move();
//			if (a.isOutOfBounds()) a.reposition();
//		});
//
////		if (dragon != null) {
////			dragon.move();
////			dragon.checkCollision(bullets, asteroids);
////		}
//
//		guards.forEach(g -> {
//			g.move(walls);
//			if (g.isOutOfBounds()) g.reposition();
//		});
	}
	
	private void deleteTarget(Unit target) {
		// dirty? but how else?
		if (target.getClass() == Enemy.class)
			env.enemies.remove(target);
		else
			env.asteroids.remove(target);
	}
	
	private void movePlayer() {
		Vector force = new Vector(0, 0);
		// W
		if (keyCodes[87]) force.y += -playerSpeed;
		// A
		if (keyCodes[65]) force.x += -playerSpeed;
		// S
		if (keyCodes[83]) force.y +=  playerSpeed;
		// D
		if (keyCodes[68]) force.x +=  playerSpeed;

		// can't move through walls
		Line2D projectory = new Line2D.Double(
			new Point((int)env.player.getLocation().x, (int)env.player.getLocation().y),
			new Point((int)(force.x + env.player.getLocation().x), (int)(force.y + env.player.getLocation().y))
		);
		
		Wall[] wallsArr = env.walls.toArray(new Wall[0]);
		for (int i=0; i<wallsArr.length; i++) {
			if (wallsArr[i].getHitbox().intersectsLine(projectory)) {
				return;
			}
		};
		env.player.updateDirection();
		env.player.updatePosition(force);
	}
	
	
	Thread bulletLauncher = new Thread();
	private void createBullet() {
		// spawn bullets until mouse is released
		// fire every *fireRate* milliseconds
		if (bulletLauncher.isAlive()) return;
		
		bulletLauncher = new Thread(() -> {
			while(leftPressed) {
				Vector direction = new Vector(
					mouse.x - env.player.getLocation().x,
					mouse.y - env.player.getLocation().y
				);
				direction.norm();
				direction.multi(bulletSpeed);
				
				Bullet b = unitFact.getInstanceOfBullet(env.player.centerposition, new Vector(mouse.x, mouse.y));
				b.setUnitImage(res.getBulletImage());
				env.bullets.add(b);

				// play sound
//				MediaPlayer media = new MediaPlayer("C:/git/ProgProjects/Engine/Resources/sounds/sf_bullet.wav");
//				media.setVolume(0.2F);
//				new Thread(media).start();

				// plays sound
				env.player.behave();

				sleep(fireRate);
			}
		});
		bulletLauncher.start();
	}
	
	Thread missileLauncher = new Thread();
	private void createMissile(Unit target) {
		// if thread responsible is already up and running,
		// it means a new one shouldn't be created and run
		if (missileLauncher.isAlive()) return;

		missileLauncher = new Thread(() -> {
			while(rightPressed) {
				//if (!isTargetAlive(target)) continue;
				Missile<?> m = unitFact.getInstanceOfMissile(env.player.centerposition, getClosestEnemy(mouse));
				env.missiles.add(m);

				// play sound
				MediaPlayer media = new MediaPlayer("C:/git/ProgProjects/Engine/Resources/sounds/sf_missile.wav");
				media.setVolume(0.2F);
				new Thread(media).start();

				sleep(500);
			}
		});
		missileLauncher.start();
	}
	
	public boolean isTargetAlive(Unit target) {
		if (env.enemies.contains(target)) return true;
		return env.asteroids.contains(target);
	}

	public boolean isTargetNotAlive(Unit target) {
		return !isTargetAlive(target);
	}
	
	
	@Override
	public void notifyMouseMoved(Point location) {
		mouse.setLocation(location);;
	}
	
	@Override
	public void notifyMousePressed(MouseEvent event) {
		mousePressed = !mousePressed;
	}
	
	@Override
	public void notifyMouseReleased(MouseEvent event) {
		
	}
	
	@Override
	public void notifyKeysPressed(boolean[] keyCodes) {
		this.keyCodes = keyCodes;
	}
	
	@Override
	public void notifyCharacterKeyPressed(Set<Character> keys) {}
	
//	public void addPlayer(Player player) {
//		this.player = player;
//	}
	
//	public void addMovableUnit(MovableUnit unit) {
//		movableUnits.add(unit);
//	}
//	
//	public void addMovableUnit(IStaticUnit unit) {
//		staticUnits.add(unit);
//	}

	public static void sleep(int t) {
		try { Thread.sleep(t); } 
		catch (InterruptedException e) {e.printStackTrace(); }
	}

	public void notifyRightPress(Point location) { 
		rightPressed = !rightPressed;
		// dont spawn if there are no enemies
		if (env.enemies.isEmpty() && env.asteroids.isEmpty()) return;
		createMissile(getClosestEnemy(mouse));
	}
	
	public void notifyLeftPress(Point location) {
		leftPressed = !leftPressed;
		createBullet();
	}
	
	public void notifyRightRelease(Point location) { rightPressed = !rightPressed; }
	public void notifyLeftRelease(Point location) { leftPressed = !leftPressed; }
}
