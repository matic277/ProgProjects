package factories;

import Engine.Environment;
import Engine.Vector;
import Units.Guard;
import Units.Unit;
import core.IUnitMovement;
import implementation.EnemyMovement;
import implementation.GuardMovement;
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

	public <T extends Unit, G extends Unit> TrackingMovement<T, G> getTrackingMovement(T target) {
		return new TrackingMovement<>(target);
	}
	
	public SimpleLinearMovement getSimpleLinearMovement(Vector direction) {
		return new SimpleLinearMovement(direction);
	}

    public IUnitMovement<Guard> getGuardMovement() {
		return new GuardMovement();
    }
}
