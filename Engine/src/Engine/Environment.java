package Engine;

import Units.*;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Environment {

    public Player player;

    public Dragon dragon;

    public ConcurrentLinkedQueue<Missile> missiles;
    public ConcurrentLinkedQueue<Unit> bullets;

    public ConcurrentLinkedQueue<Unit> dummyUnits;
    public ConcurrentLinkedQueue<Wall> walls;

    public ConcurrentLinkedQueue<Asteroid> asteroids;

    public ConcurrentLinkedQueue<Unit> enemies;
    public ConcurrentLinkedQueue<Guard> guards;

    public Environment() {
        missiles = new ConcurrentLinkedQueue<Missile>();
        bullets = new ConcurrentLinkedQueue<Unit>();

        dummyUnits = new ConcurrentLinkedQueue<Unit>();

        asteroids = new ConcurrentLinkedQueue<Asteroid>();

        enemies = new ConcurrentLinkedQueue<Unit>();
        guards = new ConcurrentLinkedQueue<Guard>();

        walls = new ConcurrentLinkedQueue<Wall>();
    }
}
