package com.brownian.morse;

import javax.sound.midi.*;

public class MorsePlayer implements Runnable {

    private static final int INSTRUMENT = 82;

    public void run() {
        try {
            Receiver receiver = MidiSystem.getReceiver();
            receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE,0,INSTRUMENT,0),-1);

            LatinReceiver latinReceiver = new LatinReceiver(new BufferedMorseReceiver(receiver));
            latinReceiver.send("s");
            Thread.sleep(2000);
            latinReceiver.send('e');
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: add getReceiver() methods, interfaces, etc.


}
