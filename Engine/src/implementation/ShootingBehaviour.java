package implementation;

import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Engine;
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


	public void behave(Unit u) {
		if (!u.canSeePlayer) return;
		bullets.add(Engine.unitFact.getInstanceOfEnemyBullet(u.centerposition, player.centerposition));
	}

}
