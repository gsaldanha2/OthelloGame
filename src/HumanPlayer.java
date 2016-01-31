/**
 * Created by Gregory on 1/19/2016.
 */
public class HumanPlayer {
    private Othello othello;

    public HumanPlayer(Othello othello) {
        this.othello = othello;
    }

    public void move(Move playerMove, Board board) {
        board.setPiece(playerMove, Fields.currPlayer);
        othello.flipPieces(othello.getFlips(playerMove, Fields.currPlayer, board), Fields.currPlayer, board);
    }
}
