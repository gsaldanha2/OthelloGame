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
    public static JPanel optionsPanel;
    public static JLabel scoreLabel, gameOverLabel;
    private final String[] levels = {"Easy", "Medium", "Hard", "Insane"};

    public static void main(String[] args) {
        Game game = new Game();
        game.openWindow();
    }

    public void openWindow() {

        final JCheckBox aiBox = new JCheckBox("Ai vs Ai");
        JButton btn = new JButton("Start");
        final JComboBox diffCombo = new JComboBox(levels);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 300));
        panel.add(aiBox);
        panel.add(btn);
        panel.add(diffCombo);

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
                String level = diffCombo.getSelectedItem().toString();
                if(level.equalsIgnoreCase("easy"))
                    Fields.difficulty = 1;
                else if(level.equalsIgnoreCase("medium"))
                    Fields.difficulty = 2;
                else if(level.equalsIgnoreCase("hard"))
                    Fields.difficulty = 3;
                else if(level.equalsIgnoreCase("insane"))
                    Fields.difficulty = 4;
                startFrame.dispose();
                startGame();
            }
        });
    }

    public void startGame() {
        scoreLabel = new JLabel("Score: " + Fields.player1score + " | " + Fields.player2score + " | Empty: " + 60);
        gameOverLabel = new JLabel("Running");
        JButton changeColors = new JButton("Colors");
        final JComboBox colorSelector = new JComboBox(new String[] {"P1", "P2", "Line", "Background"});
        optionsPanel = new JPanel();
        optionsPanel.setPreferredSize(new Dimension(150, 300));
        optionsPanel.add(scoreLabel);
        optionsPanel.add(gameOverLabel);
        optionsPanel.add(colorSelector);
        optionsPanel.add(changeColors);
        optionsPanel.setBackground(Fields.whiteColor);
        JFrame optionsFrame = new JFrame();
        optionsFrame.setTitle("Game Info");
        optionsFrame.setResizable(false);
        optionsFrame.add(optionsPanel);
        optionsFrame.pack();
        optionsFrame.setLocation(1320, 300);
        optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionsFrame.setVisible(true);

        //create the board
        board = new Board(8, 8, 86);

        System.out.println("Difficulty: " + Fields.difficulty);

        frame = new JFrame();
        frame.setTitle("Othello Steam");
        frame.setResizable(false);
        frame.add(board);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        changeColors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String colorToChange = colorSelector.getSelectedItem().toString();
                if(colorToChange.equalsIgnoreCase("P1"))
                    Fields.whiteColor = JColorChooser.showDialog(frame, "Choose Player 1 Color", Fields.whiteColor);
                else if(colorToChange.equalsIgnoreCase("P2"))
                    Fields.blackColor = JColorChooser.showDialog(frame, "Choose Player 2 Color", Fields.blackColor);
                else if(colorToChange.equalsIgnoreCase("Line"))
                    Fields.lineColor = JColorChooser.showDialog(frame, "Choose Line Color", Fields.lineColor);
                else if(colorToChange.equalsIgnoreCase("Background"))
                    Fields.bgColor = JColorChooser.showDialog(frame, "Choose Background Color", Fields.bgColor);
                board.repaint();
            }
        });
    }

}
