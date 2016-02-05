import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private HumanPlayer humanPlayer, humanPlayer2;
    private ComputerPlayer cpuPlayer, cpuPlayerOpp;

    private Othello othello;

    public Board(int width, int height, final int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.board = new int[width][height];
        initializeBoard();

        othello = new Othello();
        humanPlayer = new HumanPlayer(othello);
        humanPlayer2 = new HumanPlayer(othello);
        cpuPlayer = new ComputerPlayer(othello, 2, Fields.difficulty);
        cpuPlayerOpp = new ComputerPlayer(othello, 1, Fields.difficultyOpp);

        this.setPreferredSize(new Dimension(width * tileSize, height * tileSize));
        this.addMouseListener(new MouseAdapter() {
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
        });
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
        System.out.println(p1 + "," + p2);
        return (p1 == 4) && (p2 == 1);
    }

    public ArrayList<Move> getAllMoves(int piece) {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int r = 0; r < getBoardHeight(); r++) {
            for (int c = 0; c < getBoardWidth(); c++) {
                if (othello.isLegal(new Move(r, c), piece, this))
                    moves.add(new Move(r, c));
            }
        }
        return moves;
    }

    public void move(int row, int col, Board board) {
        if (!moving) {
            moving = true;
            if (!othello.gameEnded(board)) {
                if (board.getAllMoves(Fields.currPlayer).size() > 0) {
                    if (!othello.isLegal(new Move(row, col), Fields.player, board)) {
                        moving = false;
                        return;
                    }
                    humanPlayer.move(new Move(row, col), board);
                }
                Fields.currPlayer = Fields.BLACK;
                Game.optionsPanel.setBackground(Fields.blackColor);
                othello.updateScore(board);
                repaint();
                cpuPlayer.move(board);
                Fields.currPlayer = Fields.WHITE;
                Game.optionsPanel.setBackground(Fields.whiteColor);
                othello.updateScore(board);
                repaint();
                othello.gameEnded(board);
            }
        }
        moving = false;
    }

    public void twoPlayerMove(int row, int col, Board board) {
        if (!moving) {
            moving = true;
            if (!othello.gameEnded(board)) {
                if (!othello.isLegal(new Move(row, col), Fields.currPlayer, board)) {
                    moving = false;
                    return;
                }
                if (Fields.currPlayer == 1)
                    humanPlayer.move(new Move(row, col), board);
                else
                    humanPlayer.move(new Move(row, col), board);
                Fields.currPlayer = (Fields.currPlayer == Fields.BLACK) ? Fields.WHITE : Fields.BLACK;
                Game.optionsPanel.setBackground(Fields.blackColor);
                othello.updateScore(board);
                repaint();
                moving = false;
                othello.gameEnded(board);
            }
        }
    }

    public void move(Board board) {
        if (!moving) {
            moving = true;
            if (!othello.gameEnded(board)) {
                cpuPlayerOpp.move(board);
                Fields.currPlayer = Fields.BLACK;
                Game.optionsPanel.setBackground(Fields.blackColor);
                othello.updateScore(board);
                repaint();
                cpuPlayer.move(board);
                Fields.currPlayer = Fields.WHITE;
                Game.optionsPanel.setBackground(Fields.whiteColor);
                othello.updateScore(board);
                repaint();
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
