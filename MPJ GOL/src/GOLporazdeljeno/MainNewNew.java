package GOLporazdeljeno;

import mpi.MPI;

public class MainNewNew {
	
	static int gdata[][] = {
			{0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0},
			{0,0,1,0,0,0,0,0},
			{0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0},
			{0,0,0,0,0,1,0,0},
			{0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0},
	};
	static int ldata[][];
	
	static int tgdata[][] = {
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
	};
	
	static int id;
	static int size;
	static int chunk;
	
	public static void main(String[] args) {
		MPI.Init(args);
		id = MPI.COMM_WORLD.Rank();			//	moj id
		size = MPI.COMM_WORLD.Size();	//	stevilo workerjeu
		chunk = gdata.length / size;
		ldata = new int[chunk][gdata[0].length];
		
		if (id == 0) {
			System.out.println("--------MASTER ID "+id+"-------------");
			
			System.out.println("n of slaves: "+size);
			printgData();

			System.out.println("--------------------------------");
		}
		
		
		MPI.COMM_WORLD.Scatter(gdata, 0, 0, MPI.OBJECT, ldata, id*chunk, chunk, MPI.OBJECT, 0);
		
		try { Thread.sleep((id+1)*100); }
		catch (InterruptedException e) {e.printStackTrace(); }
		
		//System.out.println("data print from "+id);
		//System.out.println("id*chunk, chunk: "+id*chunk+", "+chunk);
		
		/*
		// set local data
		ldata = new int[chunk][gdata[0].length];
		for (int i=id*chunk, ii=0; i<(id*chunk+chunk); i++, ii++) {
			for (int j=0; j<ldata[0].length; j++) {
				//System.out.println("gdata["+i+"]["+j+"] = "+gdata[i][j]);
				ldata[ii][j] = gdata[i][j];
			}
		}
		*/
		
		// Status print
		if (id == 0) System.out.println("MASTER id "+id+", ("+id*chunk+", "+(id*chunk+chunk)+"):");
		else System.out.println("SLAVE id "+id+", ("+id*chunk+", "+(id*chunk+chunk)+"):");
		simulate();
		printlData();
		printgData();
		
		
		
		
		
		
		
		MPI.COMM_WORLD.Gather(ldata, 0, 0, MPI.OBJECT, gdata, 0, 1, MPI.OBJECT, 0);
		
		if (id == 0) {
			System.out.println("master gathering");
			
			printgData();
			
		}
		
		MPI.Finalize();
	}
	
	public static void simulate() {
		//int s = id*chunk;
		//int e = chunk;
		/*
		for(int i=0; i<ldata.length; i++)
			for(int j=0; j<ldata[0].length; j++) {
				if (checker(i, j)) tgdata[i][j] = 1;
				else tgdata[i][j] = 0;
			}
		*/
		ldata[0][0] = 1+id;
	}
	
	public static boolean checker(int pi, int pj) {
		if (pi == 0 || pj == 0 || pi == gdata.length-1 || pj == gdata[0].length-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++){
			ci = pi + check[i][0];
			cj = pj + check[i][1];
			
			if (gdata[ci][cj] == 1) counter++;
		}
		if (gdata[pi][pj] == 0 &&  counter == 3) return true;
		if (gdata[pi][pj] == 1 && (counter == 3 || counter == 2)) return true;
		return false;	
	}
	static int check[][] = {
			{-1,-1},	//	top left
			{-1, 0},	//	top
			{-1, 1},	//	top right
			{ 0,-1},	//	left
			{ 0, 1},	//	right
			{ 1,-1},	//	bottom left
			{ 1, 0},	//	bottom
			{ 1, 1} 	//	bottom right
	};
	
	public static void printgData() {
		System.out.println("gData:");
		for(int i=0; i<gdata.length; i++) {
			for(int j=0; j<gdata[0].length; j++) 
				System.out.print(gdata[i][j]+", ");
			System.out.println();
		}
		System.out.println("---------");
	}
	public static void printtgData() {
		System.out.println("tgData:");
		for(int i=0; i<tgdata.length; i++) {
			for(int j=0; j<tgdata[0].length; j++) 
				System.out.print(tgdata[i][j]+", ");
			System.out.println();
		}
		System.out.println("---------");
	}
	public static void printlData() {
		System.out.println("lData:");
		for(int i=0; i<ldata.length; i++) {
			for(int j=0; j<ldata[0].length; j++) 
				System.out.print(ldata[i][j]+", ");
			System.out.println();
		}
		System.out.println("---------");
	}

}
