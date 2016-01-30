import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private ComputerPlayer cpuPlayer, cpuPlayer2;

    private Othello othello;

    public Board(int width, int height, final int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.board = new int[width][height];
        initializeBoard();

        othello = new Othello();
        humanPlayer = new HumanPlayer(othello);
        cpuPlayer = new ComputerPlayer(othello, 1,4);
        cpuPlayer2 = new ComputerPlayer(othello, 2,4);

        this.setPreferredSize(new Dimension(width * tileSize, height * tileSize));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                final int x = e.getX() / tileSize;
                final int y = e.getY() / tileSize;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(Fields.playType == 1) {
                            move(y, x, Board.this);
                        }else {
                            move(Board.this);
                        }
                    }
                }).start();
            }
        });
    }

        public void move(int row, int col, Board board) {
        if(!moving) {
            moving = true;
            if (!othello.gameEnded(board)) {
                if (!othello.isLegal(new Move(row, col), Fields.player, board)) {
                    moving = false;
                    return;
                }
                humanPlayer.move(new Move(row, col), board);
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cpuPlayer2.move(board);
                repaint();
                moving = false;
            }
        }
    }
    public void move(Board board) {
        if (!moving) {
            moving = true;
            if (!othello.gameEnded(board)) {
                cpuPlayer.move(board);
                repaint();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                cpuPlayer2.move(board);
                repaint();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                moving = false;
                move(board);
            }
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
                g.drawRect(row * tileSize, col * tileSize, tileSize, tileSize);
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

    public int evaluateBoard(int piece) {
        int output = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (getPieceAt(row, col) == piece) {
                    output++;
                }
            }
        }
        return output;
    }

    public void setPiece(Move move, int piece) {
        board[move.row][move.col] = piece;
    }

    public void setPiece(int row, int col, int piece) {
        board[row][col] = piece;
    }

    public int getPieceAt(Move move) {
        return board[move.row][move.col];
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
