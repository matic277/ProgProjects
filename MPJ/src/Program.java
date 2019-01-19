import mpi.*;
public class Program {

	static int g[][] = new int [][]{
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

	private static int id;
	private static int size;
	
	
	private static int s, e, chunk;
	
	public static boolean test() {
		return false;
	}
	
	public static void main(String[] args) {
		//g = GridXX.initGrid();
		g = new int [][]{
				{0, 0, 0, 0},
				{1, 1, 1, 1},
				{2, 2, 2, 2},
				{3, 3, 3, 3},
				{4, 4, 4, 4},
				{5, 5, 5, 5},
				{6, 6, 6, 6},
				{7, 7, 7, 7},
				{8, 8, 8, 8}
		};
		
		g = new int [][]{
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
	
		MPI.Init(args);
		id = MPI.COMM_WORLD.Rank();
		size = MPI.COMM_WORLD.Size();
		
		/*
	 	WORKFLOW:
	 	Master has 2d grid. Converts it to 1d array and broadcasts to all slaves.
	 	Slaves all convert received 1d array to 2d grid and process it.
	 	Slaves then convert the processed 2d grid to 1d array, which is then
	 	gathered by master() - by chunks.
	 	Master converts the received 1d array to 2d grid and displays it.
	 	
	 	sendBuf			- 1d - the initial broadcasted array, slaves and master get this via broadcast
	 	recBuf			- 1d - gets gathered by master only, this is processed data that should be shown each iteration
	 	localGrid		- 2d - this is unprocessed grid, transformed by master and slaves from sendBuf
	 	processedGrid	- 2d - this is a processed grid, calculated from localGrid by slaves and master
	 	processedSendBuf- 1d - this is processed array, converted from processedGrid, ready to be sent by everyone
	 							and gathered by master
	 	recGrid			- 2d - grid converted from processedSendBuf, only master gets it - end of iteration
	 							convert this to 1d array = sendBuf, repeat this process
		*/
		
		int sendBuf[] = to1DArray(g);							 // 1d array, send this to other threads
		int recBuf[] = new int[sendBuf.length];			 		 // 1d array, this get received BY MASTER id=0 ONLY, this is a processed grid, gets converted to recGrid
		int localGrid[][] = new int[g.length][g[0].length];		 // 2d grid, this is converted from recBuf (1d array), ready to process
		int processedGrid[][] = new int[g.length][g[0].length];	 // 2d grid, processed locally
		int processedSendBuf[] = new int[g.length * g[0].length];// processed array, converted by each slave and received by master
		int recGrid[][] = new int[g.length][g[0].length];		 // 2d grid, gathered by master and ready to display, converted from 1dArray recBuf
		
		//
		
		int counter = 0;
		
		while (true) {
		
			MPI.COMM_WORLD.Bcast(sendBuf, 0, 1, MPI.INT, 0);
			
			try { Thread.sleep(id*150); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			
			if (id == 0) {
				System.out.println("master receiving");
				print(sendBuf);
			}
			
			//System.out.println("printing id: "+id);
			//print(sendBuf);
			
			localGrid = to2DArray(sendBuf);
			
			/*
			if (id == size-1) {
				System.out.println("LOCAL:");
				printGrid(localGrid);
			}
			*/
			
			if (id == 1) {
				System.out.println("LOCAL GRID ID = 1 BEFORE PROCESSING:");
				printGrid(localGrid);
			}
			
			processedGrid = simulate(localGrid, id);
			
			if (id == 1) {
				System.out.println("PROCESSED ID = 1 RESULT:");
				printGrid(processedGrid);
			}
			/*
			if (id == size-1) {
				System.out.println("\nPROCESSED:");
				printGrid(processedGrid);
			}
			*/
			
			processedSendBuf = to1DArray(processedGrid);
			
			//processedSendBuf[0] = id * 10 + 3;
			//processedSendBuf[1] = id * 10 + 4;
			// processedSendBuf[processedSendBuf.length -2] = -1;
			//processedSendBuf[0] = 98;
			
			// WORKING
			// MPI.COMM_WORLD.Gather(sendBuf, 0, sendBuf.length, MPI.INT, recBuf, 0, sendBuf.length, MPI.INT, 0);
			
	
			MPI.COMM_WORLD.Gather(
					processedSendBuf,
					
					s*localGrid[0].length,
					(id == size-1)? localGrid[0].length * chunk : localGrid[0].length * chunk,
				
					MPI.INT, recBuf,
					
					s*localGrid[0].length,
					(id == size-1)? localGrid[0].length * chunk : localGrid[0].length * chunk,
					//localGrid[0].length * chunk,
					
					MPI.INT, 0
			);
			
			
			try { Thread.sleep(id*15); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			
			//System.out.println("\n id: "+id+" offset, ammount: "+ s*localGrid[0].length +" "+ localGrid[0].length * chunk);
				
			
			if (id == 0) {
				System.out.println("Master gather: ");
				print(recBuf);
				
				recGrid = to2DArray(recBuf);
				
				printGrid(recGrid);
				
				sendBuf = recBuf;
				//sendBuf[0] = 99;
				//MPI.COMM_WORLD.Bcast(sendBuf, 0, 1, MPI.INT, 0);
				
				System.out.println("master sending:");
				print(sendBuf);
				
				counter++;
				
				
			}
			
			MPI.COMM_WORLD.Barrier();
		}
		
		//MPI.Finalize();
	}
	
	public static void printGrid(int a[][]) {
		System.out.println("Status:");
		for (int i=0; i<a.length; i++) {
			System.out.print(i+"  - ");
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
	
	public static int[][] to2DArray(int d[]) {
		int w = g[0].length;
		int h = g.length;
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
	
	public static int[][] simulate(int a[][], int idd) {
		int b[][] = new int[a.length][a[0].length];
		
		// determine the chunk, based on id
		int height = g.length;
		chunk = height / size;
		s = chunk * idd;
		e = s + chunk;
		
		// last chunk may be bigger
		if (idd == size) e = height;
		
		System.out.println(idd + ": " + s + " " + e + " " + chunk);
		
		// actual simulation
		for(int i=s; i<e; i++)
			for(int j=0; j<a[i].length; j++) {
				if (applyRules(i, j, a)) b[i][j] = 1;
				//else b[i][j] = 0;
				//if (id == 0) System.out.println("processing: "+i+" "+j); 
			}
		return b;
	}
	
	public static boolean applyRules(int pi, int pj, int a[][]) {
		//System.out.println("@index: "+pi+" "+pj);
//		if (id == 1) {
//			System.out.println("checking "+pi+" "+pj);
//		}
		if (pi == 0 || pj == 0 || pi == a.length-1 || pj == a[0].length-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++){
			ci = pi + check[i][0];
			cj = pj + check[i][1];
			
			if (a[ci][cj] == 1) counter++;
		}
		if (a[pi][pj] == 0 &&  counter == 3) { /*if (id == 1) { System.out.println("ID=1, 3 neighbours at: "+pi+" "+pj); }*/ return true; }
		if (a[pi][pj] == 1 && (counter == 3 || counter == 2)) return true;
		return false;	
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
