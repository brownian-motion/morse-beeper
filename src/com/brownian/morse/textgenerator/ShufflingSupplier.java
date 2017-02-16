package com.brownian.morse.textgenerator;

import com.sun.istack.internal.NotNull;

import java.util.Random;
import java.util.function.Supplier;

/**
 * A supplier that provides an infinite series of Strings chosen randomly from the underlying collection.
 */
public class ShufflingSupplier implements Supplier<String> {
    private String[] strings;

    /**
     * Creates a ShufflingSupplier that selects random strings from the given array.
     * @param strings an array of {@link String}s to choose randomly from
     * @throws IllegalArgumentException if provided null or an empty array.
     */
    public ShufflingSupplier(@NotNull String[] strings) throws IllegalArgumentException{
        if(strings == null || strings.length == 0)
            throw new IllegalArgumentException("Must provide a non-empty array of strings");
        this.strings = strings;
    }

    /**
     * Gets a random string from the provided array
     * @return a random string from the provided array
     */
    @NotNull
    public String get(){
        return strings[new Random().nextInt(strings.length)];
    }
}
