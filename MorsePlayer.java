import javax.sound.midi.*;

public class MorsePlayer extends Runnable {

  public static final int DOT_MILLIS = 300;

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
 *
 */
  public void play(Morse.Symbol symbol){
    switch(symbol){
      case Morse.Symbol.DOT:
        //TODO
      case Morse.Symbol.DASH:
        //TODO
    }
  }
}
