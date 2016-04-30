package com.game.othello;

/**
 * Created by Gregory on 1/18/2016.
 */
//this class respresents a row and column coordinate on the board
public class Move {
    private int row, col;
    private int player = 1;

    public Move(int row, int col, int player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
