package factories;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Environment;
import Engine.Vector;
import Units.Player;
import Units.Unit;
import Units.Wall;
import core.IUnitMovement;
import implementation.EnemyMovement;
import implementation.SimpleLinearMovement;
import implementation.TrackingMovement;

public class MovementFactory {
	
	Environment env;
	
	public MovementFactory(Environment env) {
		this.env = env;
	}
	
	public EnemyMovement getEnemeyMovement() {
		return new EnemyMovement(env);
	}

	public <T extends Unit> TrackingMovement<T> getTrackingMovement(T target) {
		return new TrackingMovement<>(target);
	}
	
	public SimpleLinearMovement getSimpleLinearMovement(Vector direction) {
		return new SimpleLinearMovement(direction);
	}

}
