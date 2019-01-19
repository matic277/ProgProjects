import mpi.MPI;

public class Program2 {
	
	public static void main(String[] args) {

		int g[][] = new int [][]{
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
		};
		
		int tg[][] = new int [][]{
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1},
			{2, 2, 2, 2, 2, 2, 2, 2, 2},
			{2, 2, 2, 2, 2, 2, 2, 2, 2},
			{2, 2, 2, 2, 2, 2, 2, 2, 2},
			{3, 3, 3, 3, 3, 3, 3, 3, 3},
			{3, 3, 3, 3, 3, 3, 3, 3, 3},
			{3, 3, 3, 3, 3, 3, 3, 3, 3},
		};

	
		MPI.Init(args);
		
		/*
	 	WORKFLOW:
	 	Master has 2d grid, initially . Converts it to 1d array and broadcasts to all slaves (and himself).
	 	Slaves (and master) all convert received 1d array to 2d grid and process it.
	 	Slaves then convert the processed 2d grid to 1d array, which is then
	 	gathered by master - by chunks.
	 	Master converts the received 1d array to 2d grid and displays it.
	 	
	 	sendBuf			- 1d - the initial broadcasted array, slaves and master get this via broadcast
	 	localGrid		- 2d - this is unprocessed grid, transformed by master and slaves from sendBuf
	 	processedGrid	- 2d - this is a processed grid, calculated from localGrid by slaves and master
	 	processedSendBuf- 1d - this is processed array, converted from processedGrid, ready to be sent by everyone
	 						   and gathered by master
	 	recBuf			- 1d - gets gathered by master only, this is processed data that should be shown each iteration
	 	recGrid			- 2d - grid converted from processedSendBuf, only master gets it - end of iteration
	 						   convert this to 1d array = sendBuf, repeat this process
	 						   
	 	NOTE: the height of the initial grid should be divisible by size
	 	
		*/
		
		int id   = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		
		int h = g.length;
		int w = g[0].length;
		
		int sendBuf[]			= new int[h * w];
		int recBuf[]			= new int[h * w];
		int localGrid[][]	 	= new int[h] [w];
		int processedGrid[][]	= new int[h] [w];
		int processedSendBuf[]	= new int[h * w];
		int recGrid[][]			= new int[h] [w];
		
		int s, e, chunk;
		
		// determine the chunk, based on id
		chunk = h / size;
		s = chunk * id;
		e = s + chunk;
		
		try { Thread.sleep(id*150); } 
		catch (InterruptedException err) { err.printStackTrace(); }
		
		int counter = 0;
		
		// assign g (initial state of grid) to sendBuf
		sendBuf = to1DArray(g);
		
		while (true) {
			
			System.out.println("starting simulation");
		
			MPI.COMM_WORLD.Bcast(sendBuf, 0, 1, MPI.INT, 0);
			
			try { Thread.sleep(id*150); } 
			catch (InterruptedException err) { err.printStackTrace(); }
			

			localGrid = to2DArray(sendBuf, h, w);
			processedGrid = simulate(localGrid, s, e, id);
			processedSendBuf = to1DArray(processedGrid);
			
			
			// prints
			{
				//System.out.println("id " + id + " -- s, e = "+start+", "+end);
				System.out.println("Received grid id = " + id);
				printGrid(localGrid);
				//System.out.println("Processed grid id = " + id);
				//printGrid(processedGrid);
				//System.out.println("\n");
			}
			
	
			MPI.COMM_WORLD.Gather(
					// sending:
					processedSendBuf,
					
					s * w,
					w * chunk,
					
					// received:
					MPI.INT, recBuf,
					
					s * w,
					w * chunk,

					MPI.INT, 0
			);
			
			
			try { Thread.sleep(id*15); } 
			catch (InterruptedException err) { err.printStackTrace(); }
					
			
			if (id == 0) {
				// convert recBuf 1d array to 2d grid
				recGrid = to2DArray(recBuf, h, w);
				
				System.out.println("Iteration " + counter + " is over...");
				System.out.println("Master gathered: ");
				printGrid(recGrid);
				
				// set sendBuf to this iterations processed array
				sendBuf = recBuf;

				//System.out.println("Master is now bcasting:");
				//printGrid(recGrid);
				
				counter++;
			}
			// ??
			// sendBuf = recBuf;
			MPI.COMM_WORLD.Barrier();
		}
	}
	
	public static void printGrid(int a[][]) {
		for (int i=0; i<a.length; i++) {
			if (i > 9) System.out.print(i+" - ");
			else System.out.print(i+"  - ");
			for (int j=0; j<a[i].length; j++) System.out.print(a[i][j]+" ");
			System.out.println();
		}
		System.out.println();
	}
	
	public static int[] to1DArray(int d[][]) {
		int a[] = new int[d.length * d[0].length];
		for (int i=0, ii=0; i<d.length; i++) 
			for (int j=0; j<d[i].length; j++, ii++) 
				a[ii] = d[i][j];
		return a;
	}
	
	public static int[][] to2DArray(int d[], int h, int w) {
		int a[][] = new int[h][w];
		for (int i=0, ii=0; i<a.length; i++) 
			for (int j=0; j<a[i].length; j++, ii++) 
				a[i][j] = d[ii];
		return a;
	}
	
	public static void print(int d[]) {
		for (int i=0; i<d.length; i++) System.out.print(d[i]+ " ");
		System.out.println();
	}
	
	public static int[][] simulate(int a[][], int s, int e, int id) {
		int b[][] = new int[a.length][a[0].length];
		for(int i=s; i<e; i++)
			for(int j=0; j<a[i].length; j++) 
				if (applyRules(i, j, a)) 
					b[i][j] = 1;
		return b;
	}
	
	public static boolean applyRules(int pi, int pj, int a[][]) {
		if (pi == 0 || pj == 0 || pi == a.length-1 || pj == a[0].length-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++){
			ci = pi + check[i][0];
			cj = pj + check[i][1];
			
			if (a[ci][cj] == 1) counter++;
		}
		if (a[pi][pj] == 0 &&  counter == 3) { return true; }
		if (a[pi][pj] == 1 && (counter == 3 || counter == 2)) return true;
		return false;	
	}
	
	// testing simulation
	public static int[][] simulate2(int a[][], int s, int e, int id) {
		int b[][] = new int[a.length][a[0].length];

		// last chunk may be bigger
		// if (idd == size) e = height;
		
		//System.out.println(idd + ": " + s + " " + e + " " + chunk);
		
		// actual simulation
		for(int i=s; i<e; i++)
			for(int j=0; j<a[i].length; j++)
				 b[i][j] = a[i][j] + id;
		
		return b;
	}
	
	static int check[][] = {
			{-1,-1},	//	levo zgoraj
			{-1, 0},	//	zgoraj
			{-1, 1},	//	desno zgoraj
			{ 0,-1},	//	levo
			{ 0, 1},	//	desno
			{ 1,-1},	//	levo spodaj
			{ 1, 0},	//	spodaj
			{ 1, 1} 	//	desno spodaj
	};


}
