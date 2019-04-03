import java.util.Random;

public class DNA {
	
	Vector seq[];

	public DNA(int size) {
		if (size == -1)  size = Var.DnaLength;
		
		seq = new Vector[size];
		
		Random r = new Random();
		
		seq[0] = new Vector(Var.vectorMinValue, Var.vectorMaxValue);
		for (int i=1; i<size; i++) {
			double x = r.nextInt(4 + 4) - 3.5;
			double y = r.nextInt(4 + 4) - 4;
			seq[i] = new Vector(seq[i-1].x+x, seq[i-1].y+y);
		}
		
//		for (int i=0; i<size; i++) 
//			seq[i] = new Vector(Var.vectorMinValue, Var.vectorMaxValue);
	}
	
	public DNA(Vector seq[]) {
		this.seq = seq;
	}
	
	public static DNA combineDNA(Vector seq1[], Vector seq2[]) {
		Vector newSeq[] = new Vector[seq1.length];
		for (int i=0, j=seq2.length/2; i<seq1.length/2; i++, j++) {
			newSeq[i] = seq1[i];
			newSeq[j] = seq1[j];
		}
//		for (int i=0; i<seq1.length/2; i++) newSeq[i] = seq1[i];
//		for (int i=seq2.length/2; i<seq2.length; i++) newSeq[i] = seq2[i];
		
		DNA newDna = new DNA(newSeq);
		
		if (Var.mutation) newDna.mutate();
			
		return newDna;
	}
	
	private void mutate() {
		Random r = new Random();
		double flip;
		for (int i=0; i<seq.length; i++) {
			if (r.nextDouble() < Var.mutationRate) {
				seq[i] = new Vector(Var.vectorMinValue, Var.vectorMaxValue);
			}
		}
	}

	public void status() {
		System.out.print("DNA: ");
		for (int i=0; i<seq.length; i++) System.out.print(seq[i] + " ");
	}

	public void printDna() {
		System.out.println("DNA:");
		for (int i=0; i<seq.length; i++) {
			System.out.print(seq[i].toString() + ". ");
		}
		System.out.println();
	}
	
	public static boolean compare(DNA s1, DNA s2) {
		for (int i=0; i<s1.seq.length; i++) 
			if (!Vector.compare(s1.seq[i], s2.seq[i]))
				return false;
		return true;
	}

}
