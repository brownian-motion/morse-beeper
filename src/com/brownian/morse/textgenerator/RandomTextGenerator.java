package com.brownian.morse.textgenerator;

import com.sun.istack.internal.NotNull;

/**
 * Generates a random series of text.
 */
public interface RandomTextGenerator {
    /**
     * Provides a random string from this generator
     * @return a random string from this generator
     */
    @NotNull
    String generateRandomText();
}
