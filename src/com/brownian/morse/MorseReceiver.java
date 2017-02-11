package com.brownian.morse;

import com.sun.istack.internal.NotNull;

import java.util.stream.Stream;

/**
 *  Defines an object that can accept Morse symbols.
 */

public interface MorseReceiver {
    /**
     * Sends a symbol of Morse code to this receiver, which is used to generate MIDI commands.
     * @param symbol A symbol of Morse code to receive.
     */
    void sendSymbol(@NotNull Morse.Symbol symbol);

    /**
     * Sends an array of {@link Morse.Symbol Morse symbols} to this receiver, which are used to generate MIDI commands.
     * @param symbols an array of Morse symbols
     */
    void sendSymbols(@NotNull Morse.Symbol[] symbols);

    /**
     * Sends a stream of {@link Morse.Symbol Morse symbols} to this receiver, which are used to generate MIDI commands.
     * @param symbolStream a stream of Morse symbols
     */
    void sendSymbols(@NotNull Stream<Morse.Symbol> symbolStream);
}