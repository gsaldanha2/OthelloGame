package com.game.othello;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Node parent = null;
    private List<Node> children = new ArrayList<Node>();
    private Move move = null;
    private int score = 0;
    private int playerPiece = 1;
    private int max = 2;

    public Node(Node parent, Move move, int max, int playerPiece) {
        this.move = move;
        this.parent = parent;
        this.max = max;
        this.playerPiece = playerPiece;
    }

    public Node() {
    }

    public int getPlayerPiece() {
        return playerPiece;
    }

    public void setPlayerPiece(int playerPiece) {
        this.playerPiece = playerPiece;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object obj) {
        Node n2 = (Node) obj;
        if ((n2.getMove().getRow() == this.getMove().getRow()) && (n2.getMove().getCol() == this.getMove().getCol())) {
            return true;
        }
        return false;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Move getMove() {
        return move;
    }

    public Node getParent() {
        return parent;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }
}
