package implementation.core;

import implementation.algorithm.MiniMaxMovingStrategy;

import java.util.ArrayList;

public class Node {
    
    static GameState gameState;
    
    int[][] state;
    int player;
    double eval;
    int depth;
    int move;
    
    Node parent;
    ArrayList<Node> children;
    
    public Node(int[][] state, int move, int player, int depth, Node parent) {
        this.state = state;
        this.player = player;
        this.parent = parent;
        this.depth = depth;
        this.move = move;
        this.children = new ArrayList<>(7);
        
        // eval position
        this.eval = MiniMaxMovingStrategy.eval(state);
        
        if (depth == 0) return;
        
        // generate children
        int[] freeCols = MiniMaxMovingStrategy.getFreeColumns(state);
        for (int i=0; i<freeCols.length; i++) {
            int col = freeCols[i];
            int row = MiniMaxMovingStrategy.getFirstFreeRowInColumn(state, col);
            int[][] subState = MiniMaxMovingStrategy.copyState(state);
            subState[row][col] = player;
            children.add(new Node(subState, col, (player == 1 ? 2 : 1), depth - 1, this));
        }
    }
    
    public ArrayList<Node> getAllLeafs(ArrayList<Node> leafs) {
        if (isLeaf()) {
            leafs.add(this);
            return leafs;
        }
        children.forEach(c -> c.getAllLeafs(leafs));
        return leafs;
    }
    
    public boolean isLeaf() { return children.isEmpty(); }
}
