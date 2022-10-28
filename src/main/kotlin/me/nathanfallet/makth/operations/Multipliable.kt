package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value

interface Multipliable<in T, out U> where T : Value, U : Value {

    fun multiply(right: T): U

}