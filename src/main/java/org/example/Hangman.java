package org.example;

import jdk.javadoc.doclet.Taglet;

import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Hangman extends JFrame implements ActionListener {
    private JLabel hangmanImage, categoryLabel, hiddenWordLabel;
    private JDialog resultDialog;
    private JButton[] letterButtons;
    private Words words;
    private int wrongAnswers;
    private String[] wordChallange;
    private JLabel resultLabel;
    private JLabel wordLabel;

    public Hangman() {
        super("Hangman Game 2.0 (Java)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        ;
        setSize(540, 760);
        setLayout(null);

        words = new Words();
        wordChallange = words.LoadChallange();
        wrongAnswers=0;
        addComponents();
        createResultDialog();
    }

    private void createResultDialog(){
        resultDialog = new JDialog();
        resultDialog.setTitle("Result");
        resultDialog.setSize(CustomTools.RESULT_DIALOG_SIZE);
        resultDialog.getContentPane().setBackground(Color.BLUE);
        resultDialog.setResizable(false);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setModal(true);
        resultDialog.setLayout(new GridLayout(3, 1));


        resultLabel = new JLabel();
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        wordLabel = new JLabel();
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(Color.CYAN);
        restartButton.addActionListener(this);

        resultDialog.add(resultLabel);
        resultDialog.add(wordLabel);
        resultDialog.add(restartButton);
        resultDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                restartGame();

            }
        });
    }
    public void addComponents() {
        hangmanImage = CustomTools.loadImage("1.png");
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);
        add(hangmanImage);

        categoryLabel = new JLabel(wordChallange[0]);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setBackground(Color.blue);
        categoryLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        categoryLabel.setForeground(Color.white);
        categoryLabel.setOpaque(true);
        categoryLabel.setBounds(0, 10 + hangmanImage.getPreferredSize().height, 540, 50);
        add(categoryLabel);

        hiddenWordLabel = new JLabel(CustomTools.hideWords(wordChallange[1]));
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBackground(Color.black);
        hiddenWordLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        hiddenWordLabel.setForeground(Color.white);
        hiddenWordLabel.setOpaque(true);
        hiddenWordLabel.setBounds(0, 30 + categoryLabel.getPreferredSize().height + hangmanImage.getPreferredSize().height, 540, 50);
        add(hiddenWordLabel);

        letterButtons = new JButton[26];
        GridLayout gridLayout = new GridLayout(4, 7);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(
                -5,
                35 + hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CustomTools.BUTTON_PANEL_SIZE.width,
                CustomTools.BUTTON_PANEL_SIZE.height);
        buttonPanel.setLayout(gridLayout);

        // create the letter buttons
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(Character.toString(c));
            button.setBackground(Color.BLUE);
            button.setFont(new Font("Dialog", Font.BOLD, 24));
            button.setForeground(Color.WHITE);
            button.addActionListener(this);

            // using ASCII values to caluclate the current index
            int currentIndex = c - 'A';

            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }
        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Dialog", Font.BOLD, 12));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(Color.BLUE);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        // quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Dialog", Font.BOLD, 12));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(Color.BLUE);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);
        add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comand = e.getActionCommand();
        if (comand.equals("Reset") || comand.equals("Restart")) restartGame();
        else if (comand.equals("Quit")) {
            dispose();
            return;
        } else {
            // disable button
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);

            // check if the word contains the user's guess,
            if (wordChallange[1].contains(comand)) {
                // indicate that the user got it right
                button.setBackground(Color.GREEN);

                // store the hidden word in a char array, so update the hidden text
                char[] hiddenWord = hiddenWordLabel.getText().toCharArray();

                for (int i = 0; i < wordChallange[1].length(); i++) {
                    // update _ to correct letter
                    if (wordChallange[1].charAt(i) == comand.charAt(0)) {
                        hiddenWord[i] = comand.charAt(0);
                    }
                }

                // update hiddenWordLabel
                hiddenWordLabel.setText(String.valueOf(hiddenWord));

                // the user guessed the word right
                if (!hiddenWordLabel.getText().contains("*")) {
                    // display dialog with success result
                    resultLabel.setText("You got it right!");
                    resultDialog.setVisible(true);
                }

            } else {
                // indicate that the user chose the wrong letter
                button.setBackground(Color.RED);

                // increase incorrect counter
                wrongAnswers++;

                // update hangman image
              CustomTools.updateImage(hangmanImage,(wrongAnswers + 1) + ".png");



                // user failed to guess word right
                if (wrongAnswers >= 6) {
                    // display result dialog with game over label
                    resultLabel.setText("Too Bad, Try Again?");
                    resultDialog.setVisible(true);
                }
            }
            wordLabel.setText("Word: " + wordChallange[1]);
        }

        

    }

    public void restartGame()
    {
        // load new challenge
        wordChallange = words.LoadChallange();
        wrongAnswers = 0;

        // load starting image
        CustomTools.updateImage(hangmanImage,"1.png");

        // update category
        categoryLabel.setText(wordChallange[0]);

        // update hiddenWord
        String hiddenWord = CustomTools.hideWords(wordChallange[1]);
        hiddenWordLabel.setText(hiddenWord);

        // enable all buttons again
        for (int i = 0; i < letterButtons.length; i++) {
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackground(Color.BLUE);
        }
    }
}
