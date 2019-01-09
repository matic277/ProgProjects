import java.util.*;
import mpi.*;

public class MaxMPII {
	private static int id;
	private static int size;
	private static final int n = 20;
	private static int[] a = new int[n];
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MPI.Init(args);
		id = MPI.COMM_WORLD.Rank();
		size = MPI.COMM_WORLD.Size();
		System.out.println("jaz sem "+id+" od "+size);
		
		Random rand = new Random();
		if (id == 0)
		{
			for (int i = 0; i < a.length; i++)
				a[i] = rand.nextInt(20);
		}	

		int sendCount = n / size;
		int[] recvBuf = new int[sendCount];
		MPI.COMM_WORLD.Scatter(a, 0, sendCount, MPI.INT, recvBuf, 0, sendCount, MPI.INT, 0);

		int[] max = new int[1];
		max[0] = recvBuf[0];
		for (int i=1; i<recvBuf.length; i++)
		{
			max[0] = (recvBuf[i] > max[0] ? recvBuf[i] : max[0]);
		}

		recvBuf = new int[size];
		MPI.COMM_WORLD.Gather(max, 0, 1, MPI.INT, recvBuf, 0, 1, MPI.INT, 0);

		max[0] = recvBuf[0];
		for (int i=1; i<recvBuf.length; i++)
		{
			max[0] = (recvBuf[i] > max[0] ? recvBuf[i] : max[0]);
		}

		if (id == 0)
			System.out.println(max[0]);
		
		MPI.Finalize();
	}


}