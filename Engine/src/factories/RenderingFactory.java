package factories;

import Units.*;
import core.IUnitRenderer;
import implementation.GuardRenderer;
import implementation.RotationRenderer;
import implementation.TurretRenderer;

public class RenderingFactory {
	
	// handled by engine?
	public IUnitRenderer<Player> getPlayerRenderer() {
		return null;
	}
	
	// enemies, bullets, missiles
	public RotationRenderer<Unit> getSimpleUnitRenderer() {
		return new RotationRenderer<>();
	}
	
	public IUnitRenderer<Asteroid> getAsteroidRenderer() {
		return null;
	}
	
	public IUnitRenderer<Guard> getGuardRenderer() {
		return new GuardRenderer(getSimpleUnitRenderer());
	}

	public IUnitRenderer<Turret> getTurretRenderer() {
		return new TurretRenderer(getSimpleUnitRenderer());
	}
}
