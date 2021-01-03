package Units;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Environment;
import Engine.Vector;
import Graphics.ResourceLoader;
import core.IUnitBehaviour;
import core.IUnitMovement;
import core.IUnitRenderer;

public class Guard extends Unit {
	
	public final Object lock = new Object();

	public Vector sight1;
	public Vector sight2;
	public Path2D visionPoly;
	public double seeingAngle = 30;
	public double[][] path;
	public int currentPathPosition = 0;

	public boolean canSeePlayer = false;

	public Guard(Vector position, Dimension size, Image image, Environment env,
				 IUnitMovement<Guard> move, IUnitRenderer<Guard> render, IUnitBehaviour behave) {
		super(position, size, image, env, move, render, behave);
		super.speed = 2;

		sight1 = new Vector(facingDirection);
		sight1.rotate(seeingAngle/2);
		sight2 = new Vector(facingDirection);
		sight2.rotate(360-seeingAngle/2);

		sight1 = new Vector(0, 0);
		sight2 = new Vector(0, 0);

		visionPoly = new Path2D.Double();
		visionPoly.moveTo(centerposition.x, centerposition.y);
		visionPoly.lineTo(centerposition.x+sight1.x, centerposition.y+sight1.y);
		visionPoly.lineTo(centerposition.x+sight2.x, centerposition.y+sight2.y);

		path = new double[][] {
				{200, 200},
				{250, 150},
				{300, 150},
				{350, 200},
				{350, 600},
				{300, 650},
				{250, 650},
				{200, 600}
		};

		position.x = path[0][0];
		position.y = path[0][1];

		pathToString();
	}

	public void pathToString() {
		System.out.println();
		System.out.println(Arrays.deepToString(path));
	}

}
