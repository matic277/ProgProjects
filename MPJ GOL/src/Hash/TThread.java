package Hash;

public class TThread implements Runnable {
	
	double t1, t2;
	
	public TThread() {
		t1 = System.currentTimeMillis();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public double getTime() {
		return (System.currentTimeMillis() - t1);
	}

}
