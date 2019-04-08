package Main;

import java.awt.geom.Point2D;

import Obstacle.LineObstacle;
import Renderer.Painter;
import Subject.DNA;
import Subject.Vector;

public class Main {
	
	public static void main(String[] args) {
		Environment e = new Environment();
		
//		Thread t = new Thread() {
//			public void run() {
//				while(true) {
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println("running");
//				}
//			}
//		};
//		t.start();
		
		//e.start();
		
//		Point2D.Double p1 = new Point2D.Double(5.1, 5.0009);
//		Point2D.Double p2 = new Point2D.Double(5.1, 5.00098);
//		
//		LineObstacle l1 = new LineObstacle(p1, p2);
//		LineObstacle l2 = new LineObstacle(p1, p2);
//		
//		System.out.println(l1.equals(l2));
		
//		Vector s1[] = {new Vector(1, 1), new Vector(1, 1), new Vector(1, 1), new Vector(1, 1)};
//		Vector s2[] = {new Vector(2, 2), new Vector(2, 2), new Vector(2, 2), new Vector(2, 2)};
//		
//		DNA dna = DNA.combineDNA(new DNA(s1), new DNA(s2));
//		System.out.println(dna.toString());
		
//		DNA dna = new DNA(-1);
//		System.out.println(dna.toString());
//		
//		int size = Var.vectorLength;
//		
//		for (int i=0; i<dna.getSeq().length; i++) {
//			double len = dna.getSeq()[i].mag();
//			if (Math.abs(size - len) != 0) {
//				System.out.println(len);
//			}
//		}
		

	}
	
	// debugging purpose
	public static void ThreadCount() {
		System.out.println("# of threads: " + Thread.activeCount());
	}
}
