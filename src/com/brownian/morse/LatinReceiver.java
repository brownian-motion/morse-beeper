package com.brownian.morse;

/**
 * Accepts a character and sends it to a MorseReceiver as an array of {@link Morse.Symbol Morse symbols}
 */
public class LatinReceiver {
    private final MorseReceiver morseReceiver;

    public LatinReceiver(MorseReceiver receiver){
        this.morseReceiver = receiver;
    }

    public void send(char latinChar){
        morseReceiver.sendSymbols(Morse.toMorse(latinChar));
        if(!Character.isWhitespace(latinChar))
            morseReceiver.sendSymbol(Morse.Symbol.CHAR_BOUNDARY_PAUSE);
    }

    public void send(String latinText){
        for(int i = 0 ; i < latinText.length() ; i++){
            send(latinText.charAt(i));
        }
    }
}
