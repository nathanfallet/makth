package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value

/**
 * Divisible interface
 */
interface Divisible<in T, out U> where T : Value, U : Value {

    /**
     * Divide by another value
     * @param right Value to divide by
     * @return Result of the division
     */
    fun divide(right: T): U

    /**
     * Get the remainder of the division by another value
     * @param right Value to divide by
     * @return Remainder of the division
     */
    fun remainder(right: T): U

}