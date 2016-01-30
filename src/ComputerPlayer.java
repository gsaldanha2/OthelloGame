import java.util.ArrayList;

/**
 * Created by Gregory on 1/19/2016.
 */
public class ComputerPlayer {
    private Othello othello;
    private int maxDepth = 5;
    private int max = 2;
    private int min = 1;

    public ComputerPlayer(Othello othello, int player, int d) {
        this.othello = othello;
        max = player;
        min = (max == 1) ? 2 : 1;
        maxDepth = d;
    }

    public void move(Board board) {
        System.out.println("\n depth: " + maxDepth + "\n");
        //move ai
        System.out.println("Making move");
        if(othello.getAllMoves(max, board).size() == 0){
            System.out.println("No legal moves");
            return;
        }
        Move bestMove = minimax(board);
        System.out.println("Calculated");
        makeMove(bestMove, board, max);
    }

    public void makeMove(Move move, Board board, int piece) {
        board.setPiece(move, piece);
        othello.flipPieces(othello.getFlips(move, piece, board), piece, board);
        System.out.println("Move made");
    }

    public Move minimax(Board orgBoard) {
        ArrayList<Move> moves = othello.getAllMoves(max, orgBoard);
        int bestValue = -1;
        int bestIndex = -1;
        for(Move move : moves) {
            int depth = 0;
            Board board = orgBoard.cloneBoard();
            makeMove(move, board, max);
            Move output = minMove(board, depth);
            if(output == null) continue;
            if(othello.getValue(output, board, max) > bestValue) {
                bestValue = othello.getValue(output, board, max);
                bestIndex = moves.indexOf(move);
            }
        }
        if(bestIndex == -1) return moves.get(0);
        return moves.get(bestIndex);
    }

    //ai
    public Move maxMove(Board board, int depth) {
        System.out.println("Running Max");
        int bestValue = -1;
        int bestIndex = 0;
        ArrayList<Move> moves = othello.getAllMoves(max, board);
        if(moves.size() == 0) return null;

        if(!(++depth <= maxDepth)) {
            for(Move move : moves) {
                if(othello.getValue(move, board, max) > bestValue) {
                    bestValue = othello.getValue(move, board, max);
                    bestIndex = moves.indexOf(move);
                }
            }
            System.out.println(moves);
            return moves.get(bestIndex);
        }

        for(Move move : moves) {
            makeMove(move, board, max);
            Move output = minMove(board, depth);
            if(output == null) return null;
            if(othello.getValue(output, board, max) > bestValue) {
                bestValue = othello.getValue(output, board, max);
                bestIndex = moves.indexOf(move);
            }
        }
        return moves.get(bestIndex);
    }

    //player
    public Move minMove(Board board, int depth) {
        depth++;
        System.out.println("Running Min");
        ArrayList<Move> moves = othello.getAllMoves(min, board);
        if(moves.size() == 0) return null;
        int bestValue = -1;
        int bestIndex = 0;
        for(Move move : moves) {
            makeMove(move, board, min);
            Move output = maxMove(board, depth);
            if(output == null) continue;
            if(othello.getValue(output, board, min) > bestValue) {
                bestValue = othello.getValue(output, board, min);
                bestIndex = moves.indexOf(move);
            }
        }
        return moves.get(bestIndex);
    }


}