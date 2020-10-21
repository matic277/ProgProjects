package implementation.core;

import implementation.graphics.GamePainter;
import implementation.graphics.Painter;
import implementation.graphics.Token;
import interfaces.Callable;
import interfaces.IPlayer;
import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import javax.swing.plaf.synth.ColorType;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicReference;

public class GameState {
    
    private static GameState gameState;
    
    final int n = 6;
    final int m = 7;
    Token[][] grid;
    TokenColor[][] typeGrid;

    CurrentPlayer player;
    boolean gameOver = false;
    
    // array of indexes where a move can
    // be made. Full columns are not here,
    // only legal moves.
    int[] freeColumns;
    
    // singleton
    private GameState() {
        grid = new Token[n][m];
        typeGrid = new TokenColor[n][m];
        initPlayers(null, null);
    }
    
    public void initPlayers(IPlayer p1, IPlayer p2) {
        player = new CurrentPlayer(p1, p2);
    }
    
    public static GameState getGameState() {
        if (gameState == null) gameState = new GameState();
        return gameState;
    }
    
    public int[] getAndRecalcFreeColumns() {
        ArrayList<Integer> freeCols = new ArrayList<>(7);
        for (int i=0; i<grid.length; i++) {
            if (grid[0][i].getType() == TokenColor.NONE) {
                freeCols.add(i);
            }
        }
        freeColumns = new int[freeCols.size()];
        for (int i=0; i<freeCols.size(); i++) freeColumns[i] = freeCols.get(i);
        return freeColumns;
    }
    
    public void initGameStateGrid(Rectangle panelSize) {
        int tokenSpacing = 10;
        int tokenRad = (panelSize.width - (m+1)*tokenSpacing) / m;
        
        for (int i = 0, y = panelSize.y + tokenSpacing + tokenRad/2; i < n; i++, y+=tokenRad + tokenSpacing) {
            for (int j = 0, x = panelSize.x + tokenSpacing + tokenRad/2; j < m; j++, x+=tokenRad + tokenSpacing) {
                grid[i][j] = TokenFactory.getTokenFactory()
                        .getToken(new Point(x, y), new Point(x, y), TokenColor.NONE, tokenRad);
                typeGrid[i][j] = TokenColor.NONE;
            }
        }
    }
    
    // Inserts token where it belongs (lowest position in column).
    // -> true if inserted, false otherwise
    public boolean inserTokenInColumn(Point2D spawnPos, int columnIndex, int tokenRad) {
        if (gameOver) return false;
        
        // row is full already
        if (grid[0][columnIndex].getType() != TokenColor.NONE) return false;
        
        boolean inserted = false;
        
        // start in bottom
        for (int i=grid.length-1; i>=0; i--) {
            if (grid[i][columnIndex].getType() == TokenColor.NONE) {
                Token token = TokenFactory
                        .getTokenFactory()
                        .getToken(grid[i][columnIndex].getPosition(), spawnPos, player.getColor(), tokenRad); // determine token rad?
                grid[i][columnIndex] = token;
                typeGrid[i][columnIndex] = player.getColor();
                
                // drop
                new Thread(getTokenDropper(token)).start();
                
                inserted = true;
                break;
            }
        }
        
        if (inserted) checkIfGameOver();
        return inserted;
    }
    
    public void checkIfGameOver() {
        // rows
        for (int row = 0; row < n; row++) {
            ArrayList<Integer> reduced = reduce(typeGrid[row]);
            if (contains4InARow(reduced)) signalGameOver(null);
        }
        
        // columns
        for (int column = 0; column < m; column++) {
            ArrayList<Integer> reduced = reduce(getColumn(column));
            if (contains4InARow(reduced)) signalGameOver(null);
        }
        
        // diags1 /
        for (ArrayList<Pair<Integer, Integer>> diagonal : diags1) {
            ArrayList<Integer> reduced = reduce(createArrayFromDiags(diagonal));
            if (contains4InARow(reduced)) signalGameOver(null);
        }
        
        // diags2 \
        for (ArrayList<Pair<Integer, Integer>> diagonal : diags2) {
            ArrayList<Integer> reduced = reduce(createArrayFromDiags(diagonal));
            if (contains4InARow(reduced)) signalGameOver(null);
        }
    }
    
    private TokenColor[] createArrayFromDiags(ArrayList<Pair<Integer, Integer>> diagonal) {
        TokenColor[] diag = new TokenColor[diagonal.size()];
        for (int i = 0; i < diagonal.size(); i++) {
            diag[i] = typeGrid[diagonal.get(i).getA()][diagonal.get(i).getB()];
        }
        return diag;
    }
    
    private TokenColor[] getColumn(int column) {
        TokenColor[] columnArr = new TokenColor[n];
        for (int i = 0; i < n; i++) {
            columnArr[i] = typeGrid[i][column];
        }
        return columnArr;
    }
    
    // counts number of same consecutive numbers
    // input: [1 1 2 2 2 1 3 3]
    // out:   [2   3     1 2  ]
    private ArrayList<Integer> reduce(TokenColor[] arr) {
        ArrayList<Integer> result = new ArrayList<>(arr.length);
        for (int i=0, j=1; i<arr.length; i++, j=i+1) {
            TokenColor type = arr[i];
            if (type == TokenColor.NONE) continue;
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
            if (i > 3) return true;
        }
        return false;
    }
    
    // TODO
    private void signalGameOver(TokenColor winner) {
        gameOver = true;
        
    }
    
    public Runnable getTokenDropper(Token token) {
        return () -> {
            while (token.drop()) Painter.sleep(144);
        };
    }
    
    public Token[][] getGrid() {
        return grid;
    }
    
    public void nextPlayer() {
        player.nextPlayer();
    }
    
    public boolean isGameOver () { return gameOver; }
    
    public Callable<Rectangle> getResetAction() {
        return (panelSize) -> {
            initGameStateGrid(panelSize);
            gameOver = false;
        };
    }
    
    public IPlayer getCurrentPlayer() {
        return player.player.getPlayer();
    }
    
    
    // recursive structure to get rid of if statements
    // ... -> player -> player2 -> player -> ...
    public static class CurrentPlayer {
        Player player;
        public CurrentPlayer(IPlayer p1, IPlayer p2) {
            player = new Player(TokenColor.RED, p1, null);
            player.next = new Player(TokenColor.YELLOW, p2, player);
        }
        public void nextPlayer() { player = player.next; }
        public TokenColor getColor() { return player.color; }
        
        public static class Player {
            TokenColor color;
            IPlayer player;
            Player next;
            public Player(TokenColor color, IPlayer player, Player next) { this.color = color; this.player = player; this.next = next; }
            public IPlayer getPlayer() { return player; }
        }
    }
    
    // predefined diags to check
    private static final ArrayList<ArrayList<Pair<Integer, Integer>>> diags1;
    private static final ArrayList<ArrayList<Pair<Integer, Integer>>> diags2;
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
