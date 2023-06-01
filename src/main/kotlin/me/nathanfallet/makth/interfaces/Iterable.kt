package me.nathanfallet.makth.interfaces

/**
 * Interface for iterable values
 */
interface Iterable: Value {

    /**
     * Get an iterator for this iterable
     * @return Iterator
     */
    fun getIterator(): Iterator<Value>

}
