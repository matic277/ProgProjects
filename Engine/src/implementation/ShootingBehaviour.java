package implementation;

import Engine.Engine;
import Graphics.ResourceLoader;
import Units.Enemy;
import Units.Player;
import Units.Unit;
import core.ISoundingBehaviour;
import core.IUnitBehaviour;
import Engine.Environment;

public class ShootingBehaviour <T extends Unit> implements IUnitBehaviour<T> {

	ISoundingBehaviour sound;
	
	public ShootingBehaviour(ISoundingBehaviour sound) {
		this.sound = sound;
	}

	@Override
	public void behave(T unit, Environment env) {
		if (!unit.canSeePlayer) return;
		env.bullets.add(Engine.unitFact.getInstanceOfEnemyBullet(
				unit, env.player.centerposition));

		sound.produceSound();
	}
}
