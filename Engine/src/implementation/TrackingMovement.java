package implementation;

import Engine.Environment;
import Units.Unit;
import core.IUnitMovement;

// are generics needed here? prolly not
public class TrackingMovement <T extends Unit> implements IUnitMovement {

    T target;

    public TrackingMovement(T target) {
        this.target = target;
    }

    @Override
    public void move(Unit unit, Environment env) {
		unit.movingDirection.x = target.getLocation().x - unit.position.x;
		unit.movingDirection.y = target.getLocation().y - unit.position.y;
		unit.movingDirection.norm();
		unit.movingDirection.multi(unit.speed);

		unit.facingDirection = unit.movingDirection;
		unit.updatePosition(unit.movingDirection);
    }

    public T getTarget() { return target; }
}


