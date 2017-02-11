package com.brownian.morse;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

/**
 * A class that receives {@link Morse.Symbol Morse symbols}, translates them to MIDI
 * instructions, and passes those to a MIDI receiver.
 */
public class BufferedMorseReceiver implements MorseReceiver {
    private final Receiver midiReceiver;
    private BlockingQueue<Morse.Symbol> symbols;

    private static final int NOTE = 80;

    private static final int DOT_MILLIS = 100; //how long to hold a dot
    private static final int DASH_MILLIS = 3 * DOT_MILLIS; //how long to hold a dash
    private static final int SYMBOL_BREAK_MILLIS = DOT_MILLIS; //how long to wait between symbols
    private static final int CHAR_BOUNDARY_PAUSE_MILLIS = 3 * DOT_MILLIS; //how long to wait between characters
    private static final int WORD_BOUNDARY_PAUSE_MILLIS = 5 * DOT_MILLIS; //how long to wait when a pause character is received

    public BufferedMorseReceiver(Receiver midiReceiver){
        this.midiReceiver = midiReceiver;
        this.symbols = symbols = new LinkedBlockingQueue<>();

        new Thread(() -> {
            try {
                //noinspection InfiniteLoopStatement
                while(true)
                    playImmediately(BufferedMorseReceiver.this.symbols.take());
            } catch (InterruptedException e) {
                System.err.println("This should never happen:");
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Receives a symbol of Morse code, and buffers it for later use.
     * @param symbol A symbol of Morse code to receive.
     */
    @Override
    public void sendSymbol(Morse.Symbol symbol) {
        addToBuffer(symbol);
    }

    @Override
    public void sendSymbols(Morse.Symbol[] symbols) {
        for(Morse.Symbol c : symbols)
            addToBuffer(c);
    }

    @Override
    public void sendSymbols(Stream<Morse.Symbol> symbolStream) {
        symbolStream.forEach(this::addToBuffer);
    }


    private void addToBuffer(Morse.Symbol symbol){
        try {
            symbols.put(symbol);
        } catch (InterruptedException e) {
            System.err.println("This should never happen:");
            e.printStackTrace();
        }
    }

    /**
     * Immediately plays the requested symbol.
     *
     * This method requires that the MIDI Receiver in this object is not playing anything on channel 0,
     * and leaves it that way afterwards.
     */
    private void playImmediately(Morse.Symbol symbol) throws InterruptedException {
        switch (symbol) {
            case DOT:
                sendMessage(true);
                Thread.sleep(DOT_MILLIS);
                sendMessage(false);
                Thread.sleep(SYMBOL_BREAK_MILLIS);
                break;
            case DASH:
                sendMessage(true);
                Thread.sleep(DASH_MILLIS);
                sendMessage(false);
                Thread.sleep(SYMBOL_BREAK_MILLIS);
                break;
            case WORD_BOUNDARY_PAUSE:
                Thread.sleep(WORD_BOUNDARY_PAUSE_MILLIS);
                break;
            case CHAR_BOUNDARY_PAUSE:
                Thread.sleep(CHAR_BOUNDARY_PAUSE_MILLIS);
                break;
        }
    }

    /**
     * Sends a message to the MIDI {@link Receiver} to immediately turn the Morse sound on or off.
     * @param isOn Whether this message will tell the Receiver to turn on the Morse sound.
     */
    private void sendMessage(boolean isOn){
        midiReceiver.send(generateMessage(isOn),-1);
    }

    /**
     * Creates a message to turn the Morse sound on or off.
     * @param isOn Whether this message will tell the Receiver to turn on the Morse sound.
     * @return a message that will tell the receiver to turn the Morse sound on or off.
     * @see #sendMessage(boolean)
     */
    private static ShortMessage generateMessage(boolean isOn){
        try {
            return new ShortMessage(ShortMessage.NOTE_ON, 0, NOTE, isOn ? 93 : 1);
        } catch (InvalidMidiDataException e){
            System.err.println("This should never happen:");
            e.printStackTrace();
            return new ShortMessage();
        }
    }
}
