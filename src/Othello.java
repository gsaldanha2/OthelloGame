/**
 * Created by Gregory on 1/18/2016.
 */
public class Othello {

    private Board board;

    public Othello(Board board) {
        this.board = board;
    }

    public void move(int row, int col) {
        Move playerMove = new Move(row, col, Fields.WHITE, board);
        if(!playerMove.isLegal()) return;
    }
}
