package factories;

import Engine.Environment;
import Units.Turret;
import Units.Unit;
import core.ISoundingBehaviour;
import core.IUnitBehaviour;
import implementation.ShootingBehaviour;
import implementation.SimpleSoundBehaviour;
import implementation.TurretBehaviour;

import java.io.File;

public class BehaviourFactory {

    Environment env;

    public BehaviourFactory(Environment env) {
        this.env = env;
    }

    public IUnitBehaviour<Turret> getTurretBehaviour() {
        return new TurretBehaviour();
    }

    public <T extends Unit> IUnitBehaviour<T> getShootingBehaviour() {
        return new ShootingBehaviour<>(getSoundingBehaviour());
    }

    public ISoundingBehaviour getSoundingBehaviour() {
        File f = new File("./Resources/sounds/sf_enemybullet.wav");
        return new SimpleSoundBehaviour(f, ISoundingBehaviour.DEFAULT_SOUND_VOLUME);
    }
}
