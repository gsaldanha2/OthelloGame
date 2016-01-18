import java.util.ArrayList;

/**
 * Created by Gregory on 1/17/2016.
 */
public class BoardManager {

    public static int[] makeAIMove(int player, int[][] board) {
        int looksAhead = 2;
        int totalPoints = 0, totalPlayerPoints = 0, lastBestMove = 0, lastBestPlayerMove = 0;
        ArrayList<Integer> legalMovesX = new ArrayList<Integer>(), legalMovesY = new ArrayList<Integer>();

        for (int i = 0; i < looksAhead; i++) {
            int bestMove = 0, bestPlayerMove = 0, nextBestMove = 0, nextBestPlayerMove = 0;
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board.length; c++) {
                    if (isLegalMove(c, r, player, board)) {
                        legalMovesX.add(c);
                        legalMovesY.add(r);
                    }
                }
            }

            for (int count = 0; count < legalMovesX.size(); count++) {
                ArrayList[] arrayLists = flipPieces(legalMovesX.get(count), legalMovesY.get(count), player, board);
                int flips = arrayLists[0].size();
                if (flips > bestMove)
                    bestMove = flips;
                else if (flips > nextBestMove)
                    nextBestMove = flips;
            }


        }
        //doesnt work
        int[] output = {0,0};
        return output;
    }

    public static ArrayList[] flipPieces(int x, int y, int player, int[][] board) {
        int middlePieces = 0;
        boolean pieceInline = false;
        ArrayList<Integer> xFlips = new ArrayList();
        ArrayList<Integer> yFlips = new ArrayList();
        ArrayList<Integer> tempXFlips = new ArrayList();
        ArrayList<Integer> tempYFlips = new ArrayList();

        if (y > 0) {
            tempXFlips.clear();
            tempYFlips.clear();
            //check above
            for (int row = y - 1; (row >= 0 && board[row][x] != -1); row--) {
                if (board[row][x] == player) {
                    pieceInline = true;
                    break;
                }
                tempYFlips.add(row);
                tempXFlips.add(x);
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline) {
                xFlips.addAll(tempXFlips);
                yFlips.addAll(tempYFlips);
            }

            tempXFlips.clear();
            tempYFlips.clear();
            //up left
            middlePieces = 0;
            pieceInline = false;
            int smallerCoord = (x <= y) ? x : y;
            for (int counter = 1; smallerCoord - counter >= 0; counter++) {
                if (board[y - counter][x - counter] == -1)
                    break;
                if (board[y - counter][x - counter] == player) {
                    pieceInline = true;
                    break;
                }
                tempYFlips.add(y - counter);
                tempXFlips.add(x - counter);
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline) {
                xFlips.addAll(tempXFlips);
                yFlips.addAll(tempYFlips);
            }
            tempXFlips.clear();
            tempYFlips.clear();
            //up right
            middlePieces = 0;
            pieceInline = false;
            for (int counter = 1; (x + counter < board.length && y - counter >= 0); counter++) {
                if (board[y - counter][x + counter] == -1)
                    break;
                if (board[y - counter][x + counter] == player) {
                    pieceInline = true;
                    break;
                }
                tempYFlips.add(y - counter);
                tempXFlips.add(x + counter);
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline) {
                xFlips.addAll(tempXFlips);
                yFlips.addAll(tempYFlips);
            }
        }

        if (y < board.length - 1) {
            tempXFlips.clear();
            tempYFlips.clear();
            //check below
            pieceInline = false;
            middlePieces = 0;
            for (int row = y + 1; (row < board.length && board[row][x] != -1); row++) {
                if (board[row][x] == player) {
                    pieceInline = true;
                    break;
                }
                tempYFlips.add(row);
                tempXFlips.add(x);
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline) {
                xFlips.addAll(tempXFlips);
                yFlips.addAll(tempYFlips);
            }
            tempXFlips.clear();
            tempYFlips.clear();
            //down left
            middlePieces = 0;
            pieceInline = false;
            for (int counter = 1; (x - counter >= 0 && y + counter < board.length); counter++) {
                if (board[y + counter][x - counter] == -1)
                    break;
                if (board[y + counter][x - counter] == player) {
                    pieceInline = true;
                    break;
                }
                tempYFlips.add(y + counter);
                tempXFlips.add(x - counter);
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline) {
                xFlips.addAll(tempXFlips);
                yFlips.addAll(tempYFlips);
            }
            tempXFlips.clear();
            tempYFlips.clear();
            //down right
            middlePieces = 0;
            pieceInline = false;
            for (int counter = 1; (x + counter < board.length && y + counter < board.length); counter++) {
                if (board[y + counter][x + counter] == -1)
                    break;
                if (board[y + counter][x + counter] == player) {
                    pieceInline = true;
                    break;
                }
                tempYFlips.add(y + counter);
                tempXFlips.add(x + counter);
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline) {
                xFlips.addAll(tempXFlips);
                yFlips.addAll(tempYFlips);
            }
        }

        if (x > 0) {
            tempXFlips.clear();
            tempYFlips.clear();
            //check left
            pieceInline = false;
            middlePieces = 0;
            for (int column = x - 1; (column >= 0 && board[y][column] != -1); column--) {
                if (board[y][column] == player) {
                    pieceInline = true;
                    break;
                }
                tempYFlips.add(y);
                tempXFlips.add(column);
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline) {
                xFlips.addAll(tempXFlips);
                yFlips.addAll(tempYFlips);
            }
        }

        if (x < board.length - 1) {
            tempXFlips.clear();
            tempYFlips.clear();
            //check right
            pieceInline = false;
            middlePieces = 0;
            for (int column = x + 1; (column < board.length && board[y][column] != -1); column++) {
                if (board[y][column] == player) {
                    pieceInline = true;
                    break;
                }
                tempYFlips.add(y);
                tempXFlips.add(column);
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline) {
                xFlips.addAll(tempXFlips);
                yFlips.addAll(tempYFlips);
            }

        }
        ArrayList[] output = {xFlips, yFlips};
        return output;
    }

    public static boolean isLegalMove(int x, int y, int player, int[][] board) {
        int middlePieces = 0;
        boolean pieceInline = false;

        if (board[y][x] != -1) {
            return false;
        }

        if (y > 0) {
            //check above
            for (int row = y - 1; (row >= 0 && board[row][x] != -1); row--) {
                if (board[row][x] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;

            //up left
            middlePieces = 0;
            pieceInline = false;
            int smallerCoord = (x <= y) ? x : y;
            for (int counter = 1; smallerCoord - counter >= 0; counter++) {
                if (board[y - counter][x - counter] == -1)
                    break;
                if (board[y - counter][x - counter] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;

            //up right
            middlePieces = 0;
            pieceInline = false;
            for (int counter = 1; (x + counter < board.length && y - counter >= 0); counter++) {
                if (board[y - counter][x + counter] == -1)
                    break;
                if (board[y - counter][x + counter] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;
        }

        if (y < board.length - 1) {
            //check below
            pieceInline = false;
            middlePieces = 0;
            for (int row = y + 1; (row < board.length && board[row][x] != -1); row++) {
                if (board[row][x] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;

            //down left
            middlePieces = 0;
            pieceInline = false;
            for (int counter = 1; (x - counter >= 0 && y + counter < board.length); counter++) {
                if (board[y + counter][x - counter] == -1)
                    break;
                if (board[y + counter][x - counter] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;

            //down right
            middlePieces = 0;
            pieceInline = false;
            for (int counter = 1; (x + counter < board.length && y + counter < board.length); counter++) {
                if (board[y + counter][x + counter] == -1)
                    break;
                if (board[y + counter][x + counter] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;
        }

        if (x > 0) {
            //check left
            pieceInline = false;
            middlePieces = 0;
            for (int column = x - 1; (column >= 0 && board[y][column] != -1); column--) {
                if (board[y][column] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;
        }

        if (x < board.length - 1) {
            //check right
            pieceInline = false;
            middlePieces = 0;
            for (int column = x + 1; (column < board.length && board[y][column] != -1); column++) {
                if (board[y][column] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;
        }

        return false;
    }
}
