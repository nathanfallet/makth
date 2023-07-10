package me.nathanfallet.makth.numbers

import kotlin.math.abs
import kotlin.math.pow
import me.nathanfallet.makth.extensions.pow
import me.nathanfallet.makth.extensions.nthRoot
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.sets.Matrix

/**
 * Integer representation
 */
interface Integer : Rational {

    // Instantiate

    companion object {

        /**
         * Instantiate an integer from a long value
         * @param value Long value
         * @return Integer
         */
        @JvmStatic
        fun instantiate(value: Long): Integer {
            return if (value < 0) IntegerImpl(value) else Natural.instantiate(value)
        }

    }

    // Integer interface

    /**
     * Get long value
     */
    val longValue: Long

    // Rational

    override val numerator: Integer get() {
        return this
    }

    override val denominator: Natural get() {
        return Natural.instantiate(1)
    }

    // Real

    override val doubleValue: Double get() {
        return longValue.toDouble()
    }

    override val absoluteValue: Natural get() {
        return Natural.instantiate(abs(longValue))
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
            return instantiate(longValue + right.longValue)
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.denominator)
                .sum(right.numerator)
            if (newNumerator is Integer) {
                return Rational.instantiate(newNumerator, right.denominator)
            }
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Integer) {
            return instantiate(longValue * right.longValue)
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.numerator)
            if (newNumerator is Integer) {
                return Rational.instantiate(
                    newNumerator,
                    right.denominator
                )
            }
        }
        if (right is Real) {
            return RealImpl(doubleValue).multiply(right)
        }
        if (right is Matrix) {
            return Matrix.instantiate(right.rows.map { rows ->
                rows.map { multiply(it) }
            })
        }
        return super.multiply(right)
    }

    override fun divide(right: Value): Value {
        if (right is Integer) {
            return Rational.instantiate(
                this,
                right
            )
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.denominator)
            if (newNumerator is Integer) {
                return Rational.instantiate(
                    newNumerator,
                    right.numerator
                )
            }
        }
        return super.divide(right)
    }

    override fun remainder(right: Value): Value {
        if (right is Integer) {
            return instantiate(longValue % right.longValue)
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.denominator).remainder(right.numerator)
            if (newNumerator is Integer) {
                return Rational.instantiate(
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
                Rational.instantiate(
                    instantiate(1),
                    instantiate(longValue.pow(-right.longValue).toLong())
                )
            } else {
                instantiate(longValue.pow(right.longValue).toLong())
            }
        }
        if (right is Rational) {
            val firstRaised = raise(right.numerator)
            if (firstRaised is Integer) {
                return if (firstRaised.doubleValue < 0) {
                    // In case of negative number, we need to take the oppposite
                    // and check if the denominator is even (to avoid complex case).
                    if (right.denominator.longValue and 1 == 0L) {
                        Real.instantiate(Double.NaN)
                    } else {
                        Real.instantiate(
                            -(-firstRaised.longValue)
                            .nthRoot(right.denominator.longValue)
                        )
                    }
                } else {
                    Real.instantiate(firstRaised.longValue.nthRoot(right.denominator.longValue))
                }
            }
        }
        return super.raise(right)
    }

}