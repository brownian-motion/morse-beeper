package com.brownian.morse;

import javax.swing.*;
import javax.sound.midi.*;
import java.awt.*;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int MILLIS_FOR_EACH_CHARACTER = 3000;

    private JLabel displayLabel;
    private LatinReceiver latinReceiver;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.startApp();
        });
    }

    public Main() {
        try{
            latinReceiver = LatinReceiver.getReceiver();
        } catch(MidiUnavailableException e){
            System.err.println("MIDI not available.");
            e.printStackTrace(System.err);
            endApp();
        }
        initGUI();
    }

    /**
     * After the app is created, starts the app.
     */
    private void startApp() {
        setVisible(true);
        displayRandomCharacters();
    }

    /**
     * Periodically displays a random character in this frame's label.
     * @see #displayRandomCharacter()
     */
    private void displayRandomCharacters(){
        new Thread(()->{
            //noinspection InfiniteLoopStatement
            while(true)
                displayRandomCharacter();
        }).start();
    }

    /**
     * Displays a random character in this frame's label.
     */
    private void displayRandomCharacter() {
        char randomChar = generateRandomAlphaCharacter();
        displayLabel.setText(String.valueOf(randomChar));
        try {
            Thread.sleep(MILLIS_FOR_EACH_CHARACTER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chooses a random letter between 'A' and 'Z'.
     * @return a random letter between 'A' and 'Z'
     */
    private char generateRandomAlphaCharacter(){
        return (char) ((int) (Math.random() * 26) + 'A');
    }

    /**
     * Ends the application.
     */
    private void endApp() {
        System.exit(0);
    }

    /**
     * Initializes the GUI elements of the application.
     */
    private void initGUI() {
        setSize(300, 200);

        getContentPane().add(displayLabel = new MorseSoundingLabel(latinReceiver));
        displayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        displayLabel.setVerticalAlignment(SwingConstants.CENTER);
        displayLabel.setFont(new Font("Serif", Font.PLAIN, 36));
        displayLabel.setVisible(true);
    }
}
