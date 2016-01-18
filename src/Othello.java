import java.util.ArrayList;

/**
 * Created by Gregory on 1/18/2016.
 */
//This class handles game logic
public class Othello {

    private Board board;
    private int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1,0}, {1, 1}};

    public Othello(Board board) {
        this.board = board;
    }

    public void move(int row, int col) {
        Move playerMove = new Move(row, col);
        if(!isLegal(playerMove, Fields.player)) {
            return;
        }
        board.setPiece(playerMove, Fields.player);
        flipPieces(getFlips(playerMove, Fields.player), Fields.player);
        Fields.nextMove();

        if(Fields.AiMode) {
        }
    }

    public void flipPieces(ArrayList<Move> moves, int piece) {
        for(int i =0; i < moves.size(); i++) {
            board.setPiece(moves.get(i), piece);
        }
    }

    public ArrayList<Move> getFlips(Move move, int piece) {
        int row = move.row;
        int col = move.col;
        int oppPiece = (piece == 1) ? 2 : 1;
        ArrayList<Move> flippedPieces = new ArrayList<Move>();

        for(int[] dir : directions) {
            ArrayList<Move> tmpMoves = new ArrayList<Move>();
            boolean hasAdj = false, hasPiece = false;
            for(int count = 1;; count++) {
                int tmpRow = row + dir[0] * count, tmpCol = col + dir[1] * count;

                if(board.getPieceAt(tmpRow, tmpCol) == Fields.EMPTY) break;
                if(board.getPieceAt(tmpRow, tmpCol) == oppPiece) {
                    tmpMoves.add(new Move(tmpRow, tmpCol));
                    hasAdj = true;
                }
                if(board.getPieceAt(tmpRow, tmpCol) == piece) hasPiece = true;
            }

            if(hasAdj && hasPiece) {
                flippedPieces.addAll(tmpMoves);
            }
        }
        return flippedPieces;
    }

    public boolean isLegal(Move move, int piece) {
        int row = move.row;
        int col = move.col;
        int oppPiece = (piece == 1) ? 2 : 1;
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
