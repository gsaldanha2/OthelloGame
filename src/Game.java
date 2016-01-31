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

        ButtonGroup group = new ButtonGroup();
        final JRadioButton aiBox = new JRadioButton("AI vs AI");
        final JRadioButton aiPlayerBox = new JRadioButton("Player vs AI", true);
        final JRadioButton twoPlayerBox = new JRadioButton("Player vs Player");
        group.add(aiBox);
        group.add(aiPlayerBox);
        group.add(twoPlayerBox);

        JLabel settingsLabel = new JLabel("AI SETTINGS");
        JLabel addonsLabel = new JLabel("AI ADDONS");
        JLabel basicLabel = new JLabel("BASIC SETTINGS");

        final JCheckBox minimaxBox = new JCheckBox("Minimax", true);
        final JCheckBox casualBox = new JCheckBox("Casual");
        final JCheckBox randomBox = new JCheckBox("Random");

        final JCheckBox cornerBox = new JCheckBox("Corner Bias", true); //addon

        final JCheckBox[] aiModeArray = {minimaxBox, casualBox, randomBox};

        JButton btn = new JButton("Start");
        final JComboBox diffCombo = new JComboBox(levels);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setPreferredSize(new Dimension(300, 500));
        //add elements
        panel.add(basicLabel);
        panel.add(aiBox);
        panel.add(diffCombo);
        panel.add(settingsLabel);
        panel.add(minimaxBox);
        panel.add(casualBox);
        panel.add(randomBox);
        panel.add(addonsLabel);
        panel.add(cornerBox);
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
                }else if(aiPlayerBox.isSelected()) {
                    Fields.playType = 1;
                }else if(twoPlayerBox.isSelected()) {
                    Fields.playType = 2;
                }

                int validInput = 0;
                for(JCheckBox box : aiModeArray) {
                    if(box.isSelected())
                        validInput++;
                }
                if(validInput == 0 || validInput > 1) {
                    System.out.println("INVALID INPUT");
                    return;
                }

                if(minimaxBox.isSelected())
                    Fields.minimax = true;
                else if(randomBox.isSelected())
                    Fields.random = true;
                else if(casualBox.isSelected())
                    Fields.casual = true;

                if(cornerBox.isSelected())
                    Fields.corners = true;

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
        JButton changeColors = new JButton("Change Color");
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
