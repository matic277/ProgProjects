package implementation;

import Engine.Environment;
import Engine.Vector;
import Units.Unit;
import core.IUnitMovement;

public class SimpleLinearMovement implements IUnitMovement<Unit> {
	
	Vector movingAndFacingDirection;
	
	public SimpleLinearMovement(Vector direction) {
		this.movingAndFacingDirection = direction;
	}

	@Override
	public void move(Unit unit, Environment env) {
		unit.updatePosition(movingAndFacingDirection);
//		System.out.println(unit.position.toString());
	}

}
