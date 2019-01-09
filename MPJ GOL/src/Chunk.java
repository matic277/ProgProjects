

public class Chunk {
	
	int g[][];
	
	public Chunk(int gg[][], int s, int e) {
		// add extra top and bottom line accordingly
		if (s == 0) e++;
		else if (e == gg.length) s--;
		else {
			s--;
			e++;
		}
		
		
		g = new int[e-s][gg[0].length];
		
		for (int i=s, ii=0; i<e; i++, ii++)
		for (int j=0; j<gg[i].length; j++)
			g[ii][j] = gg[i][j];
	}
	
	public void print() {
		System.out.println("Status:");
		for (int i=0; i<g.length; i++) {
			for (int j=0; j<g[i].length; j++) System.out.print(g[i][j]+" ");
			System.out.println();
		}
		System.out.println();
	}
	
	public int[][] process() {
		int newg[][] = new int[g.length][g[0].length];

		for (int i=0; i<g.length; i++)
		for (int j=0; j<g[i].length; j++) {
			//System.out.println("ind: "+i+" "+j);
			if (applyRules(i,j)) newg[i][j] = 1;
			else newg[i][j] = 0;
		}
		
		return newg;
	}
	
	public boolean applyRules(int pi, int pj) {
		if (pi == 0 || pj == 0 || pi == g.length-1 || pj == g[0].length-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++){
			ci = pi + check[i][0];
			cj = pj + check[i][1];
			
			if (g[ci][cj] == 1) counter++;
		}
		if (g[pi][pj] == 0 &&  counter == 3) return true;
		if (g[pi][pj] == 1 && (counter == 3 || counter == 2)) return true;
		return false;	
	}
	
	int check[][] = {
			{-1,-1},	//	top left
			{-1, 0},	//	top
			{-1, 1},	//	top right
			{ 0,-1},	//	left
			{ 0, 1},	//	right
			{ 1,-1},	//	bottom left
			{ 1, 0},	//	bottom
			{ 1, 1} 	//	bottom right
	};

}
