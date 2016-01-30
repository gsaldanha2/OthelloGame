import java.util.ArrayList;

/**
 * Created by Gregory on 1/19/2016.
 */
public class ComputerPlayer {
    private Othello othello;
    private int maxDepth = 5;
    private int max = 2;
    private int min = 1;
    int counter = 0;

    public ComputerPlayer(Othello othello, int player, int d) {
        this.othello = othello;
        max = player;
        min = (max == 1) ? 2 : 1;
        maxDepth = d;
    }

    public void move(Board board) {
        //move ai
        if(othello.getAllMoves(max, board).size() == 0){
            System.out.println("No legal moves");
            return;
        }
        Move bestMove = minimax(board);
        makeMove(bestMove, board, max);
        System.out.println(counter);
        counter = 0;
    }

    public void makeMove(Move move, Board board, int piece) {
        board.setPiece(move, piece);
        othello.flipPieces(othello.getFlips(move, piece, board), piece, board);
    }

    public Move minimax(Board orgBoard) {
        ArrayList<Move> moves = othello.getAllMoves(max, orgBoard);
        int bestValue = -1;
        int bestIndex = -1;
        for(Move move : moves) {
            counter++;
            int depth = 0;
            Board board = orgBoard.cloneBoard();
            makeMove(move, board, max);
            Move output = minMove(board, depth);
            if(output == null) continue;
            if(othello.evaluate(board, max) > bestValue) {
                bestValue = othello.evaluate(board, max);
                bestIndex = moves.indexOf(move);
            }
        }
        if(bestIndex == -1) return moves.get(0);
        return moves.get(bestIndex);
    }

    //ai
    public Move maxMove(Board orgBoard, int depth) {
        int bestValue = -1;
        int bestIndex = 0;
        ArrayList<Move> moves = othello.getAllMoves(max, orgBoard);
        if(moves.size() == 0) return null;

        if(!(++depth <= maxDepth)) {
            for(Move move : moves) {
                counter++;
                if(othello.getValue(move, orgBoard, max) > bestValue) {
                    bestValue = othello.getValue(move, orgBoard, max);
                    bestIndex = moves.indexOf(move);
                }
            }
            makeMove(moves.get(bestIndex), orgBoard, max);
            return moves.get(bestIndex);
        }

        for(Move move : moves) {
            Board board = orgBoard.cloneBoard();
            makeMove(move, board, max);
            Move output = minMove(board, depth);
            if(output == null) return null;
            if(othello.getValue(output, board, max) > bestValue) {
                bestValue = othello.getValue(output, board, max);
                bestIndex = moves.indexOf(move);
            }
        }
        makeMove(moves.get(bestIndex), orgBoard, max);
        return moves.get(bestIndex);
    }

    //player
    public Move minMove(Board orgBoard, int depth) {
        depth++;
        ArrayList<Move> moves = othello.getAllMoves(min, orgBoard);
        if(moves.size() == 0) return null;
        int bestValue = -1;
        int bestIndex = 0;
        for(Move move : moves) {
            counter++;
            Board board = orgBoard.cloneBoard();
            makeMove(move, board, min);
            Move output = maxMove(board, depth);
            if(output == null) continue;
            if(othello.getValue(output, board, min) > bestValue) {
                bestValue = othello.getValue(output, board, min);
                bestIndex = moves.indexOf(move);
            }
        }
        makeMove(moves.get(bestIndex), orgBoard, min);
        return moves.get(bestIndex);
    }
}