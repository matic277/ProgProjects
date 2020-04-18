package core;

import Engine.Environment;
import Units.Unit;

public interface IUnitMovement <T> {
	
	void move(T unit, Environment env);

}
