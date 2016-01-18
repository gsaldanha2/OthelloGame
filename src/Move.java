/**
 * Created by Gregory on 1/18/2016.
 */
public class Move {
    public int row, col, piece, oppPiece;
    private Board board;
    private int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1,0}, {1, 1}};

    public Move(int row, int col, int piece, Board board) {
        this.row = row;
        this.col = col;
        this.piece = piece;
        this.board = board;

        oppPiece = (piece == 1) ? 2 : 1;
    }

    public boolean isLegal() {
        if(board.getPieceAt(row, col) != Fields.EMPTY) return false;

        for(int[] dir : directions) {
            boolean hasAdj = false, hasPiece = false;
            for(int count = 1;; count++) {
                int tmpRow = row + dir[0] * count, tmpCol = col + dir[1] * count;

                if(board.getPieceAt(tmpRow, tmpCol) == Fields.EMPTY) break;
                if(board.getPieceAt(tmpRow, tmpCol) == oppPiece) hasAdj = true;
                if(board.getPieceAt(tmpRow, tmpCol) == piece) hasPiece = true;
            }

            if(hasAdj && hasPiece) return true;
        }
        return false;
    }
}
