package implementation;

import Units.DummyUnit;
import Units.Guard;
import Units.Player;
import Units.Unit;
import core.IUnitRenderer;

import java.awt.*;
import java.awt.geom.Path2D;

public class GuardRenderer implements IUnitRenderer<Guard> {

    IUnitRenderer<Unit> defaultUnitRenderer;

    public GuardRenderer(IUnitRenderer<Unit> defaultUnitRenderer) {
        this.defaultUnitRenderer = defaultUnitRenderer;
    }

    @Override
    public void draw(Graphics2D g, Guard unit) {
        // normal rendering
        defaultUnitRenderer.draw(g, unit);

        // additional rendering
        unit.drawHP(g);

        Color c = new Color(255, 0, 255, 100);

		g.setColor(c);

		synchronized (unit.lock) {
			g.fill(unit.visionPoly); // TODO: method randomly crashes due to Path2D not being thread safe?
		}

		// debug: drawing path points
		for (int i=0; i<unit.path.length; i++) {
			g.drawRect((int)unit.path[i][0], (int)unit.path[i][1], 5, 5);
		}
    }
}
