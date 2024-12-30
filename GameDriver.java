import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class GameDriver extends GameEngine {
    static final int WIDTH = 600;
    static final int HEIGHT = 600;

    JFrame frame;
    JPanel menuPanel;
    JPanel puzzlePanel;
    JTextPane wordOutput;
    JTextPane scoreOutput;
    JTextPane helpText;
    JButton enter;
    JButton reset;
    JButton clear;
    JButton back;
    String word = "";
    Object[] output = new Object[2];
    ArrayList<int[]> coords = new ArrayList<>();
    int score = 0;
    String[] wordWrapper = { "" };
    ArrayList<String> wordSubmited = new ArrayList<>();

    public GameDriver() {
        initialize();
    }

    // adds the buttons and initialize all th buttons
    public void initialize() {
        frame = new JFrame("Word Puzzle");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuPanel = new JPanel();
        puzzlePanel = new JPanel();
        JButton play = new JButton("Play");
        JButton help = new JButton("Help");

        JTextPane text = new JTextPane();
        text.setText("Word Puzzle");
        enter = new JButton("Enter");
        clear = new JButton("Clear");
        back = new JButton("Back");
        wordOutput = new JTextPane();
        wordOutput.setText("Word: " + word);
        scoreOutput = new JTextPane();
        scoreOutput.setText("Score: " + score);
        helpText = new JTextPane();
        helpText.setText(
                "Adjecent letter only\n3 letter minimum\nSame node can't be reused\n100 points for each letter of the valid word");
        reset = new JButton("New Game");

        wordOutput.setPreferredSize(new Dimension(300, 50));
        scoreOutput.setPreferredSize(new Dimension(300, 50));
        enter.setPreferredSize(new Dimension(100, 50));
        reset.setPreferredSize(new Dimension(100, 50));
        clear.setPreferredSize(new Dimension(100, 50));
        back.setPreferredSize(new Dimension(100, 50));
        menuPanel.setLayout(new GridLayout(3, 1));
        centerTextPane(wordOutput, 1);
        centerTextPane(scoreOutput, 1);
        centerTextPane(text, 2);
        centerTextPane(helpText, 2);

        menuPanel.add(text);
        menuPanel.add(play);
        menuPanel.add(help);
        frame.add(menuPanel, BorderLayout.CENTER);

        play.addActionListener(new ButtonActionListener("play", this));
        help.addActionListener(new ButtonActionListener("help", this));
        enter.addActionListener(new ButtonActionListener("enter", this));
        clear.addActionListener(new ButtonActionListener("clear", this));
        reset.addActionListener(new ButtonActionListener("reset", this));
        back.addActionListener(new ButtonActionListener("back", this));

        puzzlePanel.setLayout(new GridLayout(4, 4));
        puzzlePanel.setVisible(false);

        letterButtonGenerator(this);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GameDriver();
    }
}
