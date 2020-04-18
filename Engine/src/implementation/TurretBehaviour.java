package implementation;

import Engine.Engine;
import Engine.Environment;
import Units.Turret;
import Units.Unit;
import core.IUnitBehaviour;
import Engine.Vector;

public class TurretBehaviour implements IUnitBehaviour<Turret> {

    double rotationDegreesPerTick = 1;
    boolean canSeePlayer = false;

    Runnable shootingController;
    Thread controllerRunner;

    public TurretBehaviour() { }

    @Override
    public void behave(Turret unit, Environment env) {
        canSeePlayer = circleContainsPoint(
                env.player.centerposition,
                unit.centerposition.x,
                unit.centerposition.y,
                unit.diameter);

        if (canSeePlayer) {
            unit.facingDirection.x = env.player.centerposition.x - unit.centerposition.x;
            unit.facingDirection.y = env.player.centerposition.y - unit.centerposition.y;

            if (shootingController == null) {
                initController(unit, env);
                startController();
            } else if (!controllerRunner.isAlive()) {
                startController();
            }
            return;
        }


        // facingDirection shouldn't be (0, 0)
        // this is handled in constructor of Owner class
        // (in this case in Turret class)
        unit.facingDirection.rotate(rotationDegreesPerTick);
    }

    private void initController(Unit unit, Environment env) {
        shootingController = () -> {
            while (canSeePlayer) {
                env.bullets.add(Engine.unitFact.getInstanceOfBullet(
                        unit.centerposition,
                        env.player.centerposition));

                Engine.sleep(750);
            }
        };
    }

    private void startController() {
        controllerRunner = new Thread(shootingController);
        controllerRunner.start();
    }

    private boolean circleContainsPoint(Vector point, double cx, double cy, int d) {
         return (Math.sqrt(Math.pow((point.x - cx), 2) + Math.pow((point.y - cy), 2)) < d);
    }
}
