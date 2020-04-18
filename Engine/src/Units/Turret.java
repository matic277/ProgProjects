package Units;

import Engine.Environment;
import Engine.Vector;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

import java.awt.*;

public class Turret extends Unit {

    public int diameter;

    public Turret(Vector position, Dimension size, Image image, Environment env, int diameter,
                  IUnitMovement<Turret> move, IUnitRenderer<Turret> render, IUnitBehaviour<Turret> behave) {
        super(position, size, image, env, move, render, behave);
        this.diameter = diameter;

        this.facingDirection = new Vector(1, 1);
    }


}
