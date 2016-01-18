import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Gregory on 1/18/2016.
 */
public class Board extends JPanel {

    private int width, height;
    private int tileSize;
    private int[][] board;

    private Othello othello;

    public Board(int width, int height, final int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.board = new int[width][height];
        initializeBoard();

        othello = new Othello(this);

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
                        othello.move(y, x);
                    }
                }).start();
            }
        });
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
                else if(getPieceAt(row, col) == Fields.BLACK) g.setColor(Fields.blackColor);
                else continue;
                g.fillOval(row * tileSize, col * tileSize, tileSize, tileSize);
            }
        }
    }

    public void initializeBoard() {
        setPiece(height / 2 - 1, width / 2 - 1, Fields.WHITE);
        setPiece(height / 2, width / 2, Fields.WHITE);
        setPiece(height / 2, width / 2 - 1, Fields.BLACK);
        setPiece(height / 2 - 1, width / 2, Fields.BLACK);
    }

    public void setPiece(Move move) {
        board[move.row][move.col] = move.piece;
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
