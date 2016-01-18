/**
 * Created by Gregory on 1/17/2016.
 */
public class Board {

    private int size;
    private int tileSize;
    private int pieceSize;
    private int piecePadding;
    private int[][] board;

    public Board(int size, int tilePadding) {
        this.size = size;
        tileSize = Othello.WIDTH / size;
        pieceSize = tileSize - tilePadding;
        board = new int[size][size];
        piecePadding = tilePadding / 2;
        //init board
        for(int r = 0; r < size; r++) {
            for(int c = 0; c < size; c++) {
                board[r][c] = -1;
            }
        }

        board[size / 2 - 1][size / 2 - 1] = 1;
        board[size / 2 - 1][size / 2] = 2;
        board[size / 2][size / 2 - 1] = 2;
        board[size / 2][size / 2] = 1;
    }

    public int getPieceAt(int column, int row) {
        return board[row][column];
    }

    public int[][] getBoard() {
        return board;
    }

    public void setPieceAt(int column, int row, int player) {
        board[row][column] = player;
    }

    public int getPieceSize() {
        return pieceSize;
    }

    public int getPiecePadding() {
        return piecePadding;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
