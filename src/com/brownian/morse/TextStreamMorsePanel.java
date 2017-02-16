package com.brownian.morse;

import com.brownian.morse.receivers.LatinReceiverAsync;
import com.sun.istack.internal.NotNull;

import javax.sound.midi.MidiUnavailableException;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A {@link javax.swing.JLabel} that displays text from a {@link Stream}, and sounds that text out in Morse using a {@link LatinReceiverAsync}.
 */
public class TextStreamMorsePanel extends MorseSoundingLabel{

    private volatile boolean isPlaying = false;
    private Iterator<String> textIterator;

    private static final int PAUSE_BETWEEN_WORDS_MILLIS = 1000;

    /**
     * Creates a {@link TextStreamMorsePanel} that receives text from the given {@link Stream} and sends it
     * to be sounded out to the given {@link LatinReceiverAsync}.
     * @param latinReceiverAsync  the {@link LatinReceiverAsync} to sound out whatever text is generated and displayed.
     * @param textStream the {@link Stream} used to supply text for this object.
     */
    public TextStreamMorsePanel(@NotNull Stream<String> textStream, @NotNull LatinReceiverAsync latinReceiverAsync){
        super(latinReceiverAsync);
        this.textIterator = textStream.iterator(); //this is necessary because we need to be able to get one string at a time
    }

    /**
     * Creates a {@link TextStreamMorsePanel} that receives text from the given {@link Stream} and sends it
     * to be sounded out to the default {@link LatinReceiverAsync}.
     * @param textStream the {@link Stream} used to supply text for this object.
     * @throws MidiUnavailableException if MIDI cannot be used to generate sounds
     * @see LatinReceiverAsync#getReceiver()
     */
    public TextStreamMorsePanel(@NotNull Stream<String> textStream) throws MidiUnavailableException{
        this(textStream, LatinReceiverAsync.getReceiver());
    }

    /**
     * Starts displaying text from the underlying {@link Stream}, if not already doing that.
     * If there is no more text in the stream, nothing happens.
     * This text is both displayed and sounded out.
     * This method is idempotent.
     * @see #pause()
     */
    public synchronized void play(){
        if(!isPlaying) {  //don't start two threads at once
            isPlaying = true;
            displayNewText();
        }

    }

    /**
     * Stops generating new text.
     * Continues displaying and sounding out whatever text is currently displayed.
     * This method is idempotent.
     * @see #play()
     */
    public synchronized void pause(){
        isPlaying = false;
    }

    /**
     * Periodically displays a new string from the provided text generator
     */
    private void displayNewText(){
        if(textIterator.hasNext())
            this.setText(" "+textIterator.next()+" ", () -> { //wait between strings. We can do this b/c the listener won't be in the main thread
                if (isPlaying)  //check for pausing - might have been paused in a different thread
                    displayNewText();
                //we can exit the method if paused, letting the thread close, because we only need a thread while playing
            });
        else
            pause();
    }

}
