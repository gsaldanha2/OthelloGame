import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Gregory on 1/18/2016.
 */
//this is the main class
public class Game {

    private JFrame frame, startFrame;
    private Board board;

    public static void main(String[] args) {
        Game game = new Game();
        game.openWindow();
    }

    public Game() {
        board = new Board(8, 8, 86);
    }

    public void openWindow() {
        final JCheckBox aiBox = new JCheckBox("Ai vs Ai");
        JPanel panel = new JPanel();
        JButton btn = new JButton("Start");
        panel.setPreferredSize(new Dimension(100, 100));
        panel.add(aiBox);
        panel.add(btn);
        startFrame = new JFrame();
        startFrame.setTitle("New Game");
        startFrame.setResizable(false);
        startFrame.add(panel);
        startFrame.pack();
        startFrame.setLocationRelativeTo(null);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setVisible(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(aiBox.isSelected()) {
                    Fields.playType = 0;
                }
                startFrame.dispose();
                startGame();
            }
        });
    }

    public void startGame() {
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
