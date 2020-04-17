package implementation;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Engine;
import Engine.Vector;
import Graphics.ResourceLoader;
import Units.Bullet;
import Units.Player;
import Units.Unit;
import core.ISoundingBehaviour;
import core.IUnitBehaviour;
import Engine.MediaPlayer;
import Engine.Environment;

public class ShootingBehaviour implements IUnitBehaviour {

	Environment env;
	ResourceLoader res;
	ISoundingBehaviour sound;
	Player player;
	
	public ShootingBehaviour(ResourceLoader res, ISoundingBehaviour sound, Player player, Environment env) {
		this.player = player;
		this.env = env;
		this.sound = sound;
		this.res = res;
	}


	public void behave(Unit u) {
		if (!u.canSeePlayer) return;
		env.bullets.add(Engine.unitFact.getInstanceOfEnemyBullet(
				u.centerposition, player.centerposition));

		sound.produceSound();
	}



}
