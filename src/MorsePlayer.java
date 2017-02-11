import javax.sound.midi.*;

public class MorsePlayer implements Runnable {

    public static final int DOT_MILLIS = 300; //how long to hold a dot
    public static final int DASH_MILLIS = 3 * DOT_MILLIS; //how long to hold a dash
    public static final int SYMBOL_BREAK_MILLIS = DOT_MILLIS; //how long to wait between symbols
    public static final int CHAR_BREAK_MILLIS = 3 * DOT_MILLIS; //how long to wait between characters
    public static final int PAUSE_BREAK_MILLIS = 5 * DOT_MILLIS; //how long to wait when a pause character is received

    public void run() {
        try {
            Receiver receiver = MidiSystem.getReceiver();
            receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE,0,81,0),-1);
            ShortMessage myMsg =
                    // Start playing the note Middle C (60),
                    // moderately loud (velocity = 93).
                    new ShortMessage(ShortMessage.NOTE_ON, 0, 80, 93);
            long timeStamp = -1;
            receiver.send(myMsg, timeStamp);
            for(int instrument = 81 ; instrument <= 100 ; instrument ++){
                Thread.sleep(500);
                System.out.println(instrument);
                receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE,0,instrument,0),-1);

                receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 80, 0),-1);

                receiver.send(new ShortMessage(ShortMessage.NOTE_ON, 0, 80, 93),-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Immediately plays the requested symbol.
     */
    private void play(Morse.Symbol symbol) {
        switch (symbol) {
            case Morse.Symbol.DOT:
                sendMessage(true);
                Thread.sleep(DOT_MILLIS);
                sendMessage(false);
                Thread.sleep(DOT_MILLIS);
            case Morse.Symbol.DASH:
                //TODO
        }
    }

    private void sendMessage(boolean isOn){
        receiver.send(generateMessage(isOn),-1);
    }

    private static ShortMessage generateMessage(boolean isOn){
        return new ShortMessage(ShortMessage.NOTE_ON, 0, INSTRUMENT, isOn?93:1);
    }
}
