package Try;

public class Simulator {
	
	static int n = Painter.n;
	static int g[][] = Painter.g;
	static int tg[][] = Painter.tg;
			
	static int check[][] = {
			{-1,-1},	//	top left
			{-1, 0},	//	top
			{-1, 1},	//	top right
			{ 0,-1},	//	left
			{ 0, 1},	//	right
			{ 1,-1},	//	bottom left
			{ 1, 0},	//	bottom
			{ 1, 1} 	//	bottom right
	};
	
	public static void simulate () {
		g = Painter.g;
		int tg[][] = new int[n][n];
		
		/*
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++) 
				if (g[i][j] == 1) 
					if (survival(i,j)) tg[i][j] = 1;
					else tg[i][j] = 0;
				else
					if(multiplication(i,j)) tg[i][j] = 1;
					else tg[i][j] = 0;
					
				
				// if(singleFunctionForAllRules(i,j)) tg[i][j] = 1;
				// else tg[i][j] = 0;
			
		Painter.g = tg;
	}
	
	//	Each cell with two or three neighbors survives.
	public static boolean survival (int pi, int pj) {
		if (pi == 0 || pj == 0 || pi == n-1 || pj == n-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++) {
			ci = pi + check[i][0];
			cj = pj + check[i][1];	
			
			if (g[ci][cj] == 1) counter++;
		}
		if (counter == 2 || counter == 3) return true;
		return false;
	}

	//	Each cell with three neighbors becomes populated.
	public static boolean multiplication (int pi, int pj) {
		if (pi == 0 || pj == 0 || pi == n-1 || pj == n-1) return false;
		int ci, cj, counter = 0;
		for (int i=0; i<check.length; i++){
			ci = pi + check[i][0];
			cj = pj + check[i][1];
			
			if (g[ci][cj] == 1) counter++;
		}
		if (counter == 3) return true;
		return false;
	}
	
	// not used, slightly slower
	public static boolean singleFunctionForAllRules(int pi, int pj) {
		if (pi == 0 || pj == 0 || pi == n-1 || pj == n-1) return false;
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
}
