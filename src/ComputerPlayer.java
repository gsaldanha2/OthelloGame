import java.util.ArrayList;

/**
 * Created by Gregory on 1/19/2016.
 */
public class ComputerPlayer {
    private Othello othello;

    public ComputerPlayer(Othello othello) {
        this.othello = othello;
    }

    public void move() {
        //move ai
        
    }
    
    public Move maxMove(Board boardState) {
        ArrayList<Move> allMoves = othello.getAllLegalMoves(Fields.AI);
    }


}
