package GOLporazdeljeno;

import mpi.MPI;

public class MainChunks {
	

	static int g[][] = {
			{0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0},
			{0,0,1,0,0,0,0,0},
			{0,0,1,0,0,0,0,0},
			{0,0,0,0,0,1,0,0},
			{0,0,0,0,0,1,0,0},
			{0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0},
	};
	
	
	static int chunks[][];
	static int id, size, chunk;
	
	public static void main(String[] args) {
		MPI.Init(args);
		id = MPI.COMM_WORLD.Rank();			//	moj id
		size = MPI.COMM_WORLD.Size();		//	stevilo workerjeu
		chunk = g.length / size;

		
		Buffer send[] = new Buffer[size];
		send = splitData();
		
		
		Buffer rec[] = new Buffer[1];
		
		MPI.COMM_WORLD.Scatter(send, 0, 0, MPI.OBJECT, rec, 0, 1, MPI.OBJECT, 0);
		
		// sleep for printing purposes
		try { Thread.sleep((id+1)*100); }
		catch (InterruptedException e) {e.printStackTrace(); }
		
		
		if(id != 0) {
			System.out.println("--Slave "+id+"--");
			rec[0].print();
		} else {
			System.out.println("--Mster "+id+"--");
			rec[0].print();
		}
		
		Buffer masterRec[] = new Buffer[size];

		MPI.COMM_WORLD.Gather(rec, 0, 1, MPI.OBJECT, masterRec, 0, 0, MPI.OBJECT, 0);
		
		//MPI.COMM_WORLD.Barrier();
		if (id == 0) {
			System.out.println("master gathering");
			printmasterbuf(masterRec);
			
		}
		
		MPI.Finalize();
	}
	
	public static Buffer[] splitData() {
		Buffer toSend[] = new Buffer[size];
		for (int i=0; i<toSend.length; i++) toSend[i] = new Buffer(chunk, id+1);
		return toSend;
	}
	
	public static void printg() {
		System.out.println("printing g:");
		for (int i=0; i<g.length; i++) {
			for (int j=0; j<g[0].length; j++)
				System.out.print(g[i][j]+" ");
			System.out.println();
		}
		
	}
	public static void printrec() {
		System.out.println("printing rec:");
		for (int i=0; i<g.length; i++) {
			for (int j=0; j<g[0].length; j++)
				System.out.print(g[i][j]+" ");
			System.out.println();
		}
	}
	public static void printmasterbuf(Buffer[] masterRec) {
		System.out.println("Master print masterRec:");
		for (int i=0; i<masterRec.length; i++) if (masterRec[i] != null) masterRec[i].print();
	}

	
}
