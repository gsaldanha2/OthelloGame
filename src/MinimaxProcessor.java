// import java.util.ArrayList;
// import java.util.concurrent.Callable;

// /**
//  * Created by Gregory on 1/30/2016.
//  */
//  //OLD CODE
// public class MinimaxProcessor implements Callable<Board> {

//     private Move rootMove;
//     private ComputerPlayer cpu;
//     private int max, min;
//     private Board board;
//     private Othello othello;
//     private int maxDepth = 1;

//     public MinimaxProcessor(Move rootMove, ComputerPlayer cpu, Othello othello, Board board, int min, int max, int maxDepth) {
//         this.rootMove = rootMove;
//         this.cpu = cpu;
//         this.min = min;
//         this.max = max;
//         this.othello = othello;
//         this.board = board;
//         this.maxDepth = maxDepth;
//     }

//     @Override
//     public Board call() throws Exception {
//         return minimax(board);
//     }

//     public Board minimax(Board orgBoard) {
//         int depth = 0;
//         Board board = orgBoard.cloneBoard();
//         cpu.makeMove(rootMove, board, max);
//         minMove(board, depth);
//         return board;
//     }

//     public Move maxMove(Board orgBoard, int depth) {
//         int bestValue = orgBoard.getBoardHeight() * orgBoard.getBoardWidth();
//         int bestIndex = 0;
//         ArrayList<Move> moves = othello.getAllMoves(max, orgBoard);
//         if (moves.size() == 0) return null;

//         if (depth >= maxDepth) {
//             for (Move move : moves) {
//                 if (othello.getValue(move, orgBoard, max) > bestValue) {
//                     bestValue = othello.getValue(move, orgBoard, max);
//                     bestIndex = moves.indexOf(move);
//                 }
//             }
//             cpu.makeMove(moves.get(bestIndex), orgBoard, max);
//             return moves.get(bestIndex);
//         }

//         for (Move move : moves) {
//             Board board = orgBoard.cloneBoard();
//             cpu.makeMove(move, board, max);
//             minMove(board, depth);
//             if (othello.evaluate(board, max) > bestValue) {
//                 bestValue = othello.evaluate(board, max);
//                 bestIndex = moves.indexOf(move);
//                 int row = moves.get(bestIndex).row;
//                 int col = moves.get(bestIndex).col;
//                 if(Fields.useCorners() && (col == 0 && (row == 0 || row == board.getBoardHeight()-1)) || (col == board.getBoardWidth() -1 && (row == 0 || row == board.getBoardHeight()-1))) { //corner bias
//                     break;
//                 }
//             }
//         }
//         cpu.makeMove(moves.get(bestIndex), orgBoard, max);
//         return moves.get(bestIndex);
//     }

//     public Move minMove(Board orgBoard, int depth) {
//         depth++;
//         ArrayList<Move> moves = othello.getAllMoves(min, orgBoard);
//         if (moves.size() == 0) return null;
//         int bestValue = orgBoard.getBoardHeight() * orgBoard.getBoardWidth();
//         int bestIndex = 0;
//         for (Move move : moves) {
//             Board board = orgBoard.cloneBoard();
//             cpu.makeMove(move, board, min);
//             maxMove(board, depth);
//             //check score
//             if (othello.evaluate(board, max) < bestValue) {
//                 bestValue = othello.evaluate(board, max);
//                 bestIndex = moves.indexOf(move);
//                 int row = moves.get(bestIndex).row;
//                 int col = moves.get(bestIndex).col;
//                 if(Fields.useCorners() && (col == 0 && (row == 0 || row == board.getBoardHeight()-1)) || (col == board.getBoardWidth() -1 && (row == 0 || row == board.getBoardHeight()-1))) { //corner bias
//                     break;
//                 }
//             }
//         }
//         cpu.makeMove(moves.get(bestIndex), orgBoard, min);
//         return moves.get(bestIndex);
//     }
// }
