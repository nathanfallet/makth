package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value

interface Divisible<in T, out U> where T : Value, U : Value {

    fun divide(right: T): U

    fun remainder(right: T): U

}