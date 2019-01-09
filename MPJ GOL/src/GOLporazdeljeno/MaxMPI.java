package GOLporazdeljeno;
import java.util.Random;
import mpi.MPI;

public class MaxMPI {

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
			for (int i=0; i<size; i++) {
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
		
		if (id == 0) {
			System.out.println("Global max: "+globalMax+", od: ["+s+"]");
		}
		MPI.Finalize();		//	pocistimo okolje
	}
}
