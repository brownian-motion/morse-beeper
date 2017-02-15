package com.brownian.morse;

import com.brownian.morse.receivers.LatinReceiverAsync;
import com.brownian.morse.receivers.OperationCompletedListener;
import com.brownian.morse.textgenerator.RandomTextGenerator;
import com.sun.istack.internal.NotNull;

import javax.sound.midi.MidiUnavailableException;

/**
 * A {@link javax.swing.JLabel} that displays text from a {@link RandomTextGenerator}, and sounds that text out in Morse using a {@link LatinReceiverAsync}.
 */
public class RandomTextMorsePanel extends MorseSoundingLabel{

    private volatile boolean isProducingRandomCharacters = false;
    private RandomTextGenerator randomTextGenerator;

    private static final int PAUSE_BETWEEN_WORDS_MILLIS = 1000;

    /**
     * Creates a RandomTextMorsePanel that receives text from the given {@link RandomTextGenerator} and sends it
     * to be sounded out to the given {@link LatinReceiverAsync}.
     * @param latinReceiverAsync  the {@link LatinReceiverAsync} to sound out whatever text is generated and displayed.
     * @param randomTextGenerator the {@link RandomTextGenerator} to create text for this object.
     */
    public RandomTextMorsePanel(@NotNull RandomTextGenerator randomTextGenerator, @NotNull LatinReceiverAsync latinReceiverAsync){
        super(latinReceiverAsync);
        this.randomTextGenerator = randomTextGenerator;
    }

    /**
     * Creates a RandomTextMorsePanel that receives text from the given {@link RandomTextGenerator} and sends it
     * to be sounded out to the default {@link LatinReceiverAsync}.
     * @param randomTextGenerator the {@link RandomTextGenerator} to create text for this object.
     * @see LatinReceiverAsync#getReceiver()
     */
    public RandomTextMorsePanel(@NotNull RandomTextGenerator randomTextGenerator) throws MidiUnavailableException{
        this(randomTextGenerator, LatinReceiverAsync.getReceiver());
    }

    /**
     * Starts generating random text, if not already doing that.
     * This text is both displayed and sounded out.
     * This method is idempotent.
     * @see #pause()
     */
    public synchronized void play(){
        if(!isProducingRandomCharacters) //don't start two threads at once
            displayRandomText();
    }

    /**
     * Stops generating new text.
     * Continues displaying and sounding out whatever text is currently displayed.
     * This method is idempotent.
     * @see #play()
     */
    public synchronized void pause(){
        isProducingRandomCharacters = false;
    }

    /**
     * Periodically displays a random string from the provided text generator
     */
    private void displayRandomText(){
        isProducingRandomCharacters = true;
        setText( randomTextGenerator.generateRandomText(), new OperationCompletedListener() {
            @Override
            public void onOperationCompleted() {
                if (isProducingRandomCharacters) { //check for pausing
                    try {
                        Thread.sleep(PAUSE_BETWEEN_WORDS_MILLIS); //wait between strings. We can do this b/c the listener won't be in the main thread
                    } catch (InterruptedException e) {
                        //nothing, we don't care
                    }
                    setText(randomTextGenerator.generateRandomText(), this);
                } //we can exit the method if paused, letting the thread close, because we only need a thread when playing
            }
        });
    }

}
