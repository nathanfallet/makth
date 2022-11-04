package me.nathanfallet.makth.numbers

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.operations.Divisible
import me.nathanfallet.makth.operations.Multipliable
import me.nathanfallet.makth.operations.Summable
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import kotlin.math.abs
import kotlin.math.floor

interface Real : Value, Summable<Real, Real>,
    Multipliable<Real, Real>, Divisible<Real, Real> {

    // Instantiate

    companion object {

        val pi: Real = RealImplPi()

        fun instantiate(
            value: Double
        ): Real {
            // Check if value is an integer
            if (floor(value) == value) {
                return Integer.instantiate(value.toLong())
            }

            // Check if value is a rational
            // Is it possible, as a double has a finite number of digits?

            // Check for some constants
            if (value == pi.getDoubleValue()) {
                return pi
            }

            // Otherwise, it's a real
            return RealImpl(value)
        }

    }

    // Real interface

    fun getDoubleValue(): Double

    fun absoluteValue(): Real {
        return instantiate(abs(getDoubleValue()))
    }

    // Value

    override fun toRawString(): String {
        return getDoubleValue().toString()
    }

    override fun toLaTeXString(): String {
        return getDoubleValue().toString()
    }

    override fun compute(context: Context): Value {
        return this
    }

    override fun extractVariables(): Set<Variable> {
        return setOf()
    }

    // Operations

    override fun sum(right: Real): Real {
        return instantiate(getDoubleValue() + right.getDoubleValue())
    }

    override fun multiply(right: Real): Real {
        return instantiate(getDoubleValue() * right.getDoubleValue())
    }

    override fun divide(right: Real): Real {
        return instantiate(getDoubleValue() / right.getDoubleValue())
    }

}