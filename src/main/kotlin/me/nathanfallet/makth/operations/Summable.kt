package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value

interface Summable<in T, out U> where T : Value, U : Value {

    fun sum(right: T): U

}