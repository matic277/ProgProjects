package core;

import Engine.Environment;
import Units.Unit;

public interface IUnitBehaviour<T> {
	
	void behave(T unit, Environment env);

}
