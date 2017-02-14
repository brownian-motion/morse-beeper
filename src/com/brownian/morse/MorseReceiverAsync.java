package com.brownian.morse;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.stream.Stream;

/**
 * A version of {@link MorseReceiver}, designed for asynchronous operation.
 * This class differs by accepting a callback function to report when the receiver is done processing its input.
 *
 * All of the synchronous functions defined in {@link MorseReceiver} will still work, but will operate asynchronously
 * and will not notify any callback, as though passed null for a listener.
 */
public interface MorseReceiverAsync extends MorseReceiver {
    /**
     * Sends a symbol to this receiver, returning immediately
     * and notifying the listener when the symbol has been processed.
     * @param symbol   The symbol to send to the receiver
     * @param listener An optional listener to notify when this symbol has been consumed.
     */
    void sendSymbol(@NotNull Morse.Symbol symbol, @Nullable OperationCompletedListener listener);

    /**
     * Sends an array of symbols to this receiver, returning immediately
     * and notifying the listener when the symbol has been processed.
     * @param symbols  The symbols to send to the receiver
     * @param listener An optional listener to notify when this symbol has been consumed.
     */
    void sendSymbols(@NotNull Morse.Symbol[] symbols, @Nullable OperationCompletedListener listener);

    /**
     * Sends a stream of symbols to this receiver, returning immediately
     * and notifying the listener when the symbol has been processed.
     * @param symbolStream The symbols to send to the receiver
     * @param listener     An optional listener to notify when this symbol has been consumed.
     */
    void sendSymbols(@NotNull Stream<Morse.Symbol> symbolStream, @Nullable OperationCompletedListener listener);
}
