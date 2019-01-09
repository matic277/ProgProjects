package GOLporazdeljeno;

import mpi.MPI;

public class MainNew {
	
	static int gData[][] = Data.x;
	static int size = 4;
	static int chunk = gData.length / size;
	static int id;
	static int lData[][] = new int[chunk][gData[0].length];
	
	static int rec[][][] = new int[gData.length][gData[0].length][size];

	public static void main(String[] args) {
		MPI.Init(args);
		id = MPI.COMM_WORLD.Rank();			//	moj id
		int size = MPI.COMM_WORLD.Size();	//	stevilo workerjeu
		
		
		if (id == 0) {
			System.out.println("--------MASTER ID "+id+"-------------");
			
			System.out.println("n of slaves: "+size);
			printgData();

			System.out.println("--------------------------------");
		}
		
		

		//int chunk = gData.length / size;
		
		MPI.COMM_WORLD.Scatter(gData, 0, 1, MPI.OBJECT, lData, id*chunk, chunk, MPI.OBJECT, 0);
		
		try { Thread.sleep((id+1)*100); }
		catch (InterruptedException e) {e.printStackTrace(); }
		
		//System.out.println("data print from "+id);
		//System.out.println("id*chunk, chunk: "+id*chunk+", "+chunk);
		
		
		if (id != 0) {
			System.out.println("Slave id "+id+", ("+id*chunk+", "+chunk+"):");
			simulate();
			printlData();
			printgData();
		}
		
		
		
		
		
		
		//MPI.COMM_WORLD.Gather(data, 0, 1, MPI.OBJECT, newData, 0, 1, MPI.OBJECT, 0);
		MPI.COMM_WORLD.Gather(lData, 0, 1, MPI.OBJECT, rec, 0, 1, MPI.OBJECT, 0);
		
		if (id == 0) {
			System.out.println("master gathering");
			
			printrecdata();
			
		}
		
		MPI.Finalize();
	}
	
	public static void printrecdata() {
		for (int i=0; i<size; i++) {
			System.out.println("new \n");
			for (int j=0; j<rec.length; j++) {
				for (int k=0; k<rec[0].length; k++)
					System.out.print(rec[i][j][k]+ " ");
				System.out.println();
			}
		}
	}
	
	public static void simulate() {
		System.out.println("simulating");
		for(int i=0; i<lData.length; i++)
			for(int j=0; j<lData[0].length; j++) {
				if (lData[i][j] == 1) lData[i][j] = 5;
			}
	}
	
	public static void printgData() {
		System.out.println("gData:");
		for(int i=0; i<gData.length; i++) {
			for(int j=0; j<gData[0].length; j++) 
				System.out.print(gData[i][j]+", ");
			System.out.println();
		}
		System.out.println("---------");
	}
	public static void printlData() {
		System.out.println("lData:");
		for(int i=0; i<lData.length; i++) {
			for(int j=0; j<lData[0].length; j++) 
				System.out.print(lData[i][j]+", ");
			System.out.println();
		}
		System.out.println("---------");
	}

}
