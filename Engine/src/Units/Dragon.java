package Units;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import Engine.Vector;

public class Dragon /* extends Unit*/ {
	
//	//Part[] parts;
//	int length = 30;
//	int partSpacing = 20;
//
//	CopyOnWriteArrayList<Part> parts;
//
//	public Dragon(Vector position, Dimension size, int heightLevel, Image headImage, Image bodyImage, Image tailImage) {
//		super(position, size, null);
//
////		parts = new Part[length];
////
////		parts[0] = new Part(new Vector(500, 500), null, headImage, heightLevel);
////		for (int i=1; i<parts.length-1; i++) parts[i] = new Part(new Vector(500+i*partSpacing, 500), null, bodyImage, heightLevel);
////		parts[parts.length-1] = new Part(new Vector(500+(length-1)*partSpacing, 500), null, tailImage, heightLevel);
//
//		parts = new CopyOnWriteArrayList<Part>();
//		int startingPointX = Engine.Engine.bounds.width;
//
//		parts.add(new Part(new Vector(startingPointX, heightLevel), null, headImage, heightLevel));
//		for (int i=0; i<length-2; i++) parts.add(new Part(new Vector(startingPointX+i*partSpacing, heightLevel), null, bodyImage, heightLevel));
//		parts.add(new Part(new Vector(startingPointX+(length-1)*partSpacing, heightLevel), null, tailImage, heightLevel));
//
//	}
//
//	@Override
//	public void move() {
////		for (int i=0; i<parts.length; i++) parts[i].move();
//		for (int i=0; i<parts.size(); i++) parts.get(i).move();
//
//		if (isOutOfBounds()) resetPosition();
//	}
//
//	@Override
//	public void draw(Graphics2D g) {
////		for (int i=0; i<parts.length; i++) parts[i].draw(g);
//		for (int i=0; i<parts.size(); i++) parts.get(i).draw(g);
//	}
//
//	@Override
//	public boolean isOutOfBounds() {
////		for (int i=0; i<parts.length; i++) {
////			if (!parts[i].isOutOfBounds()) return false;
////		}
////		return true;
//		for (int i=0; i<parts.size(); i++) {
//			if (!parts.get(i).isOutOfBounds()) return false;
//		}
//		return true;
//	}
//
//	public void resetPosition() {
//		for (int i=0; i<parts.size(); i++) {
////		for (int i=0; i<parts.length; i++) {
//			// delicate repositioning, if x is too
//			// far to the right, it'll just stay
//			// out of bounds, looping in out of bounds
//			// and resetting itself out of bounds
//			// +30 is just right, +50 bugs out
//			//parts[i].updatePosition(new Vector(Engine.Engine.bounds.getWidth() + 30 + (length*partSpacing), 0));
//			parts.get(i).updatePosition(new Vector(Engine.Engine.bounds.getWidth() + (parts.size()*partSpacing), 0));
//		}
//	}
//
//	public void checkCollision(ConcurrentLinkedQueue<Bullet> bullets, ConcurrentLinkedQueue<Asteroid> asteroids) {
//		if (bullets.isEmpty()) return;
//		Bullet[] blts = bullets.toArray(new Bullet[1]);
//
//		int collisionIndx = -1;
//		loop:
//		for (int i=0; i<blts.length; i++) {
//			for (int j=0; j<parts.size(); j++) {
//				if (parts.get(j).checkCollision(blts[i])) {
//					collisionIndx = j;
//					bullets.remove(blts[i]);
//					break loop;
//				}
//			}
//		}
//		if (collisionIndx == -1) return;
//
//		//System.out.println("Collision at" + collisionIndx);
//
//		ArrayList<Part> newParts = new ArrayList<Part>();
//		for (int i=collisionIndx; i<parts.size(); i++) {
//			asteroids.add(new Asteroid(
//				parts.get(i).getLocation(),
//				new Vector(),
//				null,
//				parts.get(i).getUnitImage()
//			));
//		}
//		for (int i=0; i<collisionIndx; i++) {
//			newParts.add(parts.get(i));
//		}
//		length = newParts.size();
//		parts.clear();
//		parts.addAll(newParts);
//	}
//
//}

static class Part /* extends Unit */ {
	
//	double speed = 2;
//	Vector direction;
//
//	double heightLevel;
//
//	public Part(Vector position, Dimension size, Image image, int heightLevel) {
//		super(position, size, image);
//		this.heightLevel = heightLevel;
//		direction = new Vector(-speed, 0);
//	}
//
//	@Override
//	public void move() {
//		position.y = 80 * Math.sin(Math.toDegrees(position.x/4500)) + heightLevel;
//		updatePosition(direction);
//	}
//
//	@Override
//	public void draw(Graphics2D g) {
//		rotateAndDraw(g);
//
//	}
//
}}
