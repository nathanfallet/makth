package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value

/**
 * Exponentiable interface
 */
interface Exponentiable<in T, out U> where T : Value, U : Value {

    /**
     * Raise left value to the power of right value
     * @param right Right value
     * @return Result
     */
    fun raise(right: T): U

}