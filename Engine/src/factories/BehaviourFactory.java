package factories;

import Engine.Environment;
import Units.Turret;
import core.IUnitBehaviour;
import implementation.TurretBehaviour;

public class BehaviourFactory {

    Environment env;

    public BehaviourFactory(Environment env) {
        this.env = env;
    }

    public IUnitBehaviour<Turret> getTurretBehaviour() {
        return new TurretBehaviour(env);
    }
}
