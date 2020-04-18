package implementation;

import Units.Guard;
import Units.Turret;
import Units.Unit;
import core.IUnitRenderer;

import java.awt.*;

public class TurretRenderer implements IUnitRenderer<Turret> {

    IUnitRenderer<Unit> defaultUnitRenderer;

    public TurretRenderer(IUnitRenderer<Unit> defaultUnitRenderer) {
        this.defaultUnitRenderer = defaultUnitRenderer;
    }

    @Override
    public void draw(Graphics2D g, Turret unit) {
        // normal rendering
        defaultUnitRenderer.draw(g, unit);

        // additional rendering
        unit.drawHP(g);

        // range indicator
        Color c = new Color(255, 0, 255, 100);
        g.setColor(c);
        g.drawOval((int)unit.centerposition.x - unit.diameter,
                (int)unit.centerposition.y - unit.diameter,
                unit.diameter * 2,
                unit.diameter * 2);
    }
}
