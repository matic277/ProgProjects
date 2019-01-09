package GOLporazdeljeno;

public class Buffer {
	
	int b[][];
	
	public Buffer (int size, int data) {
		b = new int[size][size];
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				b[i][j] = data;
	}

	public void print() {
		for (int i=0; i<b.length; i++) {
			for (int j=0; j<b[0].length; j++)
				System.out.print(b[i][j]+" ");
			System.out.println();
		}
	}
}
