package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value

/**
 * Multipliable interface
 */
interface Multipliable<in T, out U> where T : Value, U : Value {

    /**
     * Multiply left value by right value
     * @param right Right value
     * @return Result
     */
    fun multiply(right: T): U

}