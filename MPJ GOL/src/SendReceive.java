import mpi.*;

public class SendReceive {

	public static void main(String[] args) {
		MPI.Init(args);
		
		
		int id = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		int buf[] = new int[1];
		
		try { Thread.sleep((id+1)*100); }	// sleeping for printing purposes
		catch (InterruptedException e) {e.printStackTrace(); }
		
		System.out.println("I am process "+id+" of total "+size+" processes.");
		

		
		if (id == 0) {
			//	posiljam
			for (int i=1; i<size; i++) {
				buf[0] = i;
				MPI.COMM_WORLD.Send(buf, 0, 1, MPI.INT, i, 0);
			}
		} else {
			//	sprejemajo
			MPI.COMM_WORLD.Recv(buf, 0, 1, MPI.INT, 0, 0);
			System.out.println(buf[0]);
		}
		
		MPI.Finalize();
		
	}
}
