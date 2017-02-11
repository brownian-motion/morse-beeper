package com.brownian.morse;

import java.io.File;
import java.io.FileInputStream;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.sound.midi.*;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

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
        Runnable soundPlayer = new MorsePlayer();
        Thread soundPlayingThread = new Thread(soundPlayer);
        soundPlayingThread.start();
    }

    private void initGUI() {
        setSize(300, 200);
    }
}
