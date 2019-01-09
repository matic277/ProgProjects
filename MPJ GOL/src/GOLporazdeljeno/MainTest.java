package GOLporazdeljeno;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import mpi.MPI;

public class MainTest {
	
	
	
	static JPanel p;
	
	public static void main(String[] args) {
		MPI.Init(args);
		int id = MPI.COMM_WORLD.Rank();		//	moj id
		int size = MPI.COMM_WORLD.Size();	//	stevilo workerjeu
		
		// data[][] should be size [n][n]
		// n should be divisible by size
		
		int data[] = {1,2,3,4,5,6,7,8,9,10,11,12};
		int newData[] = new int[3];
		
		
		if (id == 0) {
			System.out.println("--------MASTER ID " + id + "-------------");
			
			System.out.println("number of slaves: " + (size-1));
			//createWindow();

			System.out.println("--------------------------------");
		}
		
		int chunk = data.length / size; System.out.println("chunk size: " + chunk);
		
		// receive buffer
		int rec[] = new int[10];
		
		//MPI.COMM_WORLD.Bcast(data, 0, 0, MPI.OBJECT, 0);	//	vsem posljemo iste podatke
		
		MPI.COMM_WORLD.Scatter(data, id*chunk, 3, MPI.INT, rec, id*chunk, 3, MPI.INT, 0);
		
		try { Thread.sleep((id+1)*100); }	// sleeping for printing purposes
		catch (InterruptedException e) {e.printStackTrace(); }
		
		
		
		System.out.println("Print from " + id + ":");
		System.out.println("Data received: (data)");
		printData(data);
		System.out.println("Simulated: (newData)");
		newData = simulate(data, chunk, id);
		printData(newData);
		
		System.out.println(" ");
		
		
		
		MPI.COMM_WORLD.Gather(newData, id*chunk, 0, MPI.INT, rec, id*chunk, 0, MPI.INT, 0);
		
		
		if (id == 0) {
			System.out.println("master gathering:");
			printGatheredData(rec);
			
			System.out.println("after combining:");
			//int combined[][] = combineGatheredData(rec, size);
			printData(rec);
		}
		
		MPI.Finalize();
	}
	
	public static void printGatheredData(int rec[]) {
		for (int i=0; i<rec.length; i++) System.out.print(rec[i]+" ");
	}
	public static void printData(int data[]) {
		for(int i=0; i<data.length; i++) System.out.print(data[i] + " ");

	}
	
	public static int[][] combineGatheredData(int data[][][], int n) {
		int combined[][] = new int[data[0].length][data[0][0].length];
		int l = combined.length / n;
		int s = 0;
		for (int k=0; k<n; k++) {
			for(int i=s; i<s+l; i++) 
				for(int j=0; j<combined[0].length; j++) 
					combined[i][j] = data[k][i][j];
			
			s += l;
		}
		return combined;
	}
	
	public static void repaintPanel() {
		try { Thread.sleep(16); }
		catch (InterruptedException e) { e.printStackTrace(); }
		p.repaint();
	}
	
	public static int[] simulate(int data[], int chunk, int id) {
		int s = id * chunk;
		int e = s + chunk;
		int n = data.length;
		int newData[] = new int[data.length];
		
		System.out.println("data -> newData (startindex, endindex): ("+s+", "+e+")");
		
		for(int i=0; i<data.length; i++)
			if (i>=s && i<e) newData[i] = id;

		return newData;
	}

	
	//	Each cell with two or three neighbors survives.
	public static boolean survival (int pi, int pj, int n, int data[][]) {
		if (pi == 0 || pj == 0 || pi == n-1 || pj == n-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++) {
			ci = pi + check[i][0];
			cj = pj + check[i][1];	
			
			if (data[ci][cj] == 1) counter++;
		}
		if (counter == 2 || counter == 3) return true; return false;
	}
	//	Each cell with three neighbors becomes populated.
	public static boolean multiplication (int pi, int pj, int n, int data[][]) {
		if (pi == 0 || pj == 0 || pi == n-1 || pj == n-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++){
			ci = pi + check[i][0];
			cj = pj + check[i][1];
			
			if (data[ci][cj] == 1) counter++;
		}
		if (counter == 3) return true; return false;
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
}
