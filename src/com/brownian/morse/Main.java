package com.brownian.morse;

import com.sun.istack.internal.Nullable;

import javax.swing.*;
import javax.sound.midi.*;
import java.awt.*;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int MILLIS_FOR_EACH_CHARACTER = 3000;

    private static final int APP_WIDTH = 300;
    private static final int APP_HEIGHT = 200;

    private static final int MAIN_MENU_BUTTON_WIDTH = 50;
    private static final int MAIN_MENU_BUTTON_HEIGHT = 20;

    private volatile boolean isProducingRandomCharacters = false;

    private LatinReceiverAsync latinReceiver;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.startApp();
        });
    }

    public Main() {
        try{
            latinReceiver = LatinReceiverAsync.getReceiver();
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
    }

    /**
     * Periodically displays a random character in this frame's label.
     * @param label the label whose text to modify
     * @see #displayRandomCharacterIn(MorseSoundingLabel, OperationCompletedListener)
     */
    private void displayRandomCharactersIn(final MorseSoundingLabel label){
        isProducingRandomCharacters = true;
        displayRandomCharacterIn(label, new OperationCompletedListener() {
            @Override
            public void onOperationCompleted() {
                if (isProducingRandomCharacters) {
                    label.setText(" "); //wait between characters
                    displayRandomCharacterIn(label, this); //re-use the same listener because it'll check every time
                }
            }
        });
    }

    private void stopDisplayingRandomCharacters(){
        isProducingRandomCharacters = false;
    }

    /**
     * Displays a random character in the given label.
     */
    private void displayRandomCharacterIn(MorseSoundingLabel label, @Nullable OperationCompletedListener listener) {
        char randomChar = generateRandomAlphaCharacter();
        label.setText(String.valueOf(randomChar),listener);
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
        setSize(APP_WIDTH, APP_HEIGHT);

        setupMainMenu();
    }

    private void setupMainMenu() {
        getContentPane().removeAll();
        JPanel mainMenuPanel = new JPanel();
        final JButton randomCharacterButton = new JButton("Listen to random characters");
        randomCharacterButton.setAlignmentX(0.75f);
        randomCharacterButton.setVerticalAlignment(SwingConstants.CENTER);
        randomCharacterButton.addActionListener(actionEvent-> setupRandomAlphaCharacters());
        mainMenuPanel.add(randomCharacterButton);
        getContentPane().add(mainMenuPanel);
        validate();
        repaint();
    }

    private void setupRandomAlphaCharacters(){
        getContentPane().removeAll();

        JPanel randomAlphaCharactersPanel = new JPanel();
        randomAlphaCharactersPanel.setLayout(new BorderLayout());

        MorseSoundingLabel soundingLabel = makeSoundingLabel(latinReceiver);
        randomAlphaCharactersPanel.add(soundingLabel,BorderLayout.CENTER);
        SwingUtilities.invokeLater(()->displayRandomCharactersIn(soundingLabel));

        JButton mainMenuButton = makeMainMenuButton();
        mainMenuButton.addActionListener(actionEvent->stopDisplayingRandomCharacters());
        randomAlphaCharactersPanel.add(mainMenuButton, BorderLayout.PAGE_START);

        getContentPane().add(randomAlphaCharactersPanel);
        validate();
        repaint();
    }

    private MorseSoundingLabel makeSoundingLabel(LatinReceiverAsync latinReceiver) {
        MorseSoundingLabel soundingLabel = new MorseSoundingLabel(latinReceiver);
        soundingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        soundingLabel.setVerticalAlignment(SwingConstants.CENTER);
        soundingLabel.setFont(new Font("Serif", Font.PLAIN, 36));
        return soundingLabel;
    }

    private JButton makeMainMenuButton(){
        JButton mainMenuButton = new JButton("Back");
        mainMenuButton.setAlignmentX(0.25f);
        mainMenuButton.setAlignmentY(0.25f);
//        mainMenuButton.setSize(new Dimension(MAIN_MENU_BUTTON_WIDTH, MAIN_MENU_BUTTON_HEIGHT));
        mainMenuButton.addActionListener(actionEvent->setupMainMenu());
        return mainMenuButton;
    }
}
