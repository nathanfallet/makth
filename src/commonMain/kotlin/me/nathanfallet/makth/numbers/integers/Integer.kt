package me.nathanfallet.makth.numbers.integers

import me.nathanfallet.makth.extensions.nthRoot
import me.nathanfallet.makth.extensions.pow
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.naturals.Natural
import me.nathanfallet.makth.numbers.naturals.NaturalFactory
import me.nathanfallet.makth.numbers.rationals.Rational
import me.nathanfallet.makth.numbers.rationals.RationalFactory
import me.nathanfallet.makth.numbers.reals.Real
import me.nathanfallet.makth.numbers.reals.RealFactory
import me.nathanfallet.makth.numbers.reals.RealImpl
import me.nathanfallet.makth.sets.matrixes.Matrix
import me.nathanfallet.makth.sets.matrixes.MatrixFactory
import kotlin.js.JsExport
import kotlin.math.abs

/**
 * Integer representation
 */
@JsExport
interface Integer : Rational {

    // Integer interface

    /**
     * Get long value
     */
    val longValue: Long

    // Rational

    override val numerator: Integer
        get() {
        return this
    }

    override val denominator: Natural
        get() {
            return NaturalFactory.instantiate(1)
    }

    // Real

    override val doubleValue: Double get() {
        return longValue.toDouble()
    }

    override val absoluteValue: Natural
        get() {
            return NaturalFactory.instantiate(abs(longValue))
    }

    // Value

    override val rawString: String get() {
        return longValue.toString()
    }

    override val laTeXString: String get() {
        return longValue.toString()
    }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Integer) {
            return IntegerFactory.instantiate(longValue + right.longValue)
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.denominator)
                .sum(right.numerator)
            if (newNumerator is Integer) {
                return RationalFactory.instantiate(newNumerator, right.denominator)
            }
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Integer) {
            return IntegerFactory.instantiate(longValue * right.longValue)
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.numerator)
            if (newNumerator is Integer) {
                return RationalFactory.instantiate(
                    newNumerator,
                    right.denominator
                )
            }
        }
        if (right is Real) {
            return RealImpl(doubleValue).multiply(right)
        }
        if (right is Matrix) {
            return MatrixFactory.instantiate(right.rows.map { rows ->
                rows.map { multiply(it) }
            })
        }
        return super.multiply(right)
    }

    override fun divide(right: Value): Value {
        if (right is Integer) {
            return RationalFactory.instantiate(
                this,
                right
            )
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.denominator)
            if (newNumerator is Integer) {
                return RationalFactory.instantiate(
                    newNumerator,
                    right.numerator
                )
            }
        }
        return super.divide(right)
    }

    override fun remainder(right: Value): Value {
        if (right is Integer) {
            return IntegerFactory.instantiate(longValue % right.longValue)
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.denominator).remainder(right.numerator)
            if (newNumerator is Integer) {
                return RationalFactory.instantiate(
                    newNumerator,
                    right.denominator
                )
            }
        }
        return super.remainder(right)
    }

    override fun raise(right: Value): Value {
        if (right is Integer) {
            return if (right.longValue < 0) {
                RationalFactory.instantiate(
                    IntegerFactory.instantiate(1),
                    IntegerFactory.instantiate(longValue.pow(-right.longValue))
                )
            } else {
                IntegerFactory.instantiate(longValue.pow(right.longValue))
            }
        }
        if (right is Rational) {
            val firstRaised = raise(right.numerator)
            if (firstRaised is Integer) {
                return if (firstRaised.doubleValue < 0) {
                    // In case of negative number, we need to take the opposite
                    // and check if the denominator is even (to avoid complex case).
                    if (right.denominator.longValue and 1 == 0L) {
                        RealFactory.instantiate(Double.NaN)
                    } else {
                        RealFactory.instantiate(
                            -(-firstRaised.longValue)
                                .nthRoot(right.denominator.longValue)
                        )
                    }
                } else {
                    RealFactory.instantiate(firstRaised.longValue.nthRoot(right.denominator.longValue))
                }
            }
        }
        return super.raise(right)
    }

}