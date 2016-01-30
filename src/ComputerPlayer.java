import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        this.max = player;
        min = (max == 1) ? 2 : 1;
        maxDepth = d;
    }

    public void move(Board board) {
        //move ai
        if (othello.getAllMoves(max, board).size() == 0) {
            System.out.println("No legal moves");
            return;
        }
        Move bestMove = minimax(board);
        makeMove(bestMove, board, max);
    }

    public void makeMove(Move move, Board board, int piece) {
        board.setPiece(move, piece);
        othello.flipPieces(othello.getFlips(move, piece, board), piece, board);
    }

    public Move minimax(Board orgBoard) {
        ArrayList<Move> moves = othello.getAllMoves(max, orgBoard);
        ArrayList<Board> boards = new ArrayList<Board>();

        System.out.println(moves.size());
        ExecutorService executorService = Executors.newFixedThreadPool(moves.size());
        List<Future<Board>> list = new ArrayList<Future<Board>>();

        for(Move move : moves) {
            Callable<Board> callable = new MinimaxProcessor(move, this, othello, orgBoard, min, max, maxDepth);
            Future<Board> future = executorService.submit(callable);
            list.add(future);
        }
        executorService.shutdown();

        for(Future<Board> fut : list) {
            try {
                Board board = fut.get();
                boards.add(board);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        //threads finished
        int bestValue = -1;
        int bestIndex = -1;
        for(Board board : boards) {
            if (othello.evaluate(board, max) > bestValue) {
                bestValue = othello.evaluate(board, max);
                bestIndex = boards.indexOf(board);
                int row = moves.get(bestIndex).row;
                int col = moves.get(bestIndex).col;
                if((col == 0 && (row == 0 || row == board.getBoardHeight())) || (col == board.getBoardWidth() && (row == 0 || row == board.getBoardHeight()))) { //corner bias
                    System.out.println("CORNER");
                    break;
                }
            }
        }
        return moves.get(bestIndex);
    }
}