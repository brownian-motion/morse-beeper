package com.brownian.morse;

import com.brownian.morse.textgenerator.RandomCharacterSupplier;
import com.brownian.morse.textgenerator.ShufflingSupplier;
import com.brownian.morse.util.SupplierIterator;
import com.sun.istack.internal.NotNull;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int APP_WIDTH = 300;
    private static final int APP_HEIGHT = 200;

    private static final int SOUNDING_LABEL_FONT_SIZE = 36;
    public static final String MOST_COMMON_WORDS_RESOURCE_PATH = "/EnglishMostCommon100.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.startApp();
        });
    }

    public Main() {
        initGUI();
    }

    /**
     * After the app is created, starts the app.
     */
    private void startApp() {
        setVisible(true);
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

        setTitle("Morse Code Practice App");

        setupMainMenu();
    }

    /**
     * Clears the app and displays the GUI for the main menu.
     */
    private void setupMainMenu() {
        getContentPane().removeAll();
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BorderLayout());

        final JLabel status = new JLabel();
        status.setFont(new Font(Font.SERIF, Font.ITALIC,12));
        mainMenuPanel.add(status, BorderLayout.PAGE_END);

        final JPanel modeButtonsPanel = new JPanel(new GridLayout(0,1));

        final JButton randomCharacterButton = new JButton("Listen to random characters");
        randomCharacterButton.addActionListener(actionEvent -> {
            try {
                setupRandomTextPanel(new SupplierIterator<>(new RandomCharacterSupplier()));
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
                status.setText("MIDI is unavailable on this device");
                getContentPane().add(mainMenuPanel); //because it had been removed
            }
        });
        modeButtonsPanel.add(randomCharacterButton);

        final JButton commonWordsButton = new JButton("Listen to common words");
        commonWordsButton.addActionListener(actionEvent -> {
            try {
                BufferedReader resourceReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(MOST_COMMON_WORDS_RESOURCE_PATH)));
                setupRandomTextPanel(new SupplierIterator<>(new ShufflingSupplier(resourceReader.lines().toArray(String[]::new))));
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
                status.setText("MIDI is unavailable on this device");
                getContentPane().add(mainMenuPanel); //because it had been removed
            } catch (NullPointerException e){
                e.printStackTrace();
                status.setText("Could not load resource "+MOST_COMMON_WORDS_RESOURCE_PATH);
                getContentPane().add(mainMenuPanel); //because it had been removed
            }
        });
        modeButtonsPanel.add(commonWordsButton);

        mainMenuPanel.add(modeButtonsPanel, BorderLayout.CENTER);
        getContentPane().add(mainMenuPanel);
        validate();
        repaint();
    }

    /**
     * Clears the GUI and displays a panel with a "Main Menu" button and a label that
     * displays and sounds out random strings from the given {@link Iterator}.
     * @param textGenerator an {@link Iterator} used to supply text (in Latin characters) to the panel
     * @throws MidiUnavailableException if MIDI cannot be used to sound out letters in Morse Code
     * @see #makeRandomTextPanel(Iterator)
     */
    private void setupRandomTextPanel(@NotNull Iterator<String> textGenerator) throws MidiUnavailableException{
        getContentPane().removeAll();

        JPanel randomTextPanel = makeRandomTextPanel(textGenerator);

        getContentPane().add(randomTextPanel);
        validate();
        repaint();
    }

    /**
     * Creates a panel with a "Main Menu" button, and a label that displays and sounds out in Morse
     * strings supplied by the given {@link Iterator}.
     * Used in {@link #setupRandomTextPanel(Iterator)}
     * @param textStream an {@link Iterator} to supply text in Latin characters for the panel
     * @return a panel that displays and sounds out random text from the given generator
     * @throws MidiUnavailableException if MIDI cannot be used to sound out letters in Morse Code
     * @see #setupRandomTextPanel(Iterator)
     */
    private JPanel makeRandomTextPanel(@NotNull Iterator<String> textStream) throws MidiUnavailableException {
        JPanel randomTextPanel = new JPanel();
        randomTextPanel.setLayout(new BorderLayout());

        TextStreamMorsePanel soundingLabel = new TextStreamMorsePanel(textStream);
        soundingLabel.setFont(new Font(Font.SERIF,Font.PLAIN, SOUNDING_LABEL_FONT_SIZE));
        soundingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        randomTextPanel.add(soundingLabel,BorderLayout.CENTER);

        JPanel mainMenuButtonWrapper = new JPanel(); //necessary to let button shrink to fit contents
        mainMenuButtonWrapper.setLayout(new BoxLayout(mainMenuButtonWrapper,BoxLayout.LINE_AXIS));

        JButton mainMenuButton = makeMainMenuButton();
        mainMenuButton.addActionListener(actionEvent -> soundingLabel.pause());
        mainMenuButtonWrapper.add(mainMenuButton);
        mainMenuButtonWrapper.add(Box.createHorizontalGlue());
        randomTextPanel.add(mainMenuButtonWrapper, BorderLayout.PAGE_START);

        JButton playPauseButton = new JButton("Play");
        playPauseButton.addActionListener(actionEvent->{
            if(soundingLabel.isPlaying()) {
                soundingLabel.pause();
                playPauseButton.setText("Play");
            } else {
                soundingLabel.play();
                playPauseButton.setText("Pause");
            }
        });
        randomTextPanel.add(playPauseButton, BorderLayout.PAGE_END);

        return randomTextPanel;
    }

    /**
     * Creates a consistent "Main Menu" button, that changes the GUI to the main menu when triggered
     * @return a JButton that changes the GUI to the main menu when triggered
     */
    private JButton makeMainMenuButton(){
        JButton mainMenuButton = new JButton("Back");
        mainMenuButton.addActionListener(actionEvent->setupMainMenu());
        return mainMenuButton;
    }
}
