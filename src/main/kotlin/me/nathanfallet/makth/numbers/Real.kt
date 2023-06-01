package me.nathanfallet.makth.numbers

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.sets.Vector
import me.nathanfallet.makth.sets.Matrix

/**
 * Real representation
 */
interface Real : Vector {

    // Instantiate

    companion object {

        /**
         * Pi constant
         */
        @JvmStatic
        val pi: Real = RealImplPi()

        /**
         * Instantiate a real from a double value
         * @param value Double value
         * @return Real
         */
        @JvmStatic
        fun instantiate(value: Double): Real {
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

    /**
     * Get double value
     * @return Double value
     */
    fun getDoubleValue(): Double

    /**
     * Get the absolute value of this real
     * @return Real
     */
    fun absoluteValue(): Real {
        return instantiate(abs(getDoubleValue()))
    }

    // Vector

    override fun getElements(): List<Value> {
        return listOf(this)
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

    override fun equals(right: Value): Boolean {
        if (right is Real) {
            return getDoubleValue() == right.getDoubleValue()
        }
        return super.equals(right)
    }

    override fun lessThan(right: Value): Boolean {
        if (right is Real) {
            return getDoubleValue() < right.getDoubleValue()
        }
        return super.lessThan(right)
    }

    override fun sum(right: Value): Value {
        if (right is Real) {
            return instantiate(getDoubleValue() + right.getDoubleValue())
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Real) {
            return instantiate(getDoubleValue() * right.getDoubleValue())
        }
        if (right is Matrix) {
            return Matrix.instantiate(right.getRows().map { rows ->
                rows.map { multiply(it) }
            })
        }
        return super.multiply(right)
    }

    override fun divide(right: Value): Value {
        if (right is Real) {
            return instantiate(getDoubleValue() / right.getDoubleValue())
        }
        return super.divide(right)
    }

    override fun remainder(right: Value): Value {
        if (right is Real) {
            return instantiate(getDoubleValue() % right.getDoubleValue())
        }
        return super.remainder(right)
    }

    override fun raise(right: Value): Value {
        if (right is Real) {
            return instantiate(getDoubleValue().pow(right.getDoubleValue()))
        }
        return super.raise(right)
    }

}
