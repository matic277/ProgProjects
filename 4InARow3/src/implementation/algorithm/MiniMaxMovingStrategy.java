package implementation.algorithm;

import enums.TokenType;
import implementation.core.GameState;
import implementation.core.Node;
import implementation.core.Pair;
import interfaces.IMovingStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiniMaxMovingStrategy implements IMovingStrategy {
    
    public GameState gameState;
    public Player player;
    
    // true  (max) player = 1
    // false (min) player = 2
    int[][] state;
    
    int depth = 4; // default depth
    
    public MiniMaxMovingStrategy(GameState gameState, Player player) { this.gameState = gameState; this.player = player; }
    
    public void setDepth(int newDepth) { depth = newDepth; }
    
    @Override
    public int makeMove() {
        initState();
        //System.out.println("--------------------------------------------------------starting--------------------------------------------------------");
        Pair<Double, Integer> result = minimax(state, 2, depth, 3);
        //System.out.println("ended with move: " + result.getB());
        return result.getB();
    }
    
    public void initState() {
        state = new int[6][7];
        TokenType[][] grid = gameState.getGrid();
        for (int i=0; i<6 ; i++) {
            for (int j=0; j<7; j++) {
                state[i][j] = grid[i][j].getVal();
            }
        }
    }
    
    public Pair<Double, Integer> minimax(int[][] state, int player, int depth, int col) {
        if (gameState.checkIfGameOver(state)) {
            System.out.println("GAMEOVER: " + new Pair<>(player == 1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, col));
            return new Pair<>(player == 1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY, col);
        }
        if (depth == 0) {
            System.out.println("\n -> 0 At depth [" + depth + "], returning: " + new Pair<>(eval(state), col));
            print(state);
            return new Pair<>(eval(state), col);
        }
        List<Pair<Double, Integer>> states = new ArrayList<>(8);
        
        // max
        if (player == 1) {
            Pair<Double, Integer> maxEval = new Pair<>(Double.NEGATIVE_INFINITY, -1);
            
            for (int freeCol : getFreeColumns(state)) {
                int[][] stateCopy = copyState(state);
                dropTokenInColumn(stateCopy, freeCol, player);
                Pair<Double, Integer> currentEval = minimax(stateCopy, 2, depth - 1, freeCol);
                
//                print(state);
//                System.out.println(currentEval+"\n");
//                states.add(currentEval);
                
//                System.out.println("\nDepth[" + depth + "] -> Comparing (current, max): " + currentEval + ", " + maxEval);
                if (currentEval.getA() >= maxEval.getA()) {
                    maxEval.setA(currentEval.getA());
                    maxEval.setB(freeCol);
                }
//                System.out.println(" -> Max eval is now: " + maxEval);
//                print(subState);
            }
//            System.out.println("\n -> At depth " + depth + ": ["+Arrays.deepToString(states.toArray())+"], returning max: " + maxEval);
            return maxEval;
        }
        // min
        else {
            Pair<Double, Integer> minEval = new Pair<>(Double.POSITIVE_INFINITY, -1);
            for (int freeCol : getFreeColumns(state)) {
                int[][] stateCopy = copyState(state);
                dropTokenInColumn(stateCopy, freeCol, player);
                Pair<Double, Integer> currentEval = minimax(stateCopy, 1, depth - 1, freeCol);
                
//                states.add(currentEval);
//                System.out.println("\nDepth[" + depth + "] -> Comparing (current, max): " + currentEval + ", " + maxEval);
                if (currentEval.getA() <= minEval.getA()) {
                    minEval.setA(currentEval.getA());
                    minEval.setB(freeCol);
                }
//                System.out.println(" -> Max eval is now: " + maxEval);
//                print(subState);
            }
//            System.out.println("\n -> At depth " + depth + ": ["+Arrays.deepToString(states.toArray())+"], returning min: " + minEval);
            return minEval;
        }
    }
    
    public static int[][] dropTokenInColumn(int[][] state, int inColumn, int player) {
        int inRow = getFirstFreeRowInColumn(state, inColumn);
        state[inRow][inColumn] = player;
        return state;
    }
    
    // counts number of same consecutive numbers
    // input: [1 1 2 2 2 1 3 3]
    // out:   [2   3     1 2  ]
    public static int[] reduceRow(int[] arr) {
        ArrayList<Integer> result = new ArrayList<>(arr.length);
        for (int i=0, j=1; i<arr.length; i++, j=i+1) {
            int type = arr[i];
            if (type == 0) continue;
            int c;
            for (j=i+1, c=1; j < arr.length; j++) {
                if (arr[j] == type) c++;
                else break;
            }
            result.add(type == 1 ? c : -c);
            i = j-1;
        }
        int[] result2 = new int[result.size()];
        for (int i=0; i<result2.length; i++) result2[i] = result.get(i);
        return result2;
    }
    
    public static int[] reduceColumn(int[][] arr, int col) {
        ArrayList<Integer> result = new ArrayList<>(arr.length);
        for (int i=0, j=1; i<arr.length; i++, j=i+1) {
            int type = arr[i][col];
            if (type == 0) continue;
            int c;
            for (j=i+1, c=1; j < arr.length; j++) {
                if (arr[j][col] == type) c++;
                else break;
            }
            result.add(type == 1 ? c : -c);
            i = j-1;
        }
        int[] result2 = new int[result.size()];
        for (int i=0; i<result2.length; i++) result2[i] = result.get(i);
        return result2;
    }
    
    // only if column has at least one free row
    public static int getFirstFreeRowInColumn(int[][] state, int inColumn) {
        for (int i=1; i<6; i++) {
            if (state[i][inColumn] != 0) return i-1;
        }
        return 5;
    }
    
    public static int[] getFreeColumns(int[][] state) {
        int numOfFreeCols = 0;
        for (int i=0; i<7; i++) if (state[0][i] == 0) numOfFreeCols++;
    
        int[] freeCols = new int[numOfFreeCols];
        for (int i=0, j=0; i<7; i++) if (state[0][i] == 0) freeCols[j++] = i;
        return freeCols;
    }
    
    public static int[][] copyState(int[][] state) {
        int len = state.length;
        int[][] newState = new int[len][state[0].length];
        for (int i=0; i<len; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, state[i].length);
        }
        return newState;
    }
    
    public static void main(String[] args) {
        int[][] state = new int [][]{
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2},
                {0, 1, 1, 1, 0, 2, 2}
        };
