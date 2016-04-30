package com.game.othello;

import java.util.ArrayList;

/**
 * Created by Gregory on 1/18/2016.
 */
//This class handles game logic
public class Othello {

    private int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}};

    public Othello() {
    }

    public int getBoardPieces(Board board) {
        int pieces = 0;
        for (int r = 0; r < board.getBoardHeight(); r++) {
            for (int c = 0; c < board.getBoardWidth(); c++) {
                if (board.getPieceAt(r, c) != Fields.EMPTY)
                    pieces++;
            }
        }
        return pieces;
    }

    public int evaluate(Board board, int player, int starting) {
        int player1Score = 0;
        for (int r = 0; r < board.getBoardHeight(); r++) {
            for (int c = 0; c < board.getBoardWidth(); c++) {
                if (board.getPieceAt(r, c) == player)
                    player1Score++;
            }
        }
        return starting - player1Score;
    }

//    public float evaluate(Board board, int player) {
//        int player1Score = 0;
//        int player2Score = 0;
//        int player2 = (player == 1) ? 2 : 1;
//        for (int r = 0; r < board.getBoardHeight(); r++) {
//            for (int c = 0; c < board.getBoardWidth(); c++) {
//                if (board.getPieceAt(r, c) == player)
//                    player1Score++;
//                if(board.getPieceAt(r, c) == player2) {
//                    player2Score++;
//                }
//            }
//        }
//        if(player2Score > player1Score) {
//            return 1f;
//        }
//        return 0f;
//    }

    public boolean gameEnded(Board board, boolean show) {
        //check if possible legal moves
        for (int r = 0; r < board.getBoardHeight(); r++) {
            for (int c = 0; c < board.getBoardWidth(); c++) {
                if (isLegal(new Move(r, c), Fields.WHITE, board) || isLegal(new Move(r, c), Fields.BLACK, board))
                    return false;
            }
        }
        int player1Score = 0;
        for (int r = 0; r < board.getBoardHeight(); r++) {
            for (int c = 0; c < board.getBoardWidth(); c++) {
                if (board.getPieceAt(r, c) == Fields.WHITE)
                    player1Score++;
            }
        }
        int player2Score = 0;
        for (int r = 0; r < board.getBoardHeight(); r++) {
            for (int c = 0; c < board.getBoardWidth(); c++) {
                if (board.getPieceAt(r, c) == Fields.BLACK)
                    player2Score++;
            }
        }
        if(show) {
            int boardsize = board.getBoardHeight() * board.getBoardWidth();
            if (player1Score > boardsize / 2) {
                Game.gameOverLabel.setText("Player 1 Wins");
            } else if (player1Score == boardsize / 2) {
                Game.gameOverLabel.setText("Draw");
            } else {
                Game.gameOverLabel.setText("Player 2 Wins");
            }
            //debug
//            DebugHandler.writeGameData(player1Score, player2Score);
//            Game.restart();
        }
        Fields.player1score = player1Score;
        Fields.player2score = player2Score;
        return true;
    }

    public int getValue(Move move, Board board, int piece) {
        return getFlips(move, piece, board).size();
    }

    public void flipPieces(ArrayList<Move> moves, int piece, Board board) {
        for (int i = 0; i < moves.size(); i++) {
            board.setPiece(moves.get(i), piece);
        }
    }

    public ArrayList<Move> getFlips(Move move, int piece, Board board) {
        int row = move.getRow();
        int col = move.getCol();
        int oppPiece = (piece == 1) ? 2 : 1;
        ArrayList<Move> flippedPieces = new ArrayList<Move>();

        for (int[] dir : directions) {
            ArrayList<Move> tmpMoves = new ArrayList<Move>();
            boolean hasAdj = false, hasPiece = false;
            for (int count = 1; ; count++) {
                int tmpRow = row + dir[0] * count, tmpCol = col + dir[1] * count;
                if (tmpRow < 0 || tmpCol < 0 || tmpRow >= board.getBoardHeight() || tmpCol >= board.getBoardWidth())
                    break;
                if (board.getPieceAt(tmpRow, tmpCol) == Fields.EMPTY) break;
                if (board.getPieceAt(tmpRow, tmpCol) == oppPiece) {
                    tmpMoves.add(new Move(tmpRow, tmpCol));
                    hasAdj = true;
                }
                if (board.getPieceAt(tmpRow, tmpCol) == piece) {
                    hasPiece = true;
                    break;
                }
            }

            if (hasAdj && hasPiece) {
                flippedPieces.addAll(tmpMoves);
            }
        }
        return flippedPieces;
    }

    public void updateScore(Board board) {
        int player1Score = 0;
        for (int r = 0; r < board.getBoardHeight(); r++) {
            for (int c = 0; c < board.getBoardWidth(); c++) {
                if (board.getPieceAt(r, c) == Fields.WHITE)
                    player1Score++;
            }
        }
        int player2Score = 0;
        for (int r = 0; r < board.getBoardHeight(); r++) {
            for (int c = 0; c < board.getBoardWidth(); c++) {
                if (board.getPieceAt(r, c) == Fields.BLACK)
                    player2Score++;
            }
        }
        Fields.player1score = player1Score;
        Fields.player2score = player2Score;
        Game.scoreLabel.setText("Score: " + Fields.player1score + " v " + Fields.player2score);
    }

    public boolean isLegal(Move move, int piece, Board board) {
        int row = move.getRow();
        int col = move.getCol();
        int oppPiece = (piece == 1) ? 2 : 1;
        if (board.getPieceAt(row, col) != Fields.EMPTY) return false;
        for (int[] dir : directions) {
            boolean hasAdj = false, hasPiece = false;
            for (int count = 1; ; count++) {
                int tmpRow = row + dir[0] * count, tmpCol = col + dir[1] * count;
                if (tmpRow < 0 || tmpCol < 0 || tmpRow >= board.getBoardHeight() || tmpCol >= board.getBoardWidth())
                    break;
                if (board.getPieceAt(tmpRow, tmpCol) == Fields.EMPTY) break;
                if (board.getPieceAt(tmpRow, tmpCol) == oppPiece) hasAdj = true;
                if (board.getPieceAt(tmpRow, tmpCol) == piece) {
                    hasPiece = true;
                    break;
                }
            }

            if (hasAdj && hasPiece) return true;
        }
        return false;
    }

}
