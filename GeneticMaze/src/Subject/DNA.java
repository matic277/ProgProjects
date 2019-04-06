package Subject;

import java.util.Random;
import Main.Var;

public class DNA {
	
	Vector seq[];

	public DNA(int size) {
		if (size == -1)  size = Var.DnaLength;
		
		seq = new Vector[size];
		
		seq[0] = new Vector();
		for (int i=1; i<size; i++) {
			seq[i] = new Vector(seq[i-1]);
			seq[i].rotate();
		}
	}	
	public DNA(Vector seq[]) {
		this.seq = seq;
	}
	
	public static DNA combineDNA(DNA dna1, DNA dna2) {
		Vector seq1[] = dna1.getSeq();
		Vector seq2[] = dna2.getSeq();
		Vector newSeq[] = new Vector[seq1.length];
		
		for (int i=0, j=seq1.length/2; i<seq2.length/2; i++, j++) {
			newSeq[i] = seq1[i];
			newSeq[j] = seq2[j];
		}
		
		return new DNA(newSeq);
	}
	
	public void mutate() {
		Random r = new Random();
		for (int i=1; i<seq.length; i++) {
			if (r.nextDouble() < Var.mutationRate) {
				seq[i] = new Vector(seq[i-1]);
				seq[i].rotate();
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
