package com.brownian.morse;

import com.brownian.morse.receivers.LatinReceiverAsync;
import com.brownian.morse.receivers.OperationCompletedListener;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.swing.*;

/**
 * A {@link JLabel} that will asynchronously sound out whatever texts it receives in Morse code.
 */
public class MorseSoundingLabel extends JLabel {

    private final LatinReceiverAsync latinReceiver;

    public MorseSoundingLabel(@NotNull LatinReceiverAsync latinReceiver){
        super();
        this.latinReceiver = latinReceiver;
    }

    @Override
    public void setText(String s){
        super.setText(s);
        if(latinReceiver != null) //the parent sets the text before the receiver is initialized
            latinReceiver.send(s);
    }

    public void setText(String s, @Nullable OperationCompletedListener listener){
        super.setText(s);
        latinReceiver.send(s, listener);
    }
}
