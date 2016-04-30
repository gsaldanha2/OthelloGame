package com.game.othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by Gregory on 1/18/2016.
 */
//This class is a jpanel that takes care of board rendering and the board itself
public class Board extends JPanel {

    private int width, height;
    private int tileSize;
    private boolean moving = false;
    private int[][] board;
    private HumanPlayer humanPlayer;
    private ComputerPlayer cpuPlayer, cpuPlayerOpp;
    private int legalTileSize;

    private Othello othello;

    public Board(int width, int height, final int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.board = new int[width][height];
        legalTileSize = tileSize / 4;
        initializeBoard();

        othello = new Othello();
        humanPlayer = new HumanPlayer(othello);
        if (Fields.playType == 0) {
            cpuPlayer = new ComputerPlayer(othello, 2, Fields.difficultyOpp);
            cpuPlayerOpp = new ComputerPlayer(othello, 1, Fields.difficulty);
        } else {
            cpuPlayer = new ComputerPlayer(othello, 2, Fields.difficulty);
            cpuPlayerOpp = new ComputerPlayer(othello, 1, Fields.difficultyOpp);
        }


        this.setPreferredSize(new Dimension(width * tileSize, height * tileSize));
        MouseListener listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                final int x = e.getX() / tileSize;
                final int y = e.getY() / tileSize;

                if (y < getBoardHeight() && x < getBoardWidth()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (Fields.playType == 1) {
                                move(y, x, Board.this);
                            } else if (Fields.playType == 2) {
                                twoPlayerMove(y, x, Board.this);
                            } else {
                                move(Board.this);
                            }
                        }
                    }).start();
                }
            }
        };
        this.addMouseListener(listener);
    }

    public void makeMove(Move move, int piece) {
        setPiece(move, piece);
        othello.flipPieces(othello.getFlips(move, piece, this), piece, this);
    }

    public boolean isNew() {
        int p1 = 0, p2 = 0;
        for (int r = 0; r < getBoardHeight(); r++) {
            for (int c = 0; c < getBoardWidth(); c++) {
                if (getPieceAt(r, c) == Fields.WHITE)
                    p1++;
                else if (getPieceAt(r, c) == Fields.BLACK)
                    p2++;
            }
        }
        return ((p1 == 2) && (p2 == 2)) || ((p1 == 4) && (p2 == 1));
    }

    public ArrayList<Move> getAllMoves(int piece) {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int r = 0; r < getBoardHeight(); r++) {
            for (int c = 0; c < getBoardWidth(); c++) {
                if (othello.isLegal(new Move(r, c), piece, this))
                    moves.add(new Move(r, c, piece));
            }
        }
        return moves;
    }

    public void move(int row, int col, Board board) {
        if (!moving) {
            moving = true;
            if (!othello.gameEnded(board, true)) {
                if (board.getAllMoves(Fields.currPlayer).size() > 0) {
                    if (!othello.isLegal(new Move(row, col), Fields.player, board)) {
                        moving = false;
                        return;
                    }
                    humanPlayer.move(new Move(row, col), board);
                }
                Fields.currPlayer = Fields.BLACK;
                Game.currPlayerLabel.setBackground(Fields.blackColor);
                othello.updateScore(board);
                repaint();
                cpuPlayer.move(board);
                Fields.currPlayer = Fields.WHITE;
                Game.currPlayerLabel.setBackground(Fields.whiteColor);
                othello.updateScore(board);
                repaint();
                othello.gameEnded(board, true);
                if (board.getAllMoves(Fields.currPlayer).size() == 0) {
                    move(0, 0, board);
                }
            }
        }
        moving = false;
    }

    public void twoPlayerMove(int row, int col, Board board) {
        if (!moving) {
            moving = true;
            if (!othello.gameEnded(board, true)) {
                if (!othello.isLegal(new Move(row, col), Fields.currPlayer, board)) {
                    moving = false;
                    return;
                }
                if (Fields.currPlayer == 1) {
                    humanPlayer.move(new Move(row, col), board);
                    Game.currPlayerLabel.setBackground(Fields.blackColor);
                } else {
                    humanPlayer.move(new Move(row, col), board);
                    Game.currPlayerLabel.setBackground(Fields.whiteColor);
                }
                Fields.currPlayer = (Fields.currPlayer == Fields.BLACK) ? Fields.WHITE : Fields.BLACK;
                othello.updateScore(board);
                repaint();
                moving = false;
                othello.gameEnded(board, true);
            }
        }
    }

    public void move(Board board) {
        if (!moving) {
            moving = true;
            if (!othello.gameEnded(board, true)) {
                cpuPlayerOpp.move(board);
                Fields.currPlayer = Fields.BLACK;
                Game.currPlayerLabel.setBackground(Fields.blackColor);
                othello.updateScore(board);
                repaint();
                cpuPlayer.move(board);
                Fields.currPlayer = Fields.WHITE;
                Game.currPlayerLabel.setBackground(Fields.whiteColor);
                othello.updateScore(board);
                repaint();
                moving = false;
                move(board);
            }/*else {
                DebugHandler.writeGameData(Fields.player1score, Fields.player2score);
            }*/
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //draw background
        g.setColor(Fields.bgColor);
        g.fillRect(0, 0, width * tileSize, height * tileSize);

        //draw lines
        g.setColor(Fields.lineColor);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < height; col++) {
                g.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }

        //draw pieces
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < height; col++) {
                if (getPieceAt(row, col) == Fields.WHITE) g.setColor(Fields.whiteColor);
                else if (getPieceAt(row, col) == Fields.BLACK) g.setColor(Fields.blackColor);
                else continue;
                g.fillOval(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }

        if (Game.showLegalMoves.isSelected()) {
            g.setColor(Color.RED);
            for (Move legalMove : getAllMoves(Fields.currPlayer)) {
                g.fillOval(legalMove.getCol() * tileSize + (tileSize / 2 - (legalTileSize / 2)), legalMove.getRow() * tileSize + (tileSize / 2 - (legalTileSize / 2)), legalTileSize, legalTileSize);
            }
        }
    }

    public void initializeBoard() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                setPiece(row, col, Fields.EMPTY);
            }
        }

        setPiece(height / 2 - 1, width / 2 - 1, Fields.WHITE);
        setPiece(height / 2, width / 2, Fields.WHITE);
        setPiece(height / 2, width / 2 - 1, Fields.BLACK);
        setPiece(height / 2 - 1, width / 2, Fields.BLACK);
    }

    public Board cloneBoard() {
        Board temp = new Board(width, height, tileSize);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                temp.setPiece(row, col, this.getPieceAt(row, col));
            }
        }
        return temp;
    }

    public void setPiece(Move move, int piece) {
        board[move.getRow()][move.getCol()] = piece;
    }

    public void setPiece(int row, int col, int piece) {
        board[row][col] = piece;
    }

    public int getPieceAt(int row, int col) {
        return board[row][col];
    }

    public int getBoardWidth() {
        return width;
    }

    public int getBoardHeight() {
        return height;
    }
}
