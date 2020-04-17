package implementation;

import Engine.Environment;
import Engine.Vector;
import Units.Guard;
import Units.Wall;
import core.IUnitMovement;

public class GuardMovement implements IUnitMovement<Guard> {

    @Override
    public void move(Guard unit, Environment env) {
		unit.sight1.set(unit.facingDirection);
		unit.sight1.rotate(unit.seeingAngle/2);
		unit.sight1.norm();
		unit.sight1.multi(600);

		unit.sight2.set(unit.facingDirection);
		unit.sight2.rotate(360 - unit.seeingAngle/2);
		unit.sight2.norm();
		unit.sight2.multi(600);


		if ((Math.abs(unit.position.x - unit.path[unit.currentPathPosition][0])) < 2 &&
			(Math.abs(unit.position.y - unit.path[unit.currentPathPosition][1])) < 2)
		{
			unit.currentPathPosition++;
			if (unit.currentPathPosition > unit.path.length-1) unit.currentPathPosition = 0;
		}
        unit.movingDirection.x = unit.path[unit.currentPathPosition][0] - unit.position.x;
        unit.movingDirection.y = unit.path[unit.currentPathPosition][1] - unit.position.y;
        unit.movingDirection.norm();
        unit.movingDirection.multi(unit.speed);
        unit.updatePosition(unit.movingDirection);

//		unit.currentPathPosition = 3;

		// create a path from *sightX* vectors and
		// current position, result is a triangle.
		// if this shape contains the point(position)
		// of *player* then face towards it, otherwise
		// keep facing the traveling direction
		// --
		// Engine thread resetting visionPoly and
		// Painter thread using visionPoly to draw
		// the visible area causes thread-safety
		// errors, so lock is required here and when
		// drawing in the draw() function
		synchronized (unit.lock) {
			unit.visionPoly.reset();
			unit.visionPoly.moveTo(unit.centerposition.x, unit.centerposition.y);
			unit.visionPoly.lineTo(unit.centerposition.x+unit.sight1.x, unit.centerposition.y+unit.sight1.y);
			unit.visionPoly.lineTo(unit.centerposition.x+unit.sight2.x, unit.centerposition.y+unit.sight2.y);
		}

		if (!unit.visionPoly.intersects(env.player.hitbox)) {
            unit.facingDirection.x = unit.movingDirection.x;
            unit.facingDirection.y = unit.movingDirection.y;
            unit.canSeePlayer = false;
			return;
		}

		// face player
		unit.facingDirection.x = env.player.position.x - unit.position.x;
		unit.facingDirection.y = env.player.position.y - unit.position.y;
		unit.canSeePlayer = true;
    }
}
