package com.brownian.morse;

/**
 *  Defines an object that can accept Morse symbols.
 */

public interface MorseReceiver {
    void sendSymbol(Morse.Symbol symbol); //sends a single symbol to this receiver
    void sendSymbols(Morse.Symbol[] symbols); //sends an array of symbols to this receiver
    void sendSymbols(Stream<Morse.Symbol> symbolStream); //sends a stream of symbols to this receiver
}