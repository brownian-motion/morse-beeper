package com.brownian.morse;

import com.brownian.morse.textgenerator.RandomCharacterSupplier;
import com.sun.istack.internal.NotNull;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int APP_WIDTH = 300;
    private static final int APP_HEIGHT = 200;

    private static final int SOUNDING_LABEL_FONT_SIZE = 36;

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

        setupMainMenu();
    }

    /**
     * Clears the app and displays the GUI for the main menu.
     */
    private void setupMainMenu() {
        getContentPane().removeAll();
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BorderLayout());

        mainMenuPanel.add(new JLabel("Morse Code Practice App"),BorderLayout.PAGE_START);

        final JLabel status = new JLabel();
        status.setFont(new Font(Font.SERIF, Font.ITALIC,12));
        mainMenuPanel.add(status, BorderLayout.PAGE_END);

        final JButton randomCharacterButton = new JButton("Listen to random characters");
        randomCharacterButton.addActionListener(actionEvent -> {
            try {
                setupRandomTextPanel(Stream.generate(new RandomCharacterSupplier()));
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
                status.setText("MIDI is unavailable on this device");
                getContentPane().add(mainMenuPanel); //because it had been removed
            }
        });
        mainMenuPanel.add(randomCharacterButton, BorderLayout.CENTER);
        getContentPane().add(mainMenuPanel);
        validate();
        repaint();
    }

    /**
     * Clears the GUI and displays a panel with a "Main Menu" button and a label that
     * displays and sounds out random strings from the given {@link Stream}.
     * @param textGenerator a {@link Stream} used to supply text (in Latin characters) to the panel
     * @throws MidiUnavailableException if MIDI cannot be used to sound out letters in Morse Code
     * @see #makeRandomTextPanel(Stream)
     */
    private void setupRandomTextPanel(@NotNull Stream<String> textGenerator) throws MidiUnavailableException{
        getContentPane().removeAll();

        JPanel randomTextPanel = makeRandomTextPanel(textGenerator);

        getContentPane().add(randomTextPanel);
        validate();
        repaint();
    }

    /**
     * Creates a panel with a "Main Menu" button, and a label that displays and sounds out in Morse
     * strings supplied by the given {@link Stream}.
     * Used in {@link #setupRandomTextPanel(Stream)}
     * @param textStream a {@link Stream} to generate text in Latin characters for the panel
     * @return a panel that displays and sounds out random text from the given generator
     * @throws MidiUnavailableException if MIDI cannot be used to sound out letters in Morse Code
     * @see #setupRandomTextPanel(Stream)
     */
    private JPanel makeRandomTextPanel(@NotNull Stream<String> textStream) throws MidiUnavailableException {
        JPanel randomTextPanel = new JPanel();
        randomTextPanel.setLayout(new BorderLayout());

        TextStreamMorsePanel soundingLabel = new TextStreamMorsePanel(textStream);
        soundingLabel.setFont(new Font(Font.SERIF,Font.PLAIN, SOUNDING_LABEL_FONT_SIZE));
        soundingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        randomTextPanel.add(soundingLabel,BorderLayout.CENTER);
        SwingUtilities.invokeLater(soundingLabel::play);

        JPanel mainMenuButtonWrapper = new JPanel(); //necessary to let button shrink to fit contents
        mainMenuButtonWrapper.setLayout(new BoxLayout(mainMenuButtonWrapper,BoxLayout.LINE_AXIS));

        JButton mainMenuButton = makeMainMenuButton();
        mainMenuButton.addActionListener(actionEvent -> soundingLabel.pause());
        mainMenuButtonWrapper.add(mainMenuButton);
        mainMenuButtonWrapper.add(Box.createHorizontalGlue());

        randomTextPanel.add(mainMenuButtonWrapper, BorderLayout.PAGE_START);
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
