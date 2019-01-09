import java.net.MalformedURLException;
import java.net.URL;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

import Try.Painter;

import java.util.*;
import mpi.*;

public class MaxMPI {
	
	static int g[][];

	private static int id;
	private static int size;
	
	
	private static int s, e, chunk;
	
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
			
			processedGrid = simulate(localGrid);
			
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
				sendBuf[0] = 99;
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
	
	public static int[][] simulate(int a[][]) {
		int b[][] = new int[a.length][a[0].length];
		
		// determine the chunk, based on id
		int height = g.length;
		chunk = height / size;
		s = chunk * id;
		e = s + chunk;
		
		// last chunk may be bigger
		if (id == size) e = height;
		
		System.out.println(id + ": " + s + " " + e + " " + chunk);
		
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

	
		
		
	/*

	static int n = 20;
	static int d[] = new int[n];	//	data
	public static void main(String[] args) {
		MPI.Init(args);
		int id = MPI.COMM_WORLD.Rank();		//	moj id
		int size = MPI.COMM_WORLD.Size();	//	koliko nas je, workerjeu
		
		Random r = new Random();
		
		if (id == 0) {
			String s = "";
			//	jst sem master, jst redistribuiram podatke
			for (int i=0; i<n; i++) {
				d[i] = r.nextInt(100);
				s += d[i] + " "; 
			}
			System.out.println("Master id "+id+": "+s);
		}
		
		int amount = n / size;				//	koliko dela dobi vsak worker
		int recBuf[] = new int[amount];		//	buffer za sprejem
		
		MPI.COMM_WORLD.Scatter(d, 0, amount, MPI.INT, recBuf, 0, amount, MPI.INT, 0);	//	vsem posljemo podatke
		
		//	najdemo nas max, potem ga posljemo
		int max[] = new int[1];
		max[0] = recBuf[0];
		String s = "";
		for (int i=0; i<recBuf.length; i++) {
			max[0] = (recBuf[i] > max[0] ? recBuf[i] : max[0]);	//	isto kot if
			s += recBuf[i] + " ";
		}
		System.out.println("ID: "+id+": "+s);
		recBuf = new int[size];
		MPI.COMM_WORLD.Gather(max, 0, 1, MPI.INT, recBuf, 0, 1, MPI.INT, 0);			// samo master z idjem 0 bo izvedel gather
																						// id = 0; zadnji parameter
		
		int globalMax = recBuf[0];
		String g = "";
		for (int i=0; i<recBuf.length; i++) {
			if (recBuf[i] > globalMax) globalMax = recBuf[i];
			g += recBuf[i] + " ";
		}
		
		System.out.println("moj maks, id="+id+" je: "+max[0]);
		
		if (id == 0) {
			System.out.println("Global max: "+globalMax+", od: ["+g+"]");
		}
		MPI.Finalize();		//	pocistimo okolje
	}
	*/
}
