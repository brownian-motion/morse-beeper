package com.brownian.morse.textgenerator;

import com.sun.istack.internal.NotNull;

import java.util.function.Supplier;

/**
 * A {@link Supplier} for a {@link java.util.stream.Stream} that generates an infinite series
 * of single-character strings from A to Z.
 * @see java.util.stream.Stream#generate(Supplier)
 */
public class RandomCharacterSupplier implements Supplier<String> {
    /**
     * Chooses a random letter between 'A' and 'Z'.
     * @return a random letter between 'A' and 'Z'
     */
    @Override
    @NotNull
    public String get() {
        return String.valueOf((char) ((int) (Math.random() * 26) + 'A'));
    }
}
