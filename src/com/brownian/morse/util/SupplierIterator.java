package com.brownian.morse.util;

import com.sun.istack.internal.NotNull;

import java.util.Iterator;
import java.util.function.Supplier;

/**
 * Allows a Supplier to be treated as an unbounded {@link Iterator}.
 */
public class SupplierIterator<E> implements Iterator<E> {

    private final Supplier<E> supplier;

    /**
     * Creates an iterator backed by the given supplier.
     * @param supplier a supplier to supply elements to this iterator
     */
    public SupplierIterator(@NotNull Supplier<E> supplier){
        this.supplier = supplier;
    }

    /**
     * Whether or not this iterator has another element.
     * Since a {@link Supplier} provides an infinite supply of elements,
     * always returns true.
     * @return true
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Returns the next element supplied by the underlying {@link Supplier}
     * @return the next element supplied by the underlying {@link Supplier}
     */
    @Override
    public E next() {
        return supplier.get();
    }
}
