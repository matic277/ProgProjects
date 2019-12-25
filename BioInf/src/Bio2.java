
public class Bio2 {
	
	
//	static int[][] m;
//	public static void editDistAlign(String s1, String s2) {
//		m = new int[s1.length()][s2.length()];
//		 for (int i=0; i<=m.length; i++) for (int j=0; j<=m[0].length; j++)
//			 m[i][j] = Integer.MIN_VALUE;	// null cells
//			 
//		 
//		rec("-"+s1, "-"+s2, 0, 0);
//	}
//	
//	public static void rec(String s1, String s2, int i, int j) {
//		// bounds
//		if (i >= m.length || j >= m[0].length) return;
//		
//		// if cell is not null, it has been computed already
//		if (m[i][j] == Integer.MIN_VALUE)
//		rec(i+1, j);
//		rec(i+1, j+1);
//		rec(i, j+1;
//	}
	
	public static void main(String[] args) {
		String s1 = "PRETTY";
		String s2 = "PRTTEIN";
		int r = Bio2.editDistance(s1, s2, s1.length(), s2.length());
		System.out.println(r);
	}
	

  
    public static int editDistance(String str1 , String str2 , int m ,int n) { 
        int dp[][] = new int[m+1][n+1]; 
        for (int i=0; i<=m; i++) { 
            for (int j=0; j<=n; j++) { 
                if (i==0) 
                    dp[i][j] = j;
                else if (j==0) 
                    dp[i][j] = i;
                else if (str1.charAt(i-1) == str2.charAt(j-1)) 
                    dp[i][j] = dp[i-1][j-1]; 
                else
                    dp[i][j] = 1 + min(dp[i][j-1],
                                       dp[i-1][j],
                                       dp[i-1][j-1]);
            } 
        }
        printMatrix(dp);
        System.out.println("[" + dp.length + "][" + dp[0].length + "]");
        System.out.println(m + ", " + n);
        
        // trace back to top left corner of matrix
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        int i=m-1, j=n-1;
        while (i != 0 || j != 0) {
        	int min = minIndex(dp[i-1][j], dp[i-1][j-1], dp[i][j-1]);
        	if (min == 1) {
        		// up
        		s1.append(str1.charAt(i));
        		s2.append("-");
        		i--;
        	}
        	else if (min == 2) {
        		// diag
        		s1.append(str1.charAt(i));
        		s2.append(str2.charAt(j));
        		i--; j--;
        	}
        	else if (min == 3) {
        		// left
        		s1.append("-");
        		s2.append(str2.charAt(j));
        		j--;
        	}
        	System.out.println("i,j: " + i + ", " + j);
        }
        
        System.out.println(s1.append(str1.charAt(0)).reverse().toString());
        System.out.println(s2.append(str2.charAt(0)).reverse().toString());
        
        return dp[m][n]; 
    } 
    
	private static void printMatrix(int[][] dp) {
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
	}
	static int minIndex(int i1, int i2, int i3) {
		if (i1 <= i2 && i1 <= i3) return 1; 
		if (i2 <= i1 && i2 <= i3) return 2; 
		return 3;
	}

	static int min(int x,int y,int z) { 
        if (x<=y && x<=z) return x; 
        if (y<=x && y<=z) return y; 
        else return z; 
    } 
	
}
