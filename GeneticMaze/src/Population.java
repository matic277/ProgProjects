import java.awt.Graphics2D;
import java.util.Random;

public class Population {
	
	Subject pop[];
	int popsize;

	public Population() {
		popsize = Var.populationSize;
		pop = new Subject[popsize];
		for (int i=0; i<popsize; i++) pop[i] = new Subject(i);
	}

	public void calculateFitness() {
		double fitnessSum = 0;
		
		// calculate fitness function of each subject
		for (int i=0; i<popsize; i++) {
			pop[i].setFitness();
			fitnessSum += pop[i].fitness;
		}
		Var.averageFitness = fitnessSum / popsize;
		
		// sort subjects by fitness
		for (int i=0; i<popsize; i++) 
		for (int j=0; j<popsize-1; j++) 
			if (pop[j].fitness > pop[j+1].fitness) swap(j, j+1);
	}

	public void createNewGeneration() {
		// take top 50% of pop
		Random r = new Random();
		int r1 = r.nextInt(popsize / 2);
		int r2 = r.nextInt(popsize / 2);
		Subject p1, p2;
		for (int i=popsize/2; i<popsize; i++) {
			p1 = pop[r1];
			p2 = pop[r2];
			pop[i] = Subject.mateSubjects(p1, p2, i);
			
			r1 = r.nextInt(popsize / 2);
			r2 = r.nextInt(popsize / 2);
		}
		
		Var.generationNumber++;
	}
	
	private void swap(int j, int jj) {
		Subject tmp = pop[j];
		pop[j] = pop[jj];
		pop[jj] = tmp;
	}
	
	public void move() {
		for (int i=0; i<pop.length; i++) pop[i].move();
		Var.dnaIndex++;
	}

	public void draw(Graphics2D g) {
		for (Subject s : pop) s.draw(g);
	}
	
	public void resetPositions() {
		for (Subject s : pop) s.resetPosition();
		Var.dnaIndex = 0;
//		for (int i=0; i<popsize; i++) {
//			for (int j=i+1; j<popsize; j++) {
//				if (DNA.compare(pop[j].dna, pop[i].dna)) {
//					System.out.println("DNA match for subjects: "+i+", "+j);
//					pop[i].printDna();
//					pop[j].printDna();
//				}
//			}
//		}
	}

}
