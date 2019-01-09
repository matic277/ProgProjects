package Try;

public class Barrier implements Runnable {
	
	Painter ref;
	static boolean reset;
	
	
	public Barrier(Painter pref) {
		ref = pref;
		reset = false;
		ref.t1 = System.currentTimeMillis();
	}

	@Override
	public void run() {
		
		do {
			// wait for pause to stop, but reset if clicked
			if (reset) {
				System.out.println("RESETTING");
				switch(Painter.gridSize) {
					case "X":
						GridX.initGrid();
						break;
					case "XX":
						GridXX.initGrid();
						break;
					case "XXX":
						GridXXX.initGrid();
						break;
					case "XXXX":
						GridXXXX.initGrid();
					break;
				}
				Painter.tg = Painter.g;
				reset = false;
				ref.repaintPanel();
			}
		} while (!ref.simulate.get());
		
		
			
		Painter.g = Painter.tg;
		Painter.tg = new int[Painter.n][Painter.n];
		
		
		
		Painter.t2 = System.currentTimeMillis();
		//Painter.t1 = System.currentTimeMillis();
		
		// we need to wait a little bit before repainting, if needed
		double t = Painter.t2 - Painter.t1;
		double wt = Painter.speed - t;				
		
		if (wt > 0) {
			System.out.println("sleeping for: "+(int)wt);
			try { Thread.sleep((int)wt); } 
			catch (InterruptedException e) { e.printStackTrace(); }
		}
		
		ref.t1 = System.currentTimeMillis();
		ref.repaintPanel();
		
	}
	

}
