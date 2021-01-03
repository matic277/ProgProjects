package implementation;

import Engine.Environment;
import Units.Missile;
import Units.Unit;
import core.IUnitMovement;

// are generics needed here? prolly not
public class TrackingMovement implements IUnitMovement<Missile> {

    Unit target;

    public TrackingMovement(Unit target) {
        this.target = target;
    }

    @Override
    public void move(Missile unit, Environment env) {
		unit.movingDirection.x = target.getLocation().x - unit.position.x;
		unit.movingDirection.y = target.getLocation().y - unit.position.y;
		unit.movingDirection.norm();
		unit.movingDirection.multi(unit.speed);

		unit.facingDirection = unit.movingDirection;
		unit.updatePosition(unit.movingDirection);
    }

    public Unit getTarget() { return target; }
}


