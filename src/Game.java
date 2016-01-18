import javax.swing.*;

/**
 * Created by Gregory on 1/18/2016.
 */
public class Game {

    private JFrame frame;
    private Board board;

    public static void main(String[] args) {
        Game game = new Game();
        game.openWindow();
    }

    public Game() {
        board = new Board(8, 8, 86);
    }

    public void openWindow() {
        frame = new JFrame();
        frame.setTitle("Othello Steam");
        frame.setResizable(false);
        frame.add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
