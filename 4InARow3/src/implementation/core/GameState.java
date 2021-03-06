package implementation.core;


import enums.TokenType;
import implementation.algorithm.PlayerType;
import implementation.graphics.GamePainter;
import implementation.graphics.PanelPainter;
import interfaces.IGameState;
import interfaces.IPlayer;
import implementation.graphics.PanelPainter.MyButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameState implements IGameState {
    
    int m = 7;
    int n = 6;
    public TokenType[][] grid;
    ConcurrentLinkedQueue<Token> tokens;
    
    CurrentPlayer currentPlayer;
    boolean isGameOver;
    boolean areBothPlayersComputers;
    
    // lock when game is over/restarted
    private final Object stateLock = new Object();
    
    public GameState() {
        this.isGameOver = false;
        this.tokens = new ConcurrentLinkedQueue<>();
        this.grid = new TokenType[n][m];
        initResetGameState();
    }
    
    public void setPlayers(IPlayer p1, IPlayer p2) {
        currentPlayer = new CurrentPlayer(p1, p2);
        areBothPlayersComputers =
                p1.getPlayerType() == PlayerType.COMPUTER &&
                p2.getPlayerType() == PlayerType.COMPUTER;
    }
    
    public void initResetGameState() {
        this.isGameOver = false;
        this.tokens = new ConcurrentLinkedQueue<>();
        this.grid = new TokenType[n][m];
        for (int i=0; i<n; i++) for (int j=0; j<m; j++) {
            grid[i][j] = TokenType.NONE;
        }
    }
    
    @Override
    public void addToken(Token token, int inRow, int inColumn) {
        tokens.add(token);
        grid[inRow][inColumn] = token.getType();
        System.out.println("   -> Token added: (" + inRow + ", " + inColumn + "), total tokens: " + tokens.size());
        new Thread(getTokenDropper(token)).start();
        printState();
        checkIfGameOver();
    }
    
    public int getEmptyRowAtColumn(int inColumn) {
        if (isColumnFull(inColumn)) return -1;
        for (int i=n-1; i>=0; i--) {
            if (grid[i][inColumn] == TokenType.NONE) {
                return i;
            }
        }
        return -1; // unreachable
    }
    
    public boolean areBothPlayersComputers() { return areBothPlayersComputers; }
    
    private Runnable getTokenDropper(Token t) {
        return () -> {
            while (!t.drop()) { PanelPainter.sleep(144); }
        };
    }
    
    public MyButton getResetButton() {
        MyButton btn = new MyButton("Reset");
        btn.setMyAction(this::initResetGameState);
        btn.setBounds(new Rectangle(100, 25, 80, 40));
        btn.addActionListener(a -> btn.executeAction());
        btn.setFocusable(false);
        return btn;
    }
    
    public void checkIfGameOver() {
        // rows
        for (int row = 0; row < n; row++) {
            ArrayList<Integer> reduced = reduce(grid[row]);
            if (contains4InARow(reduced)) {
                isGameOver = true;
                return;
            }
        }
        
        // columns
        for (int column = 0; column < m; column++) {
            ArrayList<Integer> reduced = reduce(getColumn(column));
            if (contains4InARow(reduced)) {
                isGameOver = true;
                return;
            }
        }
        
        // diags1 /
        for (ArrayList<Pair<Integer, Integer>> diagonal : diags1) {
            ArrayList<Integer> reduced = reduce(createArrayFromDiags(diagonal));
            if (contains4InARow(reduced)) {
                isGameOver = true;
                return;
            }
        }
        
        // diags2 \
        for (ArrayList<Pair<Integer, Integer>> diagonal : diags2) {
            ArrayList<Integer> reduced = reduce(createArrayFromDiags(diagonal));
            if (contains4InARow(reduced)) {
                isGameOver = true;
                return;
            }
        }
    }
    
    private TokenType[] createArrayFromDiags(ArrayList<Pair<Integer, Integer>> diagonal) {
        TokenType[] diag = new TokenType[diagonal.size()];
        for (int i = 0; i < diagonal.size(); i++) {
            diag[i] = grid[diagonal.get(i).getA()][diagonal.get(i).getB()];
        }
        return diag;
    }
    
    private TokenType[] getColumn(int column) {
        TokenType[] columnArr = new TokenType[n];
        for (int i = 0; i < n; i++) {
            columnArr[i] = grid[i][column];
        }
        return columnArr;
    }
    
    // TODO useless method of interface
    @Override
    public void inputToken(Token token, int inColumn, GamePainter gamePainter) { }
    
    // counts number of same consecutive numbers
    // input: [1 1 2 2 2 1 3 3]
    // out:   [2   3     1 2  ]
    private ArrayList<Integer> reduce(TokenType[] arr) {
        ArrayList<Integer> result = new ArrayList<>(arr.length);
        for (int i=0, j=1; i<arr.length; i++, j=i+1) {
            TokenType type = arr[i];
            if (type == TokenType.NONE) continue;
            int c;
            for (j=i+1, c=1; j < arr.length; j++) {
                if (arr[j] == type) c++;
                else break;
            }
            result.add(c);
            i = j-1;
        }
        return result;
    }
    
    private boolean contains4InARow(ArrayList<Integer> reduced) {
        for (int i : reduced) {
            if (i == 4) return true;
        }
        return false;
    }
    
    @Override
    public boolean checkIfGameOver(int[][] state) {
        // rows
        for (int row = 0; row < n; row++) {
            ArrayList<Integer> reduced = reduce(grid[row]);
            if (contains4InARow(reduced)) {
                isGameOver = true;
                return true;
            }
        }
    
        // columns
        for (int column = 0; column < m; column++) {
            ArrayList<Integer> reduced = reduce(getColumn(column));
            if (contains4InARow(reduced)) {
                isGameOver = true;
                return true;
            }
        }
    
        // diags1 /
        for (ArrayList<Pair<Integer, Integer>> diagonal : diags1) {
            ArrayList<Integer> reduced = reduce(createArrayFromDiags(diagonal));
            if (contains4InARow(reduced)) {
                isGameOver = true;
                return true;
            }
        }
    
        // diags2 \
        for (ArrayList<Pair<Integer, Integer>> diagonal : diags2) {
            ArrayList<Integer> reduced = reduce(createArrayFromDiags(diagonal));
            if (contains4InARow(reduced)) {
                isGameOver = true;
                return true;
            }
        }
        return false;
    }
    
    // TODO!!
    
    public int[] getFreeColumns() {
        int numOfFreeCols = 0;
        for (int i=0; i<7; i++) if (grid[0][i] == TokenType.NONE) numOfFreeCols++;
    
        int[] freeCols = new int[numOfFreeCols];
        for (int i=0, j=0; i<7; i++) if (grid[0][i] == TokenType.NONE) freeCols[j++] = i;
        return freeCols;
    }
    
    public void isLegalMoveOrThrowException(int inColumn) {
        if (inColumn < 0 || inColumn > 7 || grid[0][inColumn] != TokenType.NONE)
            throw new RuntimeException("Illegal move made by " + currentPlayer.player.player.getPlayerType() +
                    ", move made in column: " + inColumn + ".\nGameState:\n" + stateToString());
    }
    
    public void printState() {
        System.out.println(stateToString());
    }
    
    public String stateToString() {
        StringBuilder state = new StringBuilder();
        for (int i=0; i<6; i++) {
            state.append("[");
            for (int j = 0; j < 7; j++)
                state.append(grid[i][j].getVal()).append(j == 6 ? "" : " ");
            state.append("]\n");
        }
        return state.toString();
    }
    
    @Override
    public ConcurrentLinkedQueue<Token> getTokens() { return tokens; }
    
    public boolean isColumnFull(int colIndex) {
        return grid[0][colIndex] != TokenType.NONE;
    }
    
    public TokenType getCurrentPlayersTokenType() {
//        return currentPlayer == null?
//                TokenType.RED :
        return currentPlayer.getTokenType();
    }
    
    @Override
    public IPlayer getCurrentPlayer() { return currentPlayer.player.player; }
    
    @Override
    public boolean checkIfGameOver(TokenType[][] grid) {
        throw new RuntimeException("Not done yet.");
    }
    
    @Override
    public void nextPlayer() { currentPlayer.nextPlayer(); }
    
    @Override
    public boolean isGameOver() { return isGameOver; }
    
    @Override
    public TokenType[][] getGrid() { return this.grid; }
    
    @Override
    public void setGrid(TokenType[][] grid) { throw new RuntimeException("Not done yet."); }
    
    public Object getStateLock() { return stateLock; }
    // predefined diags to check
    public static final ArrayList<ArrayList<Pair<Integer, Integer>>> diags1;
    public static final ArrayList<ArrayList<Pair<Integer, Integer>>> diags2;
    
    static {
        diags1 = new ArrayList<>(6);
        diags2 = new ArrayList<>(6);
        ArrayList<Pair<Integer, Integer>> diag = new ArrayList<>(6);
        
        for (int i=3, j=0; i>=0; i--, j++)
            diag.add(new Pair<>(i, j));
        diags1.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=4, j=0; i>=0; i--, j++)
            diag.add(new Pair<>(i, j));
        diags1.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=5, j=0; i>=0; i--, j++)
            diag.add(new Pair<>(i, j));
        diags1.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=5, j=1; i>=0; i--, j++)
            diag.add(new Pair<>(i, j));
        diags1.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=5, j=2; i>=1; i--, j++)
            diag.add(new Pair<>(i, j));
        diags1.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=5, j=3; i>=2; i--, j++)
            diag.add(new Pair<>(i, j));
        diags1.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=2, j=0; i<=5; i++, j++)
            diag.add(new Pair<>(i, j));
        diags2.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=1, j=0; i<=5; i++, j++)
            diag.add(new Pair<>(i, j));
        diags2.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=0, j=0; i<=5; i++, j++)
            diag.add(new Pair<>(i, j));
        diags2.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=0, j=1; i<=5; i++, j++)
            diag.add(new Pair<>(i, j));
        diags2.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=0, j=2; i<=4; i++, j++)
            diag.add(new Pair<>(i, j));
        diags2.add(diag);
        
        diag = new ArrayList<>(6);
        for (int i=0, j=3; i<=3; i++, j++)
            diag.add(new Pair<>(i, j));
        diags2.add(diag);
    }
}
