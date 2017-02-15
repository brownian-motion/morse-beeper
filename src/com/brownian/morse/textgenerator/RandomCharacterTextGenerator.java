package com.brownian.morse.textgenerator;

import com.sun.istack.internal.NotNull;

/**
 * A {@link TextGenerator} that generates single-character strings from A to Z.
 */
public class RandomCharacterTextGenerator implements TextGenerator {
    /**
     * Chooses a random letter between 'A' and 'Z'.
     * @return a random letter between 'A' and 'Z'
     */
    @Override
    @NotNull
    public String generateNewText() {
        return String.valueOf((char) ((int) (Math.random() * 26) + 'A'));
    }
}
