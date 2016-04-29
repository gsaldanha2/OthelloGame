package com.game.othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Gregory on 1/18/2016.
 */
//this is the main class
public class Game {

    private ArrayList<Component> secondAiOptionsArray = new ArrayList<Component>();
    private ArrayList<Component> firstAiOptionsArray = new ArrayList<Component>();

    private ImageIcon icon = new ImageIcon(getClass().getResource("icon.png"));

    private JFrame frame, startFrame;
    private Board board;
    public static JPanel optionsPanel;
    public static JLabel scoreLabel, gameOverLabel, currPlayerLabel;
    public static JCheckBox showLegalMoves;
    private final String[] levels = {"Easy", "Medium", "Hard"};

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
        firstAiOptionsArray.add(settingsLabel);
        JLabel settings2Label = new JLabel("AI 2nd Player");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        settings2Label.setFont(new Font("Arial", Font.BOLD, 14));
        secondAiOptionsArray.add(settings2Label);
        JLabel addonsLabel = new JLabel("AI ADDONS");
        firstAiOptionsArray.add(addonsLabel);
        JLabel addonsLabel2 = new JLabel("AI 2nd ADDONS");
        secondAiOptionsArray.add(addonsLabel2);
        JLabel basicLabel = new JLabel("BASIC SETTINGS");
        basicLabel.setFont(new Font("Arial", Font.BOLD, 14));

        ButtonGroup group1 = new ButtonGroup();
        final JCheckBox minimaxBox = new JCheckBox("Minimax", true);
        firstAiOptionsArray.add(minimaxBox);
        final JCheckBox casualBox = new JCheckBox("Casual");
        firstAiOptionsArray.add(casualBox);
        final JCheckBox randomBox = new JCheckBox("Random");
        firstAiOptionsArray.add(randomBox);
        group1.add(minimaxBox);
        group1.add(casualBox);
        group1.add(randomBox);

        final JCheckBox cornerBox = new JCheckBox("Corner Bias", true);
        firstAiOptionsArray.add(cornerBox);

        //second ai options
        ButtonGroup group2 = new ButtonGroup();
        final JCheckBox minimaxBoxOpp = new JCheckBox("Minimax", true);
        secondAiOptionsArray.add(minimaxBoxOpp);
        final JCheckBox casualBoxOpp = new JCheckBox("Casual");
        secondAiOptionsArray.add(casualBoxOpp);
        final JCheckBox randomBoxOpp = new JCheckBox("Random");
        secondAiOptionsArray.add(randomBoxOpp);
        group1.add(minimaxBox);
        group1.add(casualBox);
        group1.add(randomBox);

        final JCheckBox cornerBoxOpp = new JCheckBox("Corner Bias", true);
        secondAiOptionsArray.add(cornerBoxOpp);

        JButton btn = new JButton("Start");
        final JComboBox diffCombo1 = new JComboBox(levels);
        firstAiOptionsArray.add(diffCombo1);
        final JComboBox diffCombo2 = new JComboBox(levels);
        secondAiOptionsArray.add(diffCombo2);

        for (Component c : secondAiOptionsArray) {
            c.setVisible(false);
        }

        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setPreferredSize(new Dimension(300, 600));
        //add elements
        panel.add(basicLabel);
        panel.add(aiBox);
        panel.add(aiPlayerBox);
        panel.add(twoPlayerBox);
        panel.add(settingsLabel);
        panel.add(minimaxBox);
        panel.add(casualBox);
        panel.add(randomBox);
        panel.add(addonsLabel);
        panel.add(cornerBox);
        panel.add(diffCombo1);

        panel.add(settings2Label);
        panel.add(minimaxBoxOpp);
        panel.add(casualBoxOpp);
        panel.add(randomBoxOpp);
        panel.add(addonsLabel2);
        panel.add(cornerBoxOpp);
        panel.add(diffCombo2);

        panel.add(btn);

        startFrame = new JFrame();
        startFrame.setTitle("New Game");
        startFrame.setResizable(false);
        startFrame.add(panel);
        startFrame.pack();
        startFrame.setLocationRelativeTo(null);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setIconImage(icon.getImage());
        startFrame.setVisible(true);

        aiBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Component c : secondAiOptionsArray) {
                    c.setVisible(true);
                }
                for (Component c : firstAiOptionsArray) {
                    c.setVisible(true);
                }
            }
        });

        twoPlayerBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Component c : secondAiOptionsArray) {
                    c.setVisible(false);
                }
                for (Component c : firstAiOptionsArray) {
                    c.setVisible(false);
                }
            }
        });
        aiPlayerBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Component c : secondAiOptionsArray) {
                    c.setVisible(false);
                }
                for (Component c : firstAiOptionsArray) {
                    c.setVisible(true);
                }
            }
        });

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aiBox.isSelected()) {
                    Fields.playType = 0;
                } else if (aiPlayerBox.isSelected()) {
                    Fields.playType = 1;
                } else if (twoPlayerBox.isSelected()) {
                    Fields.playType = 2;
                }

                if (minimaxBox.isSelected())
                    Fields.minimax = true;
                else if (randomBox.isSelected())
                    Fields.random = true;
                else if (casualBox.isSelected())
                    Fields.casual = true;

                if (cornerBox.isSelected())
                    Fields.corners = true;

                if (minimaxBoxOpp.isSelected())
                    Fields.minimaxOpp = true;
                else if (randomBoxOpp.isSelected())
                    Fields.randomOpp = true;
                else if (casualBoxOpp.isSelected())
                    Fields.casualOpp = true;

                if (cornerBoxOpp.isSelected())
                    Fields.cornersOpp = true;

                String level = diffCombo1.getSelectedItem().toString();
                if (level.equalsIgnoreCase("easy"))
                    Fields.difficulty = 1;
                else if (level.equalsIgnoreCase("medium"))
                    Fields.difficulty = 3;
                else if (level.equalsIgnoreCase("hard"))
                    Fields.difficulty = 5;

                level = diffCombo2.getSelectedItem().toString();
                if (level.equalsIgnoreCase("easy"))
                    Fields.difficultyOpp = 1;
                else if (level.equalsIgnoreCase("medium"))
                    Fields.difficultyOpp = 3;
                else if (level.equalsIgnoreCase("hard"))
                    Fields.difficultyOpp = 5;

                startFrame.dispose();
                startGame();
            }
        });
    }

    public void startGame() {
        ColorHandler.readColorData();
        scoreLabel = new JLabel("Score: " + Fields.player1score + " | " + Fields.player2score + " | Empty: " + 60);
        gameOverLabel = new JLabel("Running");
        currPlayerLabel = new JLabel();
        currPlayerLabel.setSize(new Dimension(150, 100));
        currPlayerLabel.setOpaque(true);
        currPlayerLabel.setBackground(Fields.whiteColor);

        showLegalMoves = new JCheckBox("Show Legal Moves", false);
        JButton changeColors = new JButton("Change Color");
        JButton giveUp = new JButton("Give up");
        giveUp.setBackground(Color.yellow);
        final JComboBox colorSelector = new JComboBox(new String[]{"P1", "P2", "Line", "Background"});

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(0, 1));
        optionsPanel.setPreferredSize(new Dimension(150, 8*86));
        optionsPanel.add(showLegalMoves);
        optionsPanel.add(scoreLabel);
        optionsPanel.add(gameOverLabel);
        optionsPanel.add(currPlayerLabel);
        optionsPanel.add(colorSelector);
        optionsPanel.add(changeColors);
        optionsPanel.add(giveUp);

        //create the board
        board = new Board(8, 8, 86);

        System.out.println("Difficulty: " + Fields.difficulty);

        frame = new JFrame();
        frame.setTitle("JOthello - © Gregory Saldanha 2016");
        frame.setResizable(false);
        frame.setSize(8 * 87 + 150, 8 * 90);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(board, BorderLayout.CENTER);
        frame.getContentPane().add(optionsPanel, BorderLayout.EAST);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(icon.getImage());
        frame.setVisible(true);

        giveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ObjButtons[] = {"Yes", "No"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to quit?", "Confirm Exit", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    restart();
                }
            }
        });

        changeColors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String colorToChange = colorSelector.getSelectedItem().toString();
                if (colorToChange.equalsIgnoreCase("P1"))
                    Fields.whiteColor = JColorChooser.showDialog(frame, "Choose Player 1 Color", Fields.whiteColor);
                else if (colorToChange.equalsIgnoreCase("P2"))
                    Fields.blackColor = JColorChooser.showDialog(frame, "Choose Player 2 Color", Fields.blackColor);
                else if (colorToChange.equalsIgnoreCase("Line"))
                    Fields.lineColor = JColorChooser.showDialog(frame, "Choose Line Color", Fields.lineColor);
                else if (colorToChange.equalsIgnoreCase("Background"))
                    Fields.bgColor = JColorChooser.showDialog(frame, "Choose Background Color", Fields.bgColor);
                board.repaint();
                if (Fields.currPlayer == 1) {
                    optionsPanel.setBackground(Fields.whiteColor);
                } else {
                    optionsPanel.setBackground(Fields.blackColor);
                }
                ColorHandler.saveColorData();
            }
        });

        showLegalMoves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.repaint();
            }
        });
    }

    public static void restart() {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar;
        try {
            currentJar = new File(Game.class.getProtectionDomain().getCodeSource().getLocation().toURI());
              /* is it a jar file? */
            if (!currentJar.getName().endsWith(".jar"))
                return;

            /* Build command: java -jar application.jar */
            final ArrayList<String> command = new ArrayList<String>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            try {
                builder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
