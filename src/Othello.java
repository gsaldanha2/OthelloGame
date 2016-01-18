import javax.swing.*;
import java.awt.*;

/**
 * Created by Gregory on 1/17/2016.
 */
public class Othello extends JPanel {

    //Objects
    public JFrame frame = new JFrame();

    private Board gameBoard;
    private Options options;

    //Variables
    public static final int WIDTH = 800, HEIGHT = 800;
    private boolean running = false;

    public static void main(String[] args) {
        Othello game = new Othello();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        game.frame.setTitle("Othello - Steam Project");
        game.frame.setResizable(false);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setLocationRelativeTo(null);
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setVisible(true);
        game.start();
    }

    public Othello() {
        //init
        super(true);
        gameBoard = new Board(8, 10);
        options = new Options();
        options.setBackgroundColor(Color.BLACK);
        options.setLineColor(Color.ORANGE);
        options.setPlayer1Color(Color.WHITE);
        options.setPlayer2Color(Color.CYAN);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(options.getBackgroundColor());
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(options.getLineColor());
        for (int r = 0; r < gameBoard.getSize(); r++) {
            for (int c = 0; c < gameBoard.getSize(); c++) {
                g.drawRect(r * gameBoard.getTileSize(), c * gameBoard.getTileSize(), gameBoard.getTileSize(), gameBoard.getTileSize());
            }
        }

        for(int r = 0; r < gameBoard.getSize(); r++) {
            for(int c = 0; c < gameBoard.getSize(); c++) {
                if(gameBoard.board[r][c] == -1)
                    continue;
                if(gameBoard.board[r][c] == 1)
                    g.setColor(options.getPlayer1Color());
                else
                    g.setColor(options.getPlayer2Color());
                g.drawOval(r * gameBoard.getPieceSize() + gameBoard.getPiecePadding(), c * gameBoard.getPieceSize() - gameBoard.getPiecePadding(), gameBoard.getPieceSize(), gameBoard.getPieceSize());
            }
        }
    }

    public synchronized void start() {
        running = true;
        run();
    }

    public void run() {
        while (running) {

        }
    }
}
