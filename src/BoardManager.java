/**
 * Created by Gregory on 1/17/2016.
 */
public class BoardManager {

    public static boolean isLegalMove(int x, int y, int player, int[][] board) {
        int middlePieces = 0;
        boolean pieceInline = false;

        if(board[y][x] != -1) {
            return false;
        }

        if(y > 0) {
            //check above
            for (int row = y - 1;(row >= 0 && board[row][x] != -1); row--) {
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
            for(int counter = 1; smallerCoord - counter >= 0; counter++) {
                if(board[y - counter][x-counter] == -1)
                    break;
                if(board[y - counter][x-counter] == player) {
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
            for(int counter = 1; (x + counter < board.length && y - counter >= 0); counter++) {
                if(board[y - counter][x + counter] == -1)
                    break;
                if(board[y - counter][x + counter] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;
        }

        if(y < board.length - 1) {
            //check below
            pieceInline = false;
            middlePieces = 0;
            for (int row = y + 1;(row < board.length && board[row][x] != -1); row++) {
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
            for(int counter = 1; (x - counter >= 0 && y + counter < board.length); counter++) {
                if(board[y + counter][x - counter] == -1)
                    break;
                if(board[y + counter][x - counter] == player) {
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
            for(int counter = 1; (x + counter < board.length && y + counter < board.length); counter++) {
                if(board[y + counter][x + counter] == -1)
                    break;
                if(board[y + counter][x + counter] == player) {
                    pieceInline = true;
                    break;
                }
                middlePieces++;
            }
            if (middlePieces > 0 && pieceInline)
                return true;
        }

        if(x > 0) {
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

        if(x < board.length - 1) {
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
