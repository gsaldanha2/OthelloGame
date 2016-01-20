import java.util.ArrayList;

/**
 * Created by Gregory on 1/19/2016.
 */
public class ComputerPlayer {
    private Othello othello;
    private Board tmpBoard;
    private int depth = 0;
    private int maxDepth = 10;
    private int looks = 0;
    private ArrayList<Move> rootMoves;
    private Move bestPossibleMove;

    public ComputerPlayer(Othello othello) {
        this.othello = othello;
    }

    public void move(Board board) {
        System.out.println("AI MOVING");
    }


}