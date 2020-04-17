package core;

import java.awt.Graphics2D;

import Units.Unit;

public interface IUnitRenderer <T> {
	
	void draw(Graphics2D g, T unit);

}
