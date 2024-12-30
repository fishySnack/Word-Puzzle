import java.awt.Insets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GameEngine {
    // check if the word is a match from the file
    public boolean match(String word) {

        try (Scanner scan = new Scanner(Paths.get("Words.txt"))) {
            while (scan.hasNextLine()) {
                if (word.equals(scan.nextLine()))
                    return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // generate a random capital letter
    public static String generateLetter() {
        Random rand = new Random();
        int randomInt = rand.nextInt(26);
        char randomLetter = (char) ('A' + randomInt);

        return "" + randomLetter;

    }

    // generate the coords for each button
    public static int[][] coordGenerater() {
        int[][] output = new int[16][2];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                output[index] = new int[] { i, j };
                index++;
            }
        }
        return output;
    }

    // check if the button is pressed is valid from the list of coords
    public static boolean isValid(ArrayList<int[]> coords, int[] coord, String buttonLetter, String word) {
        if (coords.size() == 0) {
            coords.add(coord);
            return true;
        }
        for (int[] usedCoord : coords) {
            if (Arrays.equals(usedCoord, coord)) {
                System.out.println("Letter already used");
                return false;
            }
        }

        int[] lastCoord = coords.get(coords.size() - 1);

        int[] xFlipper = { 0, 0, 1, 1, 1, -1, -1, -1 };
        int[] yFlipper = { 1, -1, 1, 0, -1, 1, 0, -1 };

        // Check if the new coordinate is adjacent to the last one
        for (int i = 0; i < xFlipper.length; i++) {
            int newX = lastCoord[0] + xFlipper[i];
            int newY = lastCoord[1] + yFlipper[i];
            if (coord[0] == newX && coord[1] == newY) {
                coords.add(coord);
                return true;
            }
        }
        return false;
    }

    // "center" the text pane
    public static void centerTextPane(JTextPane textPane, int type) {

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();

        StyleConstants.setBold(attributeSet, true);
        if (type == 1) {
            StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(0, doc.getLength(), attributeSet, false);
            textPane.setMargin(new Insets(20, 0, 20, 0));

        }
        if (type == 2) {
            StyleConstants.setFontSize(attributeSet, 20);
            StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), attributeSet, false);

        }

        textPane.setFocusable(false);

    }

    // generate the button with the letter with 2 vowels min.
    public static void letterButtonGenerator(GameDriver gameDriver) {
        char[] vowels = { 'A', 'E', 'I', 'O', 'U' };// screw 'y'
        // generate 3 number where the letter will be a vowel
        ArrayList<Integer> generateVowels = new ArrayList<>();
        Random rand = new Random();
        while (generateVowels.size() != 2) {
            int randomNumber = rand.nextInt(16);
            if (!generateVowels.contains(randomNumber)) {
                generateVowels.add(randomNumber);
            }
        }

        int[][] coordGenerater = new int[16][2];
        coordGenerater = coordGenerater();
        for (int i = 0; i < 16; i++) {
            JButton button;
            if (generateVowels.contains(i)) {
                int randomNumber = rand.nextInt(5);
                button = new JButton("" + vowels[randomNumber]);
            } else {
                button = new JButton(generateLetter());
            }

            button.putClientProperty("coord", coordGenerater[i]);

            button.addActionListener(new ButtonActionListener("letterButton", gameDriver));
            gameDriver.puzzlePanel.add(button);
        }

    }

}
