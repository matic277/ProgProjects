package implementation;

import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;
import Graphics.ResourceLoader;
import Units.Bullet;
import Units.Player;
import Units.Unit;
import core.IUnitBehaviour;

public class ShootingBehaviour implements IUnitBehaviour {
	
	ConcurrentLinkedQueue<Unit> bullets;
	ResourceLoader res;
	Player player;
	
	public ShootingBehaviour(ResourceLoader res, Player player, ConcurrentLinkedQueue<Unit> bullets) {
		this.player = player;
		this.bullets = bullets;
		this.res = res;
	}

	@Override
	public void behave(Unit u) {
		if (!u.canSeePlayer) return;
		
//		Vector direction = new Vector(player.position.x - u.position.x, player.position.y - u.position.y);
//		direction.norm();
//		direction.multi(5);
		bullets.add(Engine.Engine.unitFact.getInstanceOfEnemyBullet(u.centerposition, player.centerposition));
	}

}
