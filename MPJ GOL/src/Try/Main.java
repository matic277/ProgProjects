package Try;
import java.awt.Graphics;

import java.util.concurrent.CyclicBarrier;

import javax.swing.JPanel;


public class Main{
	
	static int nthreads = 1;
	static Painter ref;
	static CyclicBarrier barrier;
	static boolean reset = false;
	
	public static void main(String[] args) {
		//Painter p = new Painter(40, true, true);
		new Menu();
		System.out.println("Try");
	}
	
	public static void initThreads () {

		barrier = new CyclicBarrier(nthreads, new Barrier(ref));
		
		int chunk = Painter.n / nthreads ;
		int lastchunk = 0;
		for (int i=0; i<nthreads-1; i++, lastchunk = i*chunk) {
			new Thread(new Worker(barrier, i*chunk, i*chunk+chunk, Painter.n, i)).start();
		}
		new Thread(new Worker(barrier, lastchunk, Painter.n, Painter.n, nthreads-1)).start();
		
		System.out.println("threads: "+nthreads);
	}
	

}