//        print(state);
        
        System.out.println(Arrays.toString(reduceRow(state[5])));
        
        //Node tree = new Node(state, 0, 1, 2, null);
        
    }
    
    // [0] = number of 3 in_a_row for max payer
    // [1] = number of 2 in_a_row for max payer
    // [2] = number of 3 in_a_row for min payer
    // [3] = number of 2 in_a_row for min payer
    public static void eval(int[] arr, int[] totalResult) {
        for (int j = 0; j < arr.length; j++) {
            switch (arr[j]) {
                case 3 -> totalResult[0]++;
                case 2 -> totalResult[1]++;
                case -3 -> totalResult[2]++;
                case -2 -> totalResult[3]++;
            }
        }
    }
    
    
    
    public static double eval(int[][] state) {
        int[] totalResult = new int[] {0, 0, 0, 0};
        
        // check all rows
        for (int i=0; i<6; i++) {
            int[] reduced = reduceRow(state[i]);
            for (int j = 0; j < reduced.length; j++) {
                if (reduced[j] > 3) return Double.POSITIVE_INFINITY;
                if (reduced[j] < -3) return Double.NEGATIVE_INFINITY;
            }
            eval(reduced, totalResult);
        }
        
        // check all columns
        for (int i=0; i<7; i++) {
            int[] reduced = reduceColumn(state, i);
            for (int j = 0; j < reduced.length; j++) {
                if (reduced[j] > 3) return Double.POSITIVE_INFINITY;
                if (reduced[j] < -3) return Double.NEGATIVE_INFINITY;
            }
            eval(reduced, totalResult);
        }
        
        // diags1 /
        int[] diag = new int[4];
        int[] reduced;
        for (int i=3, j=0, k=0; i>=0; i--, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[5];
        for (int i=4, j=0, k=0; i>=0; i--, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[6];
        for (int i=5, j=0, k=0; i>=0; i--, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[6];
        for (int i=5, j=1, k=0; i>=0; i--, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[5];
        for (int i=5, j=2, k=0; i>=1; i--, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[4];
        for (int i=5, j=3, k=0; i>=2; i--, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        
        // diags2 \
        diag = new int[4];
        for (int i=2, j=0, k=0; i<=5; i++, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[5];
        for (int i=1, j=0, k=0; i<=5; i++, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[6];
        for (int i=0, j=0, k=0; i<=5; i++, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[6];
        for (int i=0, j=1, k=0; i<=5; i++, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[5];
        for (int i=0, j=2, k=0; i<=4; i++, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        diag = new int[4];
        for (int i=0, j=3, k=0; i<=3; i++, j++, k++) diag[k] = state[i][j];
        reduced = reduceRow(diag);
        for (int i=0; i<reduced.length; i++) {
            if (reduced[i] > 3) return Double.POSITIVE_INFINITY;
            if (reduced[i] < -3) return Double.NEGATIVE_INFINITY;
        }
        eval(reduced, totalResult);
        
        // 3 -> 70%
        // 2 -> 30%
        // [0] = number of 3 in_a_row for max payer
        // [1] = number of 2 in_a_row for max payer
        // [2] = number of 3 in_a_row for min payer
        // [3] = number of 2 in_a_row for min payer
        return (0.7 * (totalResult[0] - totalResult[2])) + (0.3 * (totalResult[1] - totalResult[3]));
    }
    
    public static void print(int[][][] s) {
        for (int i=0; i<s.length; i++) {
            System.out.println(" "+(Arrays.deepToString(s[i])).substring(1, 137).replaceAll("\\],", "]\n"));
            System.out.println();
        }
    }
    
    public static void print(int[][] s) {
        for (int i=0; i<s.length; i++) {
            System.out.println(Arrays.toString(s[i]));
        }
    }
    
    private static void print(int[] s) {
        System.out.println(Arrays.toString(s));
    }
}
