import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Gregory on 1/19/2016.
 */
public class ComputerPlayer {
    private Othello othello;
    private int maxLooks = 5;
    private int max = 2;
    private int min = 1;
    private int looksAhead = 0;
    private Node mainNode;

    public ComputerPlayer(Othello othello, int player, int d) {
        this.othello = othello;
        this.max = player;
        min = (max == 1) ? 2 : 1;
        maxDepth = d;
    }

    public void move(Board board) {
        //move ai
        if (board.getAllMoves(max).size() == 0) {
            System.out.println("No legal moves");
            return;
        }
        if(Fields.useMinimax()) {
            Move bestMove = minimax(board);
            board.makeMove(bestMove, max);
        }else if(Fields.useRandom()) {
            Move move = random(board);
            board.makeMove(move, max);
        }else if(Fields.useCasual()) {
            Move move = casual(board);
            board.makeMove(move, max);
        }
    }

    public Move random(Board board) {
        ArrayList<Move> moves = board.getAllMoves(max);
        if(Fields.useCorners()) {
            for (Move move : moves) {
                int row = move.row;
                int col = move.col;
                if((col == 0 && (row == 0 || row == board.getBoardHeight()-1)) || (col == board.getBoardWidth() -1 && (row == 0 || row == board.getBoardHeight()-1))) { //corner bias
                    return move;
                }
            }
        }
        Random random = new Random();
        return moves.get(random.nextInt(moves.size()));
    }

    public Move casual(Board board) {
        ArrayList<Move> moves = board.getAllMoves(max);
        int bestValue = -1;
        int bestIndex = -1;
        for(Move move : moves) {
            int val = othello.getValue(move, board, max);
            if(val > bestValue) {
                bestValue = val;
                bestIndex = moves.indexOf(move);
                int row = move.row;
                int col = move.col;
                if(Fields.useCorners() && (col == 0 && (row == 0 || row == board.getBoardHeight()-1)) || (col == board.getBoardWidth() -1 && (row == 0 || row == board.getBoardHeight()-1))) { //corner bias
                    break;
                }
            }
        }

        return moves.get(bestIndex);
    }
    
    public Move lookAhead(Board board) {
        simMoves(board);
        //start from leaves and move upwards
    }
    
    //notes. node tree will now generate but the depth variable probably wont work just yet.
    public void simMoves(Board board) {
        looksAhead = 0;
        mainNode = new Node();
        
        ArrayList allMoves = board.getAllMoves(max);
        if(allMoves.size() > 0) {
            simMoves(mainNode, allMoves, board, max, min);
        }
    }
    
    public void simMoves(Node parent, ArrayList<Move> allMoves, Board board, int playerA, int playerB) {
        if(playerA == min) {
            looksAhead++;
        }
        
        if((playerA == max) && !(looksAhead < maxLooks)) {
            return;
        }
        for(Move currMove : moves) {
           Node currNode = new Node(root, currMove);
            root.addChild(currNode);
            //make move
            Board tempBoard = board.cloneBoard();
            tempBoard.makeMove(currMove, playerA);
            //sim opponent move
            ArrayList opponentMoves = tempBoard.getAllMoves(playerB);
            if(opponentMoves.size() > 0) {
                this.simMoves(currNode, opponentMoves, tempBoard, playerB, playerA);
            }
        }
    }

    // //old code
    // public Move minimax(Board orgBoard) {
    //     ArrayList<Move> moves = othello.getAllMoves(max, orgBoard);
    //     ArrayList<Board> boards = new ArrayList<Board>();

    //     ExecutorService executorService = Executors.newFixedThreadPool(moves.size());
    //     List<Future<Board>> list = new ArrayList<Future<Board>>();

    //     for(Move move : moves) {
    //         Callable<Board> callable = new MinimaxProcessor(move, this, othello, orgBoard, min, max, maxDepth);
    //         Future<Board> future = executorService.submit(callable);
    //         list.add(future);
    //     }
    //     executorService.shutdown();

    //     for(Future<Board> fut : list) {
    //         try {
    //             Board board = fut.get();
    //             boards.add(board);
    //         }catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }
    //     //threads finished
    //     int bestValue = -1;
    //     int bestIndex = -1;
    //     for(Board board : boards) {
    //         if (othello.evaluate(board, max) > bestValue) {
    //             bestValue = othello.evaluate(board, max);
    //             bestIndex = boards.indexOf(board);
    //             int row = moves.get(bestIndex).row;
    //             int col = moves.get(bestIndex).col;
    //             if(Fields.useCorners() && (col == 0 && (row == 0 || row == board.getBoardHeight()-1)) || (col == board.getBoardWidth() -1 && (row == 0 || row == board.getBoardHeight()-1))) { //corner bias
    //                 System.out.println("GRABBING CORNER");
    //                 break;
    //             }
    //         }
    //     }
    //     return moves.get(bestIndex);
    // }
}
