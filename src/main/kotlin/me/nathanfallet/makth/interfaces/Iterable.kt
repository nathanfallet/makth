package me.nathanfallet.makth.interfaces

/**
 * Interface for iterable values
 */
interface Iterable: Value {

    /**
     * Iterator
     */
    val iterator: Iterator<Value>

}
