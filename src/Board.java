import javax.swing.*;
import java.awt.*;

/**
 * Created by Gregory on 1/18/2016.
 */
public class Board extends JPanel {

    private int width, height;
    private int tilesize;
    private int[][] board;

    public Board(int width, int height, int tilesize) {
        this.width = width;
        this.height = height;
        this.tilesize = tilesize;
        this.board =  new int[width][height];

        this.setPreferredSize(new Dimension(width * tilesize, height * tilesize));
        this.addMouseListener(new MoveClickHandler());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //draw background
        g.setColor(Options.bgColor);
        g.fillRect(0, 0, width * tilesize, height * tilesize);

        //draw lines
        g.setColor(Options.lineColor);
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < height; col++) {
                g.drawRect(row * tilesize, col * tilesize, tilesize, tilesize);
            }
        }
    }

    public void setPiece(Move move, int piece) {
        board[move.row][move.col] = piece;
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
