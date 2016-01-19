import java.util.ArrayList;

/**
 * Created by Gregory on 1/18/2016.
 */
//This class handles game logic
public class Othello {

    private Board board;
    private Board tempBoard, backupTempBoard;
    private int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1,0}, {1, 1}};
    private int looksAhead, lookAheadTotal = 2;

    public Othello(Board board) {
        this.board = board;
    }

    public void move(int row, int col, Board board) {
        Move playerMove = new Move(row, col);
        if(!isLegal(playerMove, Fields.player, board)) {
            return;
        }
        board.setPiece(playerMove, Fields.player);
        flipPieces(getFlips(playerMove, Fields.player, board), Fields.player, board);
        Fields.nextMove();
    }

    public void aiLookAhead() {
        ArrayList<Move> aiMoves = getAllMoves(Fields.BLACK, board);
        for(Move move : aiMoves) {
            looksAhead = 0;
            tempBoard = board.cloneBoard();
            aiLookAhead(move);
        }
    }

    public void aiLookAhead(Move move) {
        move(move.row, move.col, tempBoard);
        Fields.nextMove();

        backupTempBoard = tempBoard.cloneBoard();
        ArrayList<Move> playerMoves = getAllMoves(Fields.WHITE, tempBoard);
        int minVal = 0;
        Move bestPlayerMove = playerMoves.get(0);
        for(Move playerMove : playerMoves) {
            move(playerMove.row, playerMove.col, tempBoard);

            ArrayList<Move> aiMoves = getAllMoves(Fields.BLACK, tempBoard);
            int flips = getBestCost(aiMoves, Fields.BLACK, tempBoard);
            if(flips < minVal) {
                bestPlayerMove = playerMove;
            }
        }

        tempBoard = backupTempBoard.cloneBoard();
        move(bestPlayerMove.row,bestPlayerMove.col, tempBoard);
        Fields.nextMove();

        if(++looksAhead < lookAheadTotal) {
            ArrayList<Move> aiMoves = getAllMoves(Fields.BLACK, board);
            for(Move aiMove : aiMoves) {
                aiLookAhead(aiMove);
            }
        }
    }

    public int getBestCost(ArrayList<Move> moves, int piece, Board board) {
        int bestVal = 0;
        int bestIndex = 0;
        for(int i = 0; i < moves.size(); i++) {
            int flips = getFlips(moves.get(i), piece, board).size();
            if(flips > bestVal) {
                bestVal = flips;
                bestIndex = i;
            }
        }
        return bestVal;
    }

    public Move getBestMove(ArrayList<Move> moves, int piece, Board board) {
        int bestVal = 0;
        int bestIndex = 0;
        for(int i = 0; i < moves.size(); i++) {
            int flips = getFlips(moves.get(i), piece, board).size();
            if(flips > bestVal) {
                bestVal = flips;
                bestIndex = i;
            }
        }
        return moves.get(bestIndex);
    }

    public ArrayList<Move> getAllMoves(int piece, Board born) {
        ArrayList<Move> moves = new ArrayList<Move>();
        for(int r = 0; r < board.getBoardHeight(); r++) {
            for(int c = 0; c < board.getBoardWidth(); c++) {
                if(isLegal(new Move(r, c), piece, board))
                    moves.add(new Move(r,c));
            }
        }
        return moves;
    }

    public void flipPieces(ArrayList<Move> moves, int piece, Board board) {
        for(int i =0; i < moves.size(); i++) {
            board.setPiece(moves.get(i), piece);
        }
    }

    public ArrayList<Move> getFlips(Move move, int piece, Board board) {
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

    public boolean isLegal(Move move, int piece, Board board) {
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
