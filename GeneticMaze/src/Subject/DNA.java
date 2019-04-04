package Subject;

import java.util.Random;
import Main.Var;

public class DNA {
	
	Vector seq[];

	public DNA(int size) {
		if (size == -1)  size = Var.DnaLength;
		
		seq = new Vector[size];
		
		seq[0] = new Vector(Var.vectorMinValue, Var.vectorMaxValue);
		for (int i=1; i<size; i++) {
			seq[i] = new Vector(seq[i-1]);
			seq[i].rotate();
		}
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
	
		DNA newDna = new DNA(newSeq);
		
		if (Var.mutation) newDna.mutate();
		
		return newDna;
	}
	
	private void mutate() {
		Random r = new Random();
		for (int i=0; i<seq.length; i++) {
			if (r.nextDouble() < Var.mutationRate) {
				seq[i] = new Vector(Var.vectorMinValue, Var.vectorMaxValue);
			}
		}
	}

	public static boolean compare(DNA s1, DNA s2) {
		for (int i=0; i<s1.seq.length; i++) 
			if (!Vector.compare(s1.seq[i], s2.seq[i]))
				return false;
		return true;
	}
	
	@ Override
	public String toString() {
		String s = "";
		for (int i=0; i<seq.length; i++) s += seq[i] + " ";
		return s;
	}

	public Vector[] getSeq() {
		return seq;
	}

}
