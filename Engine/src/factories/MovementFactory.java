package factories;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import Engine.Vector;
import Units.Player;
import Units.Wall;
import core.IUnitMovement;
import implementation.EnemyMovement;
import implementation.SimpleLinearMovement;

public class MovementFactory {
	
	Player player;
	ConcurrentLinkedQueue<Wall> walls;
	
	public MovementFactory(ConcurrentLinkedQueue<Wall> walls, Player player) {
		this.player = player;
		this.walls = walls;
	}
	
	public EnemyMovement getEnemeyMovement() {
		return new EnemyMovement(walls, player);
	}
	
	public SimpleLinearMovement getSimpleLinearMovement(Vector direction) {
		return new SimpleLinearMovement(direction);
	}

}
