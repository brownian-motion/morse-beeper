package com.brownian.morse.receivers;

import com.brownian.morse.Morse;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

/**
 * A {@link LatinReceiver} that supports asynchronous operations.
 * Instead of synchronously calling and waiting, these send methods
 * return immediately, and notify a listener when the provided character has been consumed.
 *
 * Calling parent send() methods is equivalent to providing null for a listener.
 */
public class LatinReceiverAsync extends LatinReceiver {
    private MorseReceiverAsync morseReceiverAsync;

    public LatinReceiverAsync(MorseReceiverAsync morseReceiverAsync){
        super(morseReceiverAsync);
        this.morseReceiverAsync = morseReceiverAsync;
    }

    public void send(char latinChar, @Nullable OperationCompletedListener listener){
        morseReceiverAsync.sendSymbols(Morse.toMorse(latinChar), listener);
        if(!Character.isWhitespace(latinChar))
            morseReceiverAsync.sendSymbol(Morse.Symbol.CHAR_BOUNDARY_PAUSE);
    }

    public void send(@NotNull String s, @Nullable OperationCompletedListener listener){
        for(int i = 0 ; i < s.length()-1 ; i++){
            send(s.charAt(i));
        }
        if(!s.isEmpty())
            send(s.charAt(s.length()-1),listener); //moved outside the for loop so we're not checking if we're at the end a bunch of times
    }

    /**
     * A factory method that generates a LatinReceiver connected to the default MIDI {@link Receiver}.
     * @return A LatinReceiver that accepts text, and forwards MIDI commands describing the Morse code to a MIDI receiver.
     * @throws MidiUnavailableException if MIDI could not be loaded
     * @see MidiSystem#getReceiver()
     */
    public static LatinReceiverAsync getReceiver() throws MidiUnavailableException{
        return new LatinReceiverAsync(MorseToMidiReceiverAsync.getReceiver());
    }
}
