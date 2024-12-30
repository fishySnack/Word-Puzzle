import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonActionListener implements ActionListener {
    private final String actionType;
    private final GameDriver gameDriver;

    // instead of typine gameDriver.(varName) i could have created a var with
    // of gameDriver.(varName) so i dont have to keep repeating it

    public ButtonActionListener(String actionType, GameDriver gameDriver) {
        this.actionType = actionType;
        this.gameDriver = gameDriver;
    }

    // adds an actionlistener and execute case depending on the actionType
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (actionType) {
            case "play":
                clearAll();
                gameDriver.helpText.setVisible(false);
                // gameDriver.back.setVisible(false);
                gameDriver.menuPanel.setVisible(false);
                gameDriver.puzzlePanel.setVisible(true);

                JPanel topTempPanel = new JPanel();
                JPanel bottomTempPanel = new JPanel();

                topTempPanel.add(gameDriver.wordOutput);
                topTempPanel.add(gameDriver.clear);
                topTempPanel.add(gameDriver.enter);
                bottomTempPanel.add(gameDriver.scoreOutput);
                bottomTempPanel.add(gameDriver.reset);
                bottomTempPanel.add(gameDriver.back);

                gameDriver.frame.add(gameDriver.puzzlePanel, BorderLayout.CENTER);
                gameDriver.frame.add(topTempPanel, BorderLayout.NORTH);
                gameDriver.frame.add(bottomTempPanel, BorderLayout.SOUTH);

                gameDriver.frame.revalidate();
                gameDriver.frame.repaint();
                break;

            case "help":
                gameDriver.menuPanel.setVisible(false);
                gameDriver.puzzlePanel.setVisible(false);

                gameDriver.frame.add(gameDriver.helpText, BorderLayout.CENTER);
                gameDriver.frame.add(gameDriver.back, BorderLayout.SOUTH);

                gameDriver.frame.revalidate();
                gameDriver.frame.repaint();
                break;
            case "reset":
                // clear the puzzlepanel and generate new ones
                gameDriver.puzzlePanel.removeAll();
                GameEngine.letterButtonGenerator(gameDriver);

                gameDriver.puzzlePanel.revalidate();
                gameDriver.puzzlePanel.repaint();
                clearAll();
                // actionType = "clear";// so the clear case is executed
                break;
            case "clear":
                clearWord();
                break;
            case "enter":
                if (gameDriver.match(gameDriver.wordWrapper[0]) && gameDriver.wordWrapper[0].length() > 2
                        && !gameDriver.wordSubmited.contains(gameDriver.wordWrapper[0])) {

                    gameDriver.score += gameDriver.wordWrapper[0].length() * 100;
                    gameDriver.scoreOutput.setText("Score: " + gameDriver.score);
                    gameDriver.wordSubmited.add(gameDriver.wordWrapper[0]);
                    // testing
                    System.out.println("Score after entering word: " + gameDriver.score);
                    System.out.println("Submitted words: " + gameDriver.wordSubmited);
                    System.out.println("Word: " + gameDriver.wordWrapper[0]);

                }
                break;
            case "letterButton":
                JButton source = (JButton) e.getSource();

                if (source.getText().length() == 1) {
                    gameDriver.output[0] = source.getClientProperty("coord");
                    gameDriver.output[1] = source.getText();

                    // System.out.println(GameEngine.isValid(gameDriver.coords, (int[])
                    // gameDriver.output[0],
                    // (String) gameDriver.output[1], gameDriver.word));
                    if (GameEngine.isValid(gameDriver.coords, (int[]) gameDriver.output[0],
                            (String) gameDriver.output[1],
                            gameDriver.word)) {

                        // wrapper needed since its a string
                        gameDriver.wordWrapper[0] = gameDriver.wordWrapper[0] + (String) gameDriver.output[1];
                        gameDriver.wordOutput.setText("Word: " + gameDriver.wordWrapper[0]);
                    }
                    // testing
                    System.out.println("Coordinates: " + Arrays.toString((int[]) gameDriver.output[0]));
                    gameDriver.coords.forEach(coord -> System.out.println(Arrays.toString(coord)));

                    System.out.println("Letter:" + (String) gameDriver.output[1]);

                    break;

                }
            case "back":
                gameDriver.initialize();
                break;

            default:
                System.out.println("Unknown action!");
        }
    }

    // for clear
    private void clearWord() {
        gameDriver.coords.clear();// clear the cords since all the words will be valid again.
        gameDriver.wordWrapper[0] = "";
        gameDriver.wordOutput.setText("Word: " + gameDriver.wordWrapper[0]);
        System.out.println("wrapper: " + gameDriver.wordWrapper[0]);
    }

    // for reset and new game
    private void clearAll() {
        gameDriver.score = 0;
        gameDriver.scoreOutput.setText("Score: " + gameDriver.score);
        clearWord();
    }
}
