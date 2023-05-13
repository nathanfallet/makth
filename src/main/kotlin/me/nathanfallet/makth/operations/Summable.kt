package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value

/**
 * Summable interface
 */
interface Summable<in T, out U> where T : Value, U : Value {

    /**
     * Sum left value with right value
     * @param right Right value
     * @return Result
     */
    fun sum(right: T): U

}