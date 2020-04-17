package factories;

import Units.Asteroid;
import Units.Guard;
import Units.Player;
import Units.Unit;
import core.IUnitRenderer;
import implementation.GuardRenderer;
import implementation.RotationRenderer;

public class RenderingFactory {
	
	// handled by engine?
	public IUnitRenderer<Player> getPlayerRenderer() {
		return null;
	}
	
	// enemies, bullets, missiles
	public IUnitRenderer<Unit> getSimpleUnitRenderer() {
		return new RotationRenderer();
	}
	
	public IUnitRenderer<Asteroid> getAsteroidRenderer() {
		return null;
	}
	
	public IUnitRenderer<Guard> getGuardRenderer() {
		return new GuardRenderer(getSimpleUnitRenderer());
	}

}
