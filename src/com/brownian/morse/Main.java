package com.brownian.morse;

import javax.swing.*;
import javax.sound.midi.*;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int INSTRUMENT = 83;

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

    private void startApp() {
        playStartupSound();
        setVisible(true);
    }

    private void endApp() {
        System.exit(0);
    }

    private void playStartupSound() {
        try {
            LatinReceiver latinReceiver = LatinReceiver.getReceiver();
            latinReceiver.send("sos");
        } catch (MidiUnavailableException e){
            System.err.println("MIDI not available.");
            e.printStackTrace(System.err);
            endApp();
        }
    }

    private void initGUI() {
        setSize(300, 200);
    }
}
