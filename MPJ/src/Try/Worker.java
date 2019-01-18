package Try;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class Worker implements Runnable {

	private CyclicBarrier barrier;
	private int n;
	private int start, end;
	// private int timerIndex;

	private int check[][] = {
			{-1,-1},	//	levo zgoraj
			{-1, 0},	//	zgoraj
			{-1, 1},	//	desno zgoraj
			{ 0,-1},	//	levo
			{ 0, 1},	//	desno
			{ 1,-1},	//	levo spodaj
			{ 1, 0},	//	spodaj
			{ 1, 1} 	//	desno spodaj
	};
	
	public Worker (CyclicBarrier b, int s, int e, int pn, int pind){
		barrier = b;
		start = s;
		end = e;
		n = pn;
		System.out.println("start,end: "+s+", "+e);
	}
	
	@Override
	public void run(){
		System.out.println("run");
		
		while(true){
			simulate();
			
			try { barrier.await(); }
			catch (Exception e) { e.printStackTrace(); }
		}
	}
	
	public void simulate(){
		
		/*
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		//System.out.println("WORKER G : "+Painter.g.length+"x"+Painter.g[0].length+" n: "+this.n);
		//System.out.println("WORKER TG: "+Painter.tg.length+"x"+Painter.tg[0].length+" n: "+this.n);
		//System.out.println("start, end: "+start+", "+end);
		
		for(int i=start; i<end; i++)
			for(int j=0; j<n; j++)
				if (Painter.g[i][j] == 1)
					if (survival(i,j)) Painter.tg[i][j] = 1;
					else  Painter.tg[i][j] = 0;
				else
					if(multiplication(i,j)) Painter.tg[i][j] = 1;
					else Painter.tg[i][j] = 0;
	}
	
	//	Each cell with two or three neighbors survives.
	public boolean survival(int pi, int pj){
		if (pi == 0 || pj == 0 || pi == n-1 || pj == n-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++){
			ci = pi + check[i][0];
			cj = pj + check[i][1];
			
			if (Painter.g[ci][cj] == 1)counter++;
		}
		if (counter == 2 || counter == 3) return true;
		return false;
	}

	//	Each cell with three neighbors becomes populated.
	public boolean multiplication(int pi, int pj){
		if (pi == 0 || pj == 0 || pi == n-1 || pj == n-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++){
			ci = pi + check[i][0];
			cj = pj + check[i][1];
			
			if (Painter.g[ci][cj] == 1) counter++;
		}
		if (counter == 3) return true;
		return false;	
	}	

}
