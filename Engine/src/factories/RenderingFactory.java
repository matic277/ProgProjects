package factories;

import core.IUnitRenderer;
import implementation.RotationRenderer;

public class RenderingFactory {
	
	// handled by engine?
	public IUnitRenderer getPlayerRenderer() {
		return null;
	}
	
	// enemies, bullets, missiles
	public IUnitRenderer getSimpleUnitRenderer() {
		return new RotationRenderer();
	}
	
	public IUnitRenderer getAsteroidRenderer() {
		return null;
	}
	
	public IUnitRenderer getGuardRenderer() {
		return null;
	}

}
