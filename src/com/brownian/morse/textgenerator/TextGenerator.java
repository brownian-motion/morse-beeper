package com.brownian.morse.textgenerator;

import com.sun.istack.internal.NotNull;

/**
 * Generates a series of text.
 */
public interface TextGenerator {
    /**
     * Provides a new string from this generator. May provide duplicates, but should always return a string.
     * @return a new string from this generator
     */
    @NotNull
    String generateNewText();
}
