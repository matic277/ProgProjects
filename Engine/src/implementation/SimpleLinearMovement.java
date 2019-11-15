package implementation;

import Engine.Vector;
import Units.Unit;
import core.IUnitMovement;

public class SimpleLinearMovement implements IUnitMovement {
	
	Vector movingAndFacingDirection;
	
	public SimpleLinearMovement(Vector direction) {
		this.movingAndFacingDirection = direction;
	}

	@Override
	public void move(Unit unit) {
		unit.updatePosition(movingAndFacingDirection);
		System.out.println(unit.position.toString());
	}

}
