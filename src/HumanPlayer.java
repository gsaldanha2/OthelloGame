/**
 * Created by Gregory on 1/19/2016.
 */
public class HumanPlayer {
    private Othello othello;

    public HumanPlayer(Othello othello) {
        this.othello = othello;
    }

    public void move(Move playerMove, Board board) {
        if(!othello.isLegal(playerMove, Fields.player, board)) {
            return;
        }
        board.setPiece(playerMove, Fields.player);
        othello.flipPieces(othello.getFlips(playerMove, Fields.player, board), Fields.player, board);
        Fields.nextMove();
    }
}
